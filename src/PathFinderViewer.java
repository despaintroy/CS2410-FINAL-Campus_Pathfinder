import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PathFinderViewer extends Application {

    final int TOP_HEIGHT = 30;
    final int LEFT_WIDTH = 200;
    final int VIEWPORT_HEIGHT = 512;
    final int VIEWPORT_WIDTH = 725;
    final double SCALE = 1.93;

    @Override
    public void start(Stage primaryStage) {

        BorderPane myPane = new BorderPane();

        myPane.setLeft(buildLeftPane());
        myPane.setTop(buildTopPane());
        myPane.setCenter(buildCenterPane());

        Scene scene = new Scene(myPane, LEFT_WIDTH+VIEWPORT_WIDTH, TOP_HEIGHT+VIEWPORT_HEIGHT);
        primaryStage.setTitle("Campus Pathfinder");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    Pane buildTopPane() {
        HBox horizontal = new HBox();
        StackPane stack = new StackPane();

        Text tempText = new Text();
        tempText.setFill(Color.WHITE);
        tempText.setFont(Font.font("helvetica", 20));
        tempText.setText("Temperature: " + (int)Weather.getTempF() + "℉");
//        tempText.setText("Temperature: " + 0 + "℉");

        horizontal.getChildren().addAll(tempText);

        stack.setStyle("-fx-background-color: rgb(50, 75, 100);");
        stack.setPrefHeight(TOP_HEIGHT);
        stack.getChildren().addAll(horizontal);

        return stack;
    }

    Pane buildLeftPane() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Old Main",
                        "Life Sciences Building",
                        "Engineering Building",
                        "Family Life"
                );
        final ComboBox buildingChoice = new ComboBox(options);
        VBox pane = new VBox();
        pane.getChildren().addAll(buildingChoice);
        pane.setStyle("-fx-background-color: rgb(65, 90, 100);");
        pane.setPrefWidth(LEFT_WIDTH);
        return pane;
    }

    Pane buildCenterPane() {

        Graph newGraph = new Graph("savedNodes.txt", "savedEdges.txt");

        StackPane stack = new StackPane();
        Pane graph = new Pane();

        try {
            FileInputStream inputstream = new FileInputStream("campus_map.png");
            Image image = new Image(inputstream);
            ImageView iv1 = new ImageView();
            iv1.setImage(image);
            iv1.setFitWidth(VIEWPORT_WIDTH);
            iv1.setFitHeight(VIEWPORT_HEIGHT);
            stack.getChildren().addAll(iv1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        float [][] nodeCoords = newGraph.getNodeCoords();
        ArrayList<Float[]> edgeCoords = newGraph.getEdgeCoords();

        for (float[] coord : nodeCoords) {

            float x = coord[0];
            float y = coord[1];

            Circle temp = new Circle(x/SCALE, y/SCALE,2, Style.nodeColor);
            graph.getChildren().add(temp);
        }

        for (Float[] coords : edgeCoords) {
            float x1 = coords[0];
            float y1 = coords[1];
            float x2 = coords[2];
            float y2 = coords[3];

            Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
            temp.setStroke(Style.edgeColor);
            graph.getChildren().add(temp);
        }

        stack.getChildren().add(graph);

        return stack;
    }
}
