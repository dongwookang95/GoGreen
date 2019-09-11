package backend.service.habit;

import backend.entity.habit.food.LocalProduce;
import backend.entity.habit.food.VegetarianMeal;
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

@Getter
@Setter
public class FoodRequestHandler {
    private String generalUrl = "https://ooppgogreen99.herokuapp.com/v1";
    private final String lpUrl = "/food/local_produce/";
    private final String vmUrl = "/food/vegetarian_meal/";
    private AuthHeader auth = new AuthHeader();
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
    private HabitRequestHandler habitHandler = HabitRequestHandler.getInstance();

    /**
     * Private constructor to define the service in the singleton design pattern.
     */
    private FoodRequestHandler() {
    }

    /**
     * Helper class to avoid concurrent threads to request the same resource at the same time.
     */
    private static class FoodRequestHelper {
        private static final FoodRequestHandler INSTANCE = new FoodRequestHandler();
    }
    
    /**
     * This method creates an ArrayList of VegetarianMeal/LocalProduce,
     * using readValue() from ObjectMapper.
     *
     * @param url       request path
     * @return ArrayList of VegetarianMeal/LocalProduce objects
     */
    private ArrayList getFoodHabit(String url) {
        ArrayList list = new ArrayList<>();
        HttpEntity entity = auth.makeEntity(auth.makeHeader());
        JsonNode jsonNode = restTemplate.exchange(
                generalUrl + url,
                HttpMethod.GET, entity,
                JsonNode.class
        ).getBody();

        //DO NOT IGNORE NULL POINTER EXCEPTIONS FFS.
        String foodString = jsonNode != null ? jsonNode.toString() : "";

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            if (url == vmUrl) {
                list = objectMapper.readValue(
                        foodString,
                        new TypeReference<ArrayList<VegetarianMeal>>(){}
                        );
            } else if (url == lpUrl) {
                list = objectMapper.readValue(
                        foodString,
                        new TypeReference<ArrayList<LocalProduce>>(){}
                        );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }  

    /**
     * Retrieves the instance for the habit service.
     * @return final INSTANCE of the service
     */
    public static FoodRequestHandler getInstance() {
        return FoodRequestHelper.INSTANCE;
    }

    /**
     * Retrieves all vegetarian meals of the user that is logged in.
     *
     * @return ArrayList of vegetarian meals.
     */
    public ArrayList<VegetarianMeal> getUserVegetarianMeals() {
        return getFoodHabit(vmUrl);
    }

    /**
     * Retrieves all local produces of the user that is logged in.
     * 
     * @return ArrayList of local produces.
     */
    public ArrayList<LocalProduce> getUserLocalProduces() {
        return getFoodHabit(lpUrl);
    }

    /**
     * Updating vegetarian meal of current user, using the id of that meal.
     *
     * @param vegMeal the new vegetarian meal
     * @param id      of target to be updated
     * @return success/fail message
     */
    public ResponseEntity<HttpStatus> updateVegMealById(VegetarianMeal vegMeal, long id) {
        String url = generalUrl + vmUrl + id;
        HttpEntity<VegetarianMeal> entity = new HttpEntity<>(vegMeal, auth.makeHeader());
        return restTemplate
            .exchange(url, HttpMethod.PUT, entity, HttpStatus.class);
    }

    /**
     * Updating a local produce of current user, using the id of the local produce.
     * @param locProd the new local produce
     * @param id of target to be updated
     * @return success/fail message
     */
    public ResponseEntity<HttpStatus> updateLocProdById(LocalProduce locProd, long id) {
        String url = generalUrl + lpUrl + id;
        HttpEntity<LocalProduce> entity = new HttpEntity<>(locProd, auth.makeHeader());
        return restTemplate
            .exchange(url, HttpMethod.PUT, entity, HttpStatus.class);
    }

    /**
     * Deletes a vegetarian meal by id.
     * @param id id of vegetarian meal.
     * @return success/fail message
     */
    public ResponseEntity<HttpStatus> deleteVegMealById(long id) {
        String url = generalUrl + vmUrl + id;
        return restTemplate.exchange(url, HttpMethod.DELETE,
                auth.makeEntity(auth.makeHeader()), HttpStatus.class);
    }

    /**
     * Deletes a local produce by id.
     * @param id id of local produce
     * @return success/fail message
     */
    public ResponseEntity<HttpStatus> deleteLocProdById(long id) {
        String url = generalUrl + lpUrl + id;
        return restTemplate.exchange(url, HttpMethod.DELETE,
                auth.makeEntity(auth.makeHeader()), HttpStatus.class);
    }

    /**
     * Adding a new VegetarianMeal object to the DB.
     * @param vegMeal VegetarianMeal object to be sent, following the VGForm format from server.
     * @return Success/fail message
     */
    public ResponseEntity<HttpStatus> sendVegetarianMeal(VegetarianMeal vegMeal) {
        HttpEntity<VegetarianMeal> entity = new HttpEntity<>(vegMeal, auth.makeHeader());
        return restTemplate
            .exchange(generalUrl + vmUrl, HttpMethod.POST, entity, HttpStatus.class);
    }

    /**
     * Adding a new LocalProduce object to the DB, through a POST request.
     * @param locProd LocalProduce to be sent, following the LocalProduceForm format from server.
     * @return Success/fail message
     */
    public ResponseEntity<HttpStatus> sendLocalProduce(LocalProduce locProd) {
        HttpEntity<LocalProduce> entity = new HttpEntity<>(locProd, auth.makeHeader());
        return restTemplate
            .exchange(generalUrl + lpUrl, HttpMethod.POST, entity, HttpStatus.class);
    }
}
