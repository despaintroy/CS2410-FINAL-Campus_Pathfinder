import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CampusPathfinder extends Application {

//    TODO: Maven dependancies to deal with that JSON library

    public static void main(String[] args) {
        Application.launch(CampusPathfinder.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("CampusPathfinder.fxml"));
        Scene scene = new Scene(root);

        // Get the width/height of different elements from the
        double topHeight = CampusPathfinderController.TOP_HEIGHT;
        double leftWidth = CampusPathfinderController.LEFT_WIDTH;
        double viewportWidth = CampusPathfinderController.VIEWPORT_WIDTH;
        double viewportHeight = CampusPathfinderController.VIEWPORT_HEIGHT;

        stage.setTitle("Campus Pathfinder");
        stage.setScene(scene);
        stage.setWidth(leftWidth+viewportWidth);
        stage.setHeight(topHeight+viewportHeight);
        stage.setResizable(false);
        stage.show();
    }
}