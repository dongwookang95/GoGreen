package backend.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Parent controller for the Login and SignUp controllers.
 * Defines methods used in the first scene.
 */
public class EntryController {
    /**
     * Generalized scene creation tool that allows different scenes to be created.
     *
     * @param filepath The scene's file relative path.
     * @param style    The scene's css relative path
     * @param width    The preferred width.
     * @param height   The preferred height.
     * @return The built scene to be set on stage.
     * @throws IOException When the FXMLLoader cannot find the fxml file.
     */
    protected Scene createScene(String filepath, String style, int width, int height)
            throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filepath));
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource(style).toExternalForm());
        return scene;
    }

    /**
     * Helper method to clear a TextField if it contains the default field value.
     * @param field The field to be changed.
     */
    private void clearTextField(TextField field) {
        String content = field.getCharacters().toString();
        if (content.equals("Username")
                || content.equals("Password")
                || content.equals("Search friend here...")
                || content.equals("Search user here...")) {
            field.clear();
        }
    }

    /**
     * Helper method to fill a TextField with the default values if empty.
     * @param field The field to be filled.
     * @param text The text to be injected.
     */
    private void fillTextField(TextField field, String text) {
        if (field.getCharacters().toString().equals("")) {
            field.setText(text);
        }
    }

    protected void focusChangedClearOrFill(boolean loseFocus, boolean gainFocus,
                                           TextField field, String textToFill) {
        if (gainFocus) {
            clearTextField(field);
        }
        if (loseFocus) {
            fillTextField(field, textToFill);
        }
    }
}
