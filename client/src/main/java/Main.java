import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/scene/SceneOne.fxml"));
        primaryStage.setTitle("GoGreen");

        Scene sceneOne = new Scene(root, 600, 400);
        sceneOne.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());

        Image icon = new Image("img/oak-icon.png");
        primaryStage.getIcons().add(icon);

        primaryStage.setScene(sceneOne);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
