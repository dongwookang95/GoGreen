package backend.controller.tab;

import backend.controller.HomeController;
import backend.entity.Habit;
import backend.entity.habit.structure.Attribute;
import backend.entity.habit.structure.Subcategory;
import backend.service.habit.EnergyRequestHandler;
import backend.service.habit.FoodRequestHandler;
import backend.service.habit.TransportRequestHandler;
import backend.service.tab.HabitRequestHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class EditHabitController extends HomeController implements Initializable {
    @FXML
    private ComboBox categoryComboBox;
    @FXML
    private ComboBox subcategoryComboBox;
    @FXML
    private Button edit;
    @FXML
    private TableView<Habit> view;
    @FXML
    private Pane bottomPane;
    @FXML
    private Button save;

    private HabitRequestHandler habitHandler = HabitRequestHandler.getInstance();
    private FoodRequestHandler foodHandler = FoodRequestHandler.getInstance();
    private EnergyRequestHandler energyHandler = EnergyRequestHandler.getInstance();
    private TransportRequestHandler transportHandler = TransportRequestHandler.getInstance();

    private ArrayList<Subcategory> subCatList = new ArrayList<>();
    private ArrayList<Map.Entry<Label,TextField>> sceneAttributePairs = new ArrayList<>();

    /**
     * Helper method to populate the category ComboBox with all categories.
     */
    private void populateCategoryComboBox() {
        categoryComboBox.getItems().addAll(habitHandler.createCategoryDescriptionList());
    }

    /**
     * Event handler for catCBox in the EditHabit scene.
     * Once a category is chosen, a list of subcategories is loaded into
     * the subCBox.
     */
    private void populateSubcategoryComboBox() {
        // Clear the ComboBox from possible previous selections.
        if (!subCatList.isEmpty()) {
            clearSubCatCBox();
        }
        // Retrieve the subcategory list based on category selection
        String cat = categoryComboBox.getValue().toString();
        subcategoryComboBox.getItems().addAll(habitHandler.createSubcategoryDescriptionList(cat));

        subCatList = habitHandler.retrieveSubcategoryList(cat);
    }

    /**
     * Clear subcategory list and ComboBox's selection list.
     */
    private void clearSubCatCBox() {
        subCatList.clear();
        subcategoryComboBox.getItems().clear();
    }

    /**
     * Event handler for the edit button in the EditHabit scene.
     * Once category and subcategory are chosen, and a button is pressed,
     * a list of habits is shown in the table.
     */
    private void setTableElements() {
        if (categoryComboBox.getItems().isEmpty() || subcategoryComboBox.getItems().isEmpty()) {
            //TODO: Show label with response
            System.out.println("All fields must be defined");
        } else {
            TableColumn<Habit, Integer> numberOfMeals = new TableColumn<>("Number of Meals");
            numberOfMeals.setCellValueFactory(new PropertyValueFactory<>("numberOfMeals"));

            TableColumn<Habit, Double> kilometers = new TableColumn<>("Kilometers");
            kilometers.setCellValueFactory(new PropertyValueFactory<>("kilometers"));

            TableColumn<Habit, String> transportTypeInstead =
                    new TableColumn<>("Alternative Vehicle");
            transportTypeInstead.setCellValueFactory(
                    new PropertyValueFactory<>("transportTypeInstead"));

            TableColumn<Habit, String> transportTypeActual = new TableColumn<>("Actual Vehicle");
            transportTypeActual.setCellValueFactory(
                    new PropertyValueFactory<>("transportTypeActual"));

            TableColumn<Habit, Double> hours = new TableColumn<>("Hours");
            hours.setCellValueFactory(new PropertyValueFactory<>("hours"));

            TableColumn<Habit, Integer> watt = new TableColumn<>("Watts");
            watt.setCellValueFactory(new PropertyValueFactory<>("watt"));

            TableColumn<Habit, Integer> numberOfSolarPanels =
                    new TableColumn<>("Number of Solar Panels");
            numberOfSolarPanels.setCellValueFactory(
                    new PropertyValueFactory<>("numberOfSolarPanels"));

            TableColumn<Habit, Double> degrees = new TableColumn<>("Degrees");
            degrees.setCellValueFactory(new PropertyValueFactory<>("degrees"));

            String subcategory = subcategoryComboBox.getValue().toString();
            switch (subcategory) {
                case "Vegetarian Meal":
                    clearTableIfUsed(view);
                    view.getColumns().add(numberOfMeals);
                    setDefaultColumns(view);
                    view.getItems().setAll(foodHandler.getUserVegetarianMeals());
                    break;
                case "Local Produce":
                    clearTableIfUsed(view);
                    view.getColumns().add(numberOfMeals);
                    setDefaultColumns(view);
                    view.getItems().setAll(foodHandler.getUserLocalProduces());
                    break;
                case "Lower Temperature":
                    clearTableIfUsed(view);
                    view.getColumns().add(hours);
                    view.getColumns().add(degrees);
                    setDefaultColumns(view);
                    view.getItems().setAll(energyHandler.getUserTemperatures());
                    break;
                case "Solar Panels":
                    clearTableIfUsed(view);
                    view.getColumns().add(hours);
                    view.getColumns().add(watt);
                    view.getColumns().add(numberOfSolarPanels);
                    setDefaultColumns(view);
                    view.getItems().setAll(energyHandler.getUserSolarPanels());
                    break;
                case "Public Transport":
                    clearTableIfUsed(view);
                    view.getColumns().add(kilometers);
                    view.getColumns().add(transportTypeActual);
                    view.getColumns().add(transportTypeInstead);
                    setDefaultColumns(view);
                    view.getItems().setAll(transportHandler.getUserPublicTransports());
                    break;
                case "Travel By Bike":
                    clearTableIfUsed(view);
                    view.getColumns().add(kilometers);
                    view.getColumns().add(transportTypeInstead);
                    setDefaultColumns(view);
                    view.getItems().setAll(transportHandler.getUserBikeRides());
                    break;
                default:
                    break;
            }
            setBottomPane(subcategory, subCatList);
        }
    }

    /**
     * Helper method to clear the table when parameters have changed.
     * @param view TableView to be cleared
     */
    private void clearTableIfUsed(TableView view) {
        if (view.getColumns().size() != 0) {
            view.getColumns().clear();
        }
    }

    private void setDefaultColumns(TableView<Habit> view) {
        TableColumn<Habit, Double> amount = new TableColumn<>("Amount");
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        view.getColumns().add(amount);
        TableColumn<Habit, Double> date = new TableColumn<>("Creation Date");
        date.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        view.getColumns().add(date);
    }

    /**
     * Method called inside the event handler for the edit button.
     * Given a subcategory name, it finds the corresponding subcategory
     * to extract the attribute list and dynamically draw enough
     * attribute fields in the bottom pane.
     * @param subcategory Name of the category to be found
     * @param subCatList List of subcategories
     */
    private void setBottomPane(String subcategory, ArrayList<Subcategory> subCatList) {
        ArrayList<Attribute> attributeList = habitHandler
            .retrieveAttributesList(subcategory, subCatList);

        if (!bottomPane.getChildren().isEmpty()) {
            bottomPane.getChildren().removeAll(bottomPane.getChildren());
            bottomPane.getChildren().add(save);
        }

        for (int i = 0; i < attributeList.size(); i++) {
            setAttributePane(i, attributeList.get(i).getDescription());
        }
    }

    /**
     * Method called inside the setBottomPane method.
     * For every attribute found in the attribute list, this method
     * creates its wrapper and defines a label and text field for it.
     * These elements are then retrieved through a Map (key, value) to
     * be analyzed and sent to the server.
     * @param offsetX Offset multiplier for the x-axis
     * @param attributeName Name of the attribute to assign elements to
     */
    private void setAttributePane(int offsetX, String attributeName) {
        Pane pane = new Pane();
        pane.getStyleClass().add("two-edit-attribute-pane");

        attributeName = attributeName.substring(0,1).toUpperCase() + attributeName.substring(1);
        Label name = new Label(attributeName);
        name.getStyleClass().add("two-edit-label");
        pane.getChildren().add(name);

        TextField text = new TextField();
        text.getStyleClass().add("two-edit-text");
        text.setLayoutX(80);
        pane.getChildren().add(text);

        pane.setLayoutX(offsetX * 230);

        bottomPane.getChildren().add(pane);
        Map.Entry<Label,TextField> pair = new AbstractMap.SimpleEntry<>(name, text);
        sceneAttributePairs.add(pair);

        bottomPane.setVisible(true);
    }

    /**
     * Event handler for the submit button in the EditHabit scene.
     * When the button is pressed, input is retrieved from TextField(s)
     * and a request to server is made to edit the specified habit.
     */
    private void updateHabit() {
        ArrayList<Map.Entry<String,String>> attributePairs =
                habitHandler.makeAttributePairsStringList(sceneAttributePairs);
        String subcategory = subcategoryComboBox.getValue().toString();
        Habit habit = view.getSelectionModel().getSelectedItem();

        String response;
        try {
            response = habitHandler.editHabit(habit, subcategory, attributePairs);
        } catch (NumberFormatException e) {
            response = "Not a number";
        }

        System.out.println(response);
        setTableElements();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bottomPane.setVisible(false);
        populateCategoryComboBox();
        view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        categoryComboBox.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> populateSubcategoryComboBox());
        edit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> setTableElements());
        save.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> updateHabit());
    }
}
