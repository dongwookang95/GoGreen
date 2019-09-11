package backend.controller.tab;

import backend.controller.EntryController;
import backend.entity.LoginRequest;
import backend.service.EntryRequestHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController extends EntryController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField password1;
    @FXML
    private Button register;
    @FXML
    private Label regMessage;

    /**
     * Event handler for the sign up button.
     * When the user clicks to sign up, credentials are checked (passwords match),
     * sent to the server to determine if username is available and eventually register
     * the new user. The user gets feedback through a dynamic label.
     */
    private void submitSignUpCredentials() {
        String name = username.getCharacters().toString();
        String psw = password.getCharacters().toString();
        String psw1 = password1.getCharacters().toString();

        if (psw.equals(psw1) || psw.isEmpty()) {
            String response = EntryRequestHandler.createNewUser(new LoginRequest(name, psw));
            regMessage.setText(response);
        } else {
            regMessage.setText("Invalid password");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        register.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> submitSignUpCredentials());

        username.focusedProperty().addListener((obsValue, loseFocus, gainFocus) ->
                focusChangedClearOrFill(loseFocus, gainFocus, username, "Username"));

        password.focusedProperty().addListener((obsValue, loseFocus, gainFocus) ->
                focusChangedClearOrFill(loseFocus, gainFocus, password, "Password"));

        password1.focusedProperty().addListener((obsValue, loseFocus, gainFocus) ->
                focusChangedClearOrFill(loseFocus, gainFocus, password1, "Password"));
    }
}
