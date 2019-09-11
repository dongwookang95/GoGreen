package backend.service.tab;

import backend.entity.Habit;
import backend.entity.habit.energy.LowerTemperature;
import backend.entity.habit.energy.SolarPanel;
import backend.entity.habit.food.LocalProduce;
import backend.entity.habit.food.VegetarianMeal;
import backend.entity.habit.structure.Attribute;
import backend.entity.habit.structure.Category;
import backend.entity.habit.structure.Subcategory;
import backend.entity.habit.transport.PublicTransport;
import backend.entity.habit.transport.TravelByBike;
import backend.service.AuthHeader;
import backend.service.HomeRequestHandler;
import backend.service.habit.EnergyRequestHandler;
import backend.service.habit.FoodRequestHandler;
import backend.service.habit.TransportRequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Service to provide functional support to all the tabs that need information about habits.
 * Can retrieve/send habits from/to server and provide structural information.
 */
@Getter
public class HabitRequestHandler extends HomeRequestHandler {
    private String generalUrl = "https://ooppgogreen99.herokuapp.com/v1/";
    private final String lpUrl = generalUrl + "food/local_produce/";
    private final String vmUrl = generalUrl + "food/vegetarian_meal/";

    private AuthHeader auth = new AuthHeader();
    private RestTemplate restTemplate = new RestTemplate();

    private final JsonNode structure = retrieveStructure();
    private final ArrayList<Category> categoryTree = retrieveCategoryTree();

    private FoodRequestHandler foodHandler = FoodRequestHandler.getInstance();
    private EnergyRequestHandler energyHandler = EnergyRequestHandler.getInstance();
    private TransportRequestHandler transportHandler = TransportRequestHandler.getInstance();

    /**
     * Private constructor to define the service in the singleton design pattern.
     */
    private HabitRequestHandler() { }

    /**
     * Set url of the EntryRequestHandler.
     * Needed for testing with Wiremock.
     *
     * @param url of localhost
     */
    public void setGeneralUrl(String url) {
        this.generalUrl = url;
    }


    /**
     * Helper class to avoid concurrent threads to request the same resource at the same time.
     */
    private static class HabitRequestHelper {
        private static final HabitRequestHandler INSTANCE = new HabitRequestHandler();
    }

    /**
     * Retrieves the instance for the habit service.
     * @return final INSTANCE of the service
     */
    public static HabitRequestHandler getInstance() {
        return HabitRequestHelper.INSTANCE;
    }

    /**
     * Retrieves the category structure in a JsonNode.
     * @return JsonNode - the structure of the habits
     */
    private JsonNode retrieveStructure() {
        HttpEntity entity = auth.makeEntity(auth.makeHeader());
        return restTemplate.exchange(generalUrl + "habit/category/all",
                HttpMethod.GET, entity, JsonNode.class).getBody();
    }

    /**
     * Creates the whole category structure from the JsonNode.
     * Using the JsonNode initialized in retrieveStructure(), this method
     * creates the category tree as currently instantiated in the database,
     * including subcategories and attributes.
     * @return ArrayList of categories
     */
    private ArrayList<Category> retrieveCategoryTree() {
        ArrayList<Category> categoryList = new ArrayList<>();
        for (int i = 0; i < structure.size(); i++) {
            JsonNode category = structure.get(i);
            Category cat = new Category();
            //TODO: Use ObjectMapper to set Category
            cat.setAttributesFromJson(category);
            categoryList.add(cat);
        }
        return categoryList;
    }

    /**
     * Creates a list of categories from CategoryTree as strings.
     * Used to populate ComboBox(es) in controllers.
     * @return list of category descriptions
     */
    public ArrayList<String> createCategoryDescriptionList() {
        ArrayList<String> list = new ArrayList<>();
        for (Category category : categoryTree) {
            list.add(category.getDescription());
        }
        return list;
    }

    /**
     * Creates a list containing the subcategories of the selected category.
     * Using the CategoryTree field, searches the corresponding category
     * to retrieve the subcategories from.
     * @return ArrayList of subcategories
     */
    public ArrayList<Subcategory> retrieveSubcategoryList(String cat) {
        ArrayList<Subcategory> subCatList = new ArrayList<>();
        for (Category category : categoryTree) {
            if (category.getDescription().equals(cat)) {
                subCatList.addAll(category.getSubCatList());
                break;
            }
        }
        return subCatList;
    }

    /**
     * Creates a list of subcategories from CategoryTree as strings.
     * Used to populate ComboBox(es) in controllers.
     * @return list of subcategory descriptions
     */
    public ArrayList<String> createSubcategoryDescriptionList(String catString) {
        ArrayList<String> list = new ArrayList<>();
        for (Subcategory subCategory : retrieveSubcategoryList(catString)) {
            list.add(subCategory.getDescription());
        }
        return list;
    }

    /**
     * Creates a list containing the attributes of the selected subcategory.
     * Using the list fetched in retrieveSubcategoryList method, searches the corresponding
     * subcategory to retrieve the attributes from.
     * @return ArrayList of attributes
     */
    public ArrayList<Attribute> retrieveAttributesList(String subcategory,
                                                       ArrayList<Subcategory> subCatList) {
        ArrayList<Attribute> attributesList = new ArrayList<>();
        for (Subcategory subCategory : subCatList) {
            if (subCategory.getDescription().equals(subcategory)) {
                attributesList.addAll(subCategory.getAttributesList());
                break;
            }
        }
        return attributesList;
    }

