import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLExample extends Application {

    public static void main(String[] args) {
        Application.launch(FXMLExample.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
        Scene scene = new Scene(root);

        // Get the width/height of different elements from the
        double topHeight = FXMLExampleController.TOP_HEIGHT;
        double leftWidth = FXMLExampleController.LEFT_WIDTH;
        double viewportWidth = FXMLExampleController.VIEWPORT_WIDTH;
        double viewportHeight = FXMLExampleController.VIEWPORT_HEIGHT;

        stage.setTitle("Campus Pathfinder");
        stage.setScene(scene);
        stage.setWidth(leftWidth+viewportWidth);
        stage.setHeight(topHeight+viewportHeight);
        stage.setResizable(false);
        stage.show();
    }
}