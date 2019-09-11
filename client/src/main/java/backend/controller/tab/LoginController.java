package backend.controller.tab;

import backend.controller.EntryController;
import backend.entity.ConnectionResponse;
import backend.entity.LoginRequest;
import backend.service.EntryRequestHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends EntryController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private Label logMessage;



    /**
     * Event handler for the submit button.
     * When the user clicks to login, credentials are sent to the server
     * to authenticate the user and retrieve the relevant information.
     */
    private void submitLoginCredentials() {
        final String filepath = "../../../fxml/scene/SceneTwo.fxml";
        final String stylePath = "../../../css/style.css";

        Stage stage = (Stage) login.getScene().getWindow();

        String name = username.getCharacters().toString();
        String psw = password.getCharacters().toString();

        LoginRequest loginRequest = new LoginRequest(name, psw);
        ConnectionResponse response = EntryRequestHandler.login(loginRequest);

        if (!response.getToken().equals("Invalid")) {
            try {
                stage.setScene(createScene(filepath, stylePath, 800, 600));
            } catch (IOException e) {
                logMessage.setText("Error loading the scene.");
            }
            stage.show();
        } else {
            logMessage.setText("Invalid credentials");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> submitLoginCredentials());

        username.focusedProperty().addListener((obsValue, loseFocus, gainFocus) ->
                focusChangedClearOrFill(loseFocus, gainFocus, username, "Username"));

        password.focusedProperty().addListener((obsValue, loseFocus, gainFocus) ->
                focusChangedClearOrFill(loseFocus, gainFocus, password,"Password"));
    }
}
