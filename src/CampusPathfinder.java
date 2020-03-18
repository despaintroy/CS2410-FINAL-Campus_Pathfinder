import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CampusPathfinder extends Application {

    public static void main(String[] args) {
        Application.launch(CampusPathfinder.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("CampusPathfinder.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Campus Pathfinder");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}