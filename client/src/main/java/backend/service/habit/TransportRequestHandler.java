package backend.service.habit;

import backend.entity.habit.transport.PublicTransport;
import backend.entity.habit.transport.TravelByBike;
import backend.service.AuthHeader;
import backend.service.tab.HabitRequestHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class handles all the requests that belong to transport related habits.
 */
@Getter
@Setter
public class TransportRequestHandler {
    private String generalUrl = "https://ooppgogreen99.herokuapp.com/v1";
    private final String bikeUrl = "/transport/travel_by_bike/";
    private final String ptUrl = "/transport/public_transport/";
    private AuthHeader auth = new AuthHeader();
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
    private HabitRequestHandler habitHandler = HabitRequestHandler.getInstance();

    /**
     * Private constructor to define the service in the singleton design pattern.
     */
    private TransportRequestHandler() {
    }

    /**
     * Helper class to avoid concurrent threads to request the same resource at the same time.
     */
    private static class TransportRequestHelper {
        private static final TransportRequestHandler INSTANCE = new TransportRequestHandler();
    }
    
    /**
     * This method creates an ArrayList of TravelByBike/PublicTransport,
     * using readValue() from ObjectMapper.
     *
     * @param url       request path
     * @return ArrayList of TravelByBike/PublicTransport objects
     */
    private ArrayList getTransportHabit(String url) {
        ArrayList list = new ArrayList<>();
        HttpEntity entity = auth.makeEntity(auth.makeHeader());
        JsonNode jsonNode = restTemplate.exchange(
                generalUrl + url,
                HttpMethod.GET,
                entity,
                JsonNode.class)
            .getBody();

        String transportString = jsonNode != null ? jsonNode.toString() : "";
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            if (url == bikeUrl) {
                list = objectMapper
                    .readValue(transportString, new TypeReference<ArrayList<TravelByBike>>(){});
            } else if (url == ptUrl) {
                list = objectMapper
                    .readValue(transportString, new TypeReference<ArrayList<PublicTransport>>(){});
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return list;
    }

    /**
     * Retrieves the instance for the habit service.
     *
     * @return final INSTANCE of the service
     */
    public static TransportRequestHandler getInstance() {
        return TransportRequestHelper.INSTANCE;
    }

    /**
     * This method retrieves all the bike rides from the current user.
     *
     * @return ArrayList of TravelByBike objects.
     */
    public ArrayList<TravelByBike> getUserBikeRides() {
        return getTransportHabit(bikeUrl);
    }

    /**
     * This method retrieves all the public transport journeys of the current user.
     *
     * @return ArrayList of PublicTransport objects.
     */
    public ArrayList<PublicTransport> getUserPublicTransports() {
        return getTransportHabit(ptUrl);
    }

    /**
     * This method sends a new bike ride to the database, using the POST method.
     *
     * @param bikeRide TravelByBike object, representing a bike ride.
     * @return confirmation message. "CREATED" if successful, otherwise "ERROR".
     */
    public ResponseEntity<HttpStatus> sendTravelByBike(TravelByBike bikeRide) {
        HttpEntity<TravelByBike> entity = new HttpEntity<>(bikeRide, auth.makeHeader());
        return restTemplate
            .exchange(generalUrl + bikeUrl, HttpMethod.POST, entity, HttpStatus.class);
    }

    /**
     * This method sends a new PublicTransport instance to database, using POST method.
     *
     * @param pubTrans PublicTransport object, representing a public transport journey.
     * @return confirmation message. "CREATED" if successful, otherwise "ERROR".
     */
    public ResponseEntity<HttpStatus> sendPublicTransport(PublicTransport pubTrans) {
        HttpEntity<PublicTransport> entity = new HttpEntity<>(pubTrans, auth.makeHeader());
        return restTemplate
            .exchange(generalUrl + ptUrl, HttpMethod.POST, entity, HttpStatus.class);
    }

    /**
     * This method updates an existing TravelByBike object in the database.
     *
     * @param bikeRide  TravelByBike object containing updated information.
     * @param id        id of the target to be updated.
     * @return confirmation message. "UPDATED" if successful, otherwise "ERROR".
     */
    public ResponseEntity<HttpStatus> updateBikeRideById(TravelByBike bikeRide, long id) {
        String url = generalUrl + bikeUrl + id;
        HttpEntity<TravelByBike> entity = new HttpEntity<>(bikeRide, auth.makeHeader());
        return restTemplate
            .exchange(url, HttpMethod.PUT, entity, HttpStatus.class);
    }

    /**
     * This method updates an existing PublicTransport object in the database.
     *
     * @param pubTrans  PublicTransport object containing updated information.
     * @param id        id of the target to be updated.
     * @return confirmation message. "UPDATED" if successful, otherwise "ERROR".
     */
    public ResponseEntity<HttpStatus> updatePublicTransportById(PublicTransport pubTrans, long id) {
        String url = generalUrl + ptUrl + id;
        HttpEntity<PublicTransport> entity = new HttpEntity<>(pubTrans, auth.makeHeader());
        return restTemplate
            .exchange(url, HttpMethod.PUT, entity, HttpStatus.class);
    }

    /**
     * This method deletes a bike ride from the database.
     *
     * @param id of target to be deleted.
     * @return confirmation message.
     */
    public ResponseEntity<HttpStatus> deleteBikeRideById(long id) {
        String url = generalUrl + bikeUrl + id;
        return restTemplate.exchange(url, HttpMethod.DELETE,
                auth.makeEntity(auth.makeHeader()), HttpStatus.class);
    }

    /**
     * This method deletes a public transport journey from the database.
     *
     * @param id of target to be deleted.
     * @return confirmation message.
     */
    public ResponseEntity<HttpStatus> deletePublicTransportById(long id) {
        String url = generalUrl + ptUrl + id;
        return restTemplate.exchange(url, HttpMethod.DELETE,
                auth.makeEntity(auth.makeHeader()), HttpStatus.class);
    }

}