    /**
     * Transform the Label-TextField Map into a String-String Map.
     * @param sceneAttributePairs Label-TextField Map to be transformed.
     * @return Transformed String-String Map of attributes.
     */
    public ArrayList<Map.Entry<String,String>> makeAttributePairsStringList(
            ArrayList<Map.Entry<Label,TextField>> sceneAttributePairs) {

        ArrayList<Map.Entry<String,String>> attributePairs = new ArrayList<>();
        for (Map.Entry<Label, TextField> scenePair : sceneAttributePairs) {
            Map.Entry<String, String> pair = new AbstractMap.SimpleEntry<>(
                    scenePair.getKey().getText(), scenePair.getValue().getText()
            );
            attributePairs.add(pair);
        }
        return attributePairs;
    }

    /**
     * Define which method should be called depending on
     * the type of habit received in the parameters.
     * @param subCategory Subcategory of the habit
     * @return Status string : Created, Updated or Error.
     * @throws NumberFormatException When amount is not a number
     */
    public String sendHabit(String subCategory, ArrayList<Map.Entry<String,String>> attributes)
            throws NumberFormatException {

        switch (subCategory) {
            case "Local Produce":
                LocalProduce locProd = new LocalProduce();
                for (Map.Entry<String,String> pair : attributes) {
                    locProd.setAttributeFromPair(pair);
                }
                return checkStatusCode(foodHandler.sendLocalProduce(locProd));
            case "Vegetarian Meal":
                VegetarianMeal vegMeal = new VegetarianMeal();
                for (Map.Entry<String, String> pair : attributes) {
                    vegMeal.setAttributeFromPair(pair);
                }
                return checkStatusCode(foodHandler.sendVegetarianMeal(vegMeal));
            case "Lower Temperature":
                LowerTemperature lowTemp = new LowerTemperature();
                for (Map.Entry<String, String> pair : attributes) {
                    lowTemp.setAttributeFromPair(pair);
                }
                return checkStatusCode(energyHandler.sendLowerTemperature(lowTemp));
            case "Solar Panel":
                SolarPanel solPan = new SolarPanel();
                for (Map.Entry<String, String> pair : attributes) {
                    solPan.setAttributeFromPair(pair);
                }
                return checkStatusCode(energyHandler.sendSolarPanel(solPan));
            case "Public Transport":
                PublicTransport pubTrans = new PublicTransport();
                for (Map.Entry<String, String> pair : attributes) {
                    pubTrans.setAttributeFromPair(pair);
                }
                return checkStatusCode(transportHandler.sendPublicTransport(pubTrans));
            case "Travel By Bike":
                TravelByBike byBike = new TravelByBike();
                for (Map.Entry<String, String> pair : attributes) {
                    byBike.setAttributeFromPair(pair);
                }
                return checkStatusCode(transportHandler.sendTravelByBike(byBike));
            default:
                return "";
        }
    }

    /**
     * This method must be changed.
     * @param habit Habit to be edited
     * @param subcategory Subcategory of the habit to be edited
     * @param attributePairs List of Map pairs containing the habit's attributes
     * @return A string containing the response based on the HttpStatus of the request
     */
    //TODO: Find an alternative solution
    public String editHabit(Habit habit, String subcategory,
                          ArrayList<Map.Entry<String,String>> attributePairs) {
        switch (subcategory) {
            case "Local Produce":
                LocalProduce locProd = (LocalProduce) habit;
                for (Map.Entry<String, String> pair : attributePairs) {
                    locProd.setAttributeFromPair(pair);
                }
                return checkStatusCode(foodHandler.updateLocProdById(locProd, locProd.getId()));

            case "Vegetarian Meal":
                VegetarianMeal vegMeal = (VegetarianMeal) habit;
                for (Map.Entry<String, String> pair : attributePairs) {
                    vegMeal.setAttributeFromPair(pair);
                }
                return checkStatusCode(foodHandler.updateVegMealById(vegMeal, vegMeal.getId()));

            case "Lower Temperature":
                LowerTemperature lowTemp = (LowerTemperature) habit;
                for (Map.Entry<String, String> pair : attributePairs) {
                    lowTemp.setAttributeFromPair(pair);
                }
                return checkStatusCode(
                    energyHandler.updateTemperatureById(lowTemp, lowTemp.getId()));

            case "Solar Panel":
                SolarPanel solPan = (SolarPanel) habit;
                for (Map.Entry<String, String> pair : attributePairs) {
                    solPan.setAttributeFromPair(pair);
                }
                return checkStatusCode(
                    energyHandler.updateSolarPanelById(solPan, solPan.getId()));

            case "Public Transport":
                PublicTransport pubTrans = (PublicTransport) habit;
                for (Map.Entry<String, String> pair : attributePairs) {
                    pubTrans.setAttributeFromPair(pair);
                }
                return checkStatusCode(
                    transportHandler.updatePublicTransportById(pubTrans, pubTrans.getId()));

            case "Travel By Bike":
                TravelByBike byBike = (TravelByBike) habit;
                for (Map.Entry<String, String> pair : attributePairs) {
                    byBike.setAttributeFromPair(pair);
                }
                return checkStatusCode(
                    transportHandler.updateBikeRideById(byBike, byBike.getId()));

            default:
                return "";
        }
    }

    /**
     * This method checks the status code inside a server response.
     * @param status ResponseEntity of type ServerStatus
     * @return Created/Updated/Error message
     */
    private String checkStatusCode(ResponseEntity<HttpStatus> status) {
        switch (status.getStatusCode().value()) {
            case 201:
                return "Created";
            case 204:
                return "Updated";
            default:
                return "Error";
        }

    }
}
