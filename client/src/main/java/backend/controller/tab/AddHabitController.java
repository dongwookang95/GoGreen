package backend.controller.tab;

import backend.controller.HomeController;
import backend.entity.habit.structure.Attribute;
import backend.entity.habit.structure.Subcategory;
import backend.service.tab.HabitRequestHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller for the AddHabit tab in SceneTwo.
 * It takes care of loading the AddHabit scene with information retrieved from the server,
 * dynamically changes ComboBox content depending on user's choices and finally
 * sends the newly created habit to the server.
 */
public class AddHabitController extends HomeController implements Initializable {
    @FXML
    private Button submit;
    @FXML
    private ComboBox categoryComboBox;
    @FXML
    private ComboBox subcategoryComboBox;
    @FXML
    private Label responseLabel;
    @FXML
    private Pane attributesPane;
    @FXML
    private Label attributesLabel;

    private HabitRequestHandler habitHandler;
    private ArrayList<Subcategory> subCatList = new ArrayList<>();
    private ArrayList<Map.Entry<Label,TextField>> sceneAttributePairs = new ArrayList<>();


    /**
     * Event handler for the submit button in the habit scene.
     * When the user clicks to add a new habit, information are sent to the server
     * to be stored.
     */
    private void submitHabit() {
        String subCategory = subcategoryComboBox.getValue().toString();
        ArrayList<Map.Entry<String,String>> attributePairs =
                habitHandler.makeAttributePairsStringList(sceneAttributePairs);

        // Provide the previously made list to service method as attributes list
        String response;
        try {
            response = habitHandler.sendHabit(subCategory, attributePairs);
        } catch (NumberFormatException | HttpClientErrorException e) {
            response = "Wrong input";
        }

        responseLabel.setText(response);

        attributesPane.getChildren().clear();
        sceneAttributePairs.clear();

    }

    /**
     * Helper method to populate the category ComboBox with all categories.
     */
    private void populateCategoryComboBox() {
        categoryComboBox.getItems().addAll(habitHandler.createCategoryDescriptionList());
    }

    /**
     * Event handler for mainCategoryCBox in the habit scene.
     * Once a category is chosen, a list of subcategories is loaded into
     * the subcategoryCBox.
     */
    private void populateSubcategoryComboBox() {
        if (!subCatList.isEmpty()) {
            clearSubCatCBox();
        }

        String cat = categoryComboBox.getValue().toString();
        subCatList = habitHandler.retrieveSubcategoryList(cat);

        subcategoryComboBox.getItems().addAll(habitHandler.createSubcategoryDescriptionList(cat));
    }

    /**
     * Clear subcategory list and ComboBox's selection list.
     */
    private void clearSubCatCBox() {
        subCatList.clear();
        subcategoryComboBox.getItems().clear();
    }

    /**
     * Event handler for the subCategoryCBox in the habit scene.
     * Once a subcategory is chosen, enough Label(s) and TextField(s) are loaded
     * depending on the amount of attributes a subcategory has.
     */
    private void setAttributesPanes() {
        String subCat = "";
        if (!subcategoryComboBox.getItems().isEmpty()) {
            subCat = subcategoryComboBox.getValue().toString();
        }

        ArrayList<Attribute> attributeList = habitHandler.retrieveAttributesList(subCat,subCatList);
        if (!attributesPane.getChildren().isEmpty()) {
            attributesPane.getChildren().clear();
            sceneAttributePairs.clear();
        }

        for (int i = 0; i < attributeList.size(); i++) {
            setAttributePane(i, attributeList.get(i).getDescription());
        }
        if (subCat.equals("Public Transport") || subCat.equals("Travel By Bike")) {
            String instructions = " -- Brief instructions: -- \n"
                + "Transport types: \n Car, Bus, Tram, Metro, Train";
            attributesLabel.setText(instructions);
        }
    }

    /**
     * Helper method to set attribute panes.
     * Depending on the attribute list size, it creates enough panes
     * with a label and a text field for each attribute.
     * The label (attribute description) and the text field (attribute value)
     * are stored in a list of value pairs to be fetched later.
     * @param offsetY Vertical offset
     * @param attributeName Name of the attribute
     */
    private void setAttributePane(int offsetY, String attributeName) {
        Pane pane = new Pane();
        pane.getStyleClass().add("two-habit-attribute-pane");

        attributeName = attributeName.substring(0,1).toUpperCase() + attributeName.substring(1);
        Label name = new Label(attributeName);
        name.getStyleClass().add("two-habit-label");
        pane.getChildren().add(name);

        TextField text = new TextField();
        text.getStyleClass().add("two-habit-text");
        pane.getChildren().add(text);

        pane.setLayoutY(offsetY * 90);

        attributesPane.getChildren().add(pane);
        Map.Entry<Label,TextField> pair = new AbstractMap.SimpleEntry<>(name, text);
        sceneAttributePairs.add(pair);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        habitHandler = HabitRequestHandler.getInstance();
        populateCategoryComboBox();

        categoryComboBox.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> populateSubcategoryComboBox());
        subcategoryComboBox.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> setAttributesPanes());
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> submitHabit());
    }
}
