package backend.controller;

import backend.entity.Achievement;
import backend.entity.user.Me;
import backend.service.UserRequestsHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller parent for SceneTwo tabs.
 * It takes care of Logout, injecting the correct values for labels
 * in the FXML files and possible scene changes originated from Home.
 */
public class HomeController extends EntryController implements Initializable {
    @FXML
    private Button logout;
    @FXML
    private Label user;
    @FXML
    private Label title;
    @FXML
    private Label firstBadgeLabel;
    @FXML
    private Label secondBadgeLabel;
    @FXML
    private Label thirdBadgeLabel;
    @FXML
    private ImageView firstBadgeImage;
    @FXML
    private ImageView secondBadgeImage;
    @FXML
    private ImageView thirdBadgeImage;

    private UserRequestsHandler userHandler = UserRequestsHandler.getInstance();
    private Me me = userHandler.getMe();

    /**
     * Event handler for the logout button.
     * When the user clicks the logout button, a disconnection message is sent
     * and the connection is dismissed. The login scene is loaded.
     *
     * @throws IOException when the FXMLLoader cannot find the fxml file.
     */
    @FXML
    public void logout() throws IOException {
        String filepath = "../../fxml/scene/SceneOne.fxml";
        String stylePath = "../../css/style.css";
        //TODO: Logout message to server?
        Stage stage = (Stage) logout.getScene().getWindow();
        Scene sceneOne = createScene(filepath, stylePath, 600, 400);
        stage.setScene(sceneOne);
    }

    /**
     * Helper method to set an image to the corresponding badge.
     */
    //TODO: Waiting for the achievement-badge feature to be implemented.
    private void setBadges() {
        for (Achievement achievement : me.getAchievements()) {
            String pathname = achievement.getBadge().getPath();
            String category = achievement.getCategory().getDescription();
            Image image = new Image(getClass().getResource(
                    "../../img/badges/" + pathname + ".png").toExternalForm());

            if (category.equals("Food")) {
                String level = achievement.getBadge().getName();
                String text = level.substring(0, 1).toUpperCase()
                    + level.substring(1) + " Cabbage";
                firstBadgeImage.setImage(image);
                firstBadgeLabel.setText(text);
            } else if (category.equals("Transport")) {
                String level = achievement.getBadge().getName();
                String text = level.substring(0, 1).toUpperCase()
                    + level.substring(1) + " Transformer";
                secondBadgeImage.setImage(image);
                secondBadgeLabel.setText(text);
            } else if (category.equals("Energy")) {
                String level = achievement.getBadge().getName();
                String text = level.substring(0, 1).toUpperCase()
                    + level.substring(1) + " White Walker";
                thirdBadgeImage.setImage(image);
                thirdBadgeLabel.setText(text);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user.setText("User: " + me.getUsername());
        title.setText("Welcome to GoGreen! Your favourite green app!");
        setBadges();

    }
}
