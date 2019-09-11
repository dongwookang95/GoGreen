package backend.service.habit;

import backend.entity.habit.energy.LowerTemperature;
import backend.entity.habit.energy.SolarPanel;
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
 * This class handles all the requests that belong to energy related habits.
 */
@Getter
@Setter
public class EnergyRequestHandler {
    private String generalUrl = "https://ooppgogreen99.herokuapp.com/v1";
    private final String tempUrl = "/energy/lower_temperature/";
    private final String solarUrl = "/energy/solar_panel/";
    private AuthHeader auth = new AuthHeader();
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
    private HabitRequestHandler habitHandler = HabitRequestHandler.getInstance();

    /**
     * Private constructor to define the service in the singleton design pattern.
     */
    private EnergyRequestHandler() {
    }

    /**
     * Helper class to avoid concurrent threads to request the same resource at the same time.
     */
    private static class EnergyRequestHelper {
        private static final EnergyRequestHandler INSTANCE = new EnergyRequestHandler();
    }
    
    /**
     * This method creates an ArrayList of LowerTemperature/SolarPanel,
     * using readValue() from ObjectMapper.
     *
     * @param url       request path
     * @return ArrayList of LowerTemperature/SolarPanel objects
     */
    private ArrayList getEnergyHabit(String url) {
        ArrayList list = new ArrayList<>();
        HttpEntity entity = auth.makeEntity(auth.makeHeader());
        JsonNode jsonNode = restTemplate.exchange(
                generalUrl + url,
                HttpMethod.GET,
                entity,
                JsonNode.class)
            .getBody();

        String energyString = jsonNode != null ? jsonNode.toString() : "";
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            if (url == tempUrl) {
                list = objectMapper
                    .readValue(energyString, new TypeReference<ArrayList<LowerTemperature>>(){});
            } else if (url == solarUrl) {
                list = objectMapper
                    .readValue(energyString, new TypeReference<ArrayList<SolarPanel>>(){});
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
    public static EnergyRequestHandler getInstance() {
        return EnergyRequestHelper.INSTANCE;
    }

    /**
     * This method retrieves all the times the user lowered the temperature.
     *
     * @return ArrayList of LowerTemperature objects.
     */
    public ArrayList<LowerTemperature> getUserTemperatures() {
        return getEnergyHabit(tempUrl);
    }

    /**
     * This method retrieves all solar panel installations of the user.
     * NOTE: needs to fixed on server.
     *
     * @return ArrayList of SolarPanel objects.
     */
    public ArrayList<SolarPanel> getUserSolarPanels() {
        return getEnergyHabit(solarUrl);
    }

    /**
     * This method sends a new LowerTemperature object to the database, using the POST method.
     *
     * @param temp LowerTemperature object, representing a lowering the temperature.
     * @return confirmation message. "CREATED" if successful, otherwise "ERROR".
     */
    public ResponseEntity<HttpStatus> sendLowerTemperature(LowerTemperature temp) {
        HttpEntity<LowerTemperature> entity = new HttpEntity<>(temp, auth.makeHeader());
        return restTemplate
            .exchange(generalUrl + tempUrl, HttpMethod.POST, entity, HttpStatus.class);
    }

    /**
     * This method sends a new SolarPanel instance to the database, using POST method.
     *
     * @param panel SolarPanel object, representing a solar panel(s).
     * @return confirmation message. "CREATED" if successful, otherwise "ERROR".
     */
    public ResponseEntity<HttpStatus> sendSolarPanel(SolarPanel panel) {
        HttpEntity<SolarPanel> entity = new HttpEntity<>(panel, auth.makeHeader());
        return restTemplate
            .exchange(generalUrl + solarUrl, HttpMethod.POST, entity, HttpStatus.class);
    }

    /**
     * This method updates an existing LowerTemperature object in the database.
     *
     * @param temp  LowerTemperature object containing updated information.
     * @param id    id of the target to be updated.
     * @return confirmation message. "UPDATED" if successful, otherwise "ERROR".
     */
    public ResponseEntity<HttpStatus> updateTemperatureById(LowerTemperature temp, long id) {
        String url = generalUrl + tempUrl + id;
        HttpEntity<LowerTemperature> entity = new HttpEntity<>(temp, auth.makeHeader());
        return restTemplate
            .exchange(url, HttpMethod.PUT, entity, HttpStatus.class);
    }

    /**
     * This method updates an existing SolarPanel object in the database.
     *
     * @param panel PublicTransport object containing updated information.
     * @param id    id of the target to be updated.
     * @return confirmation message. "UPDATED" if successful, otherwise "ERROR".
     */
    public ResponseEntity<HttpStatus> updateSolarPanelById(SolarPanel panel, long id) {
        String url = generalUrl + solarUrl + id;
        HttpEntity<SolarPanel> entity = new HttpEntity<>(panel, auth.makeHeader());
        return restTemplate
            .exchange(url, HttpMethod.PUT, entity, HttpStatus.class);
    }

    /**
     * This method deletes a LowerTemperature object from the database.
     *
     * @param id of target to be deleted.
     * @return confirmation message.
     */
    public ResponseEntity<HttpStatus> deleteLowerTemperatureById(long id) {
        String url = generalUrl + tempUrl + id;
        return restTemplate.exchange(url, HttpMethod.DELETE,
                auth.makeEntity(auth.makeHeader()), HttpStatus.class);
    }

    /**
     * This method deletes a SolarPanel object from the database.
     *
     * @param id of target to be deleted.
     * @return confirmation message.
     */
    public ResponseEntity<HttpStatus> deleteSolarPanelById(long id) {
        String url = generalUrl + solarUrl + id;
        return restTemplate.exchange(url, HttpMethod.DELETE,
                auth.makeEntity(auth.makeHeader()), HttpStatus.class);
    }
    
}
