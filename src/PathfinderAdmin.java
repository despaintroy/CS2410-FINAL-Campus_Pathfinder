import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PathfinderAdmin extends Application {

    public static void main(String[] args) {
        Application.launch(PathfinderAdmin.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("PathfinderAdmin.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Pathfinder Admin");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}