//package fxmlexample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLExample extends Application {

//    final int TOP_HEIGHT = 35;
//    final double LEFT_WIDTH = 200;
//    final double VIEWPORT_WIDTH = 725;
//    final double VIEWPORT_HEIGHT = 512;

    public static void main(String[] args) {
        Application.launch(FXMLExample.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
        Scene scene = new Scene(root);

        final double TOP_HEIGHT = FXMLExampleController.TOP_HEIGHT;
        final double LEFT_WIDTH = FXMLExampleController.LEFT_WIDTH;
        final double VIEWPORT_WIDTH = FXMLExampleController.VIEWPORT_WIDTH;
        final double VIEWPORT_HEIGHT = FXMLExampleController.VIEWPORT_HEIGHT;

        stage.setTitle("Campus Pathfinder");
        stage.setScene(scene);
        stage.setWidth(LEFT_WIDTH+VIEWPORT_WIDTH);
        stage.setHeight(TOP_HEIGHT+VIEWPORT_HEIGHT);
        stage.setResizable(false);
        stage.show();
    }
}