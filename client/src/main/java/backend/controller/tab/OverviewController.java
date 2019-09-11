package backend.controller.tab;

import backend.controller.HomeController;
import backend.entity.habit.structure.OverviewElement;
import backend.service.UserRequestsHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Overview tab in SceneTwo.
 * It fills the overview table on startup with the user's registered habits,
 * trigger EventHandler methods for habit inspections and finally
 * allows the user to modify old habits for minor corrections.
 */
public class OverviewController extends HomeController implements Initializable {

    @FXML
    private Button reload;
    @FXML
    private TableView<OverviewElement> view;
    @FXML
    private TableColumn<OverviewElement, String> col1;
    @FXML
    private TableColumn<OverviewElement, Double> col2;

    private UserRequestsHandler userHandler = UserRequestsHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle bundle) {
        view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        reload.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> reload());
        col1.setCellValueFactory(new PropertyValueFactory<>("description"));
        col2.setCellValueFactory(new PropertyValueFactory<>("total"));
        setTableElements();
    }

    /**
     * Event handler of the reload button
     * When pressed, it reloads the current user and shows updated values.
     */
    private void reload() {
        userHandler.reloadMeInstance();
        setTableElements();
    }

    /**
     * Helper method to set elements in the table.
     */
    private void setTableElements() {
        view.getItems().clear();
        view.getItems().setAll(userHandler.getMe().getOverview());
    }
}
