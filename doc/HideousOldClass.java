package depricated;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/*
TODO: Slider to control how to prioritize going inside.
TODO: Loading bar for how complete the algorithm it.
TODO: Select your own nodes of where to go to/from
TODO: Cache all possible paths and load into memory. Then update paths in real time when moving mouse around the screen.
 */

public class PathFinderViewer extends Application {

    final int TOP_HEIGHT = 35;
    final int LEFT_WIDTH = 200;
    final int VIEWPORT_HEIGHT = 512;
    final int VIEWPORT_WIDTH = 725;

    MapView map = new MapView(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    Buildings c = new Buildings();

    @Override
    public void start(Stage primaryStage) {

        BorderPane myPane = new BorderPane();

        myPane.setLeft(buildLeftPane());
        myPane.setTop(buildTopPane());
        myPane.setCenter(buildCenterPane(map));

        Scene scene = new Scene(myPane, LEFT_WIDTH+VIEWPORT_WIDTH, TOP_HEIGHT+VIEWPORT_HEIGHT);
        primaryStage.setTitle("Campus Pathfinder");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    Pane buildTopPane() {

        // Build the top pane
//        Button showPaths = new Button();
        Text tempText = new Text();

        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(tempText, new Insets(0, 10, 0, 0));
        topPane.setStyle("-fx-background-color: rgb(30, 50, 80);");
        topPane.setPrefHeight(TOP_HEIGHT);
        topPane.getChildren().addAll(tempText);

        // Show Paths Button
//        showPaths.setText("Show All Paths");
//        showPaths.setOnAction(e -> {map.showAllPaths();});

        // Temperature readout
        tempText.setFill(Color.WHITE);
        tempText.setFont(Font.font("helvetica", 20));
        try {
            tempText.setText("Temperature is " + Math.round(Weather.getTempF()) + " ºF");
        } catch (Weather.CannotGetTempException e) {
            tempText.setText("Ø");
        }

        return topPane;
    }


    Pane buildLeftPane() {

        // Build left pane
        VBox leftPane = new VBox();
        leftPane.setStyle("-fx-background-color: rgb(65, 80, 100);");
        leftPane.setPrefWidth(LEFT_WIDTH);

        leftPane.setSpacing(0);

        Label fromLabel = new Label();
        ComboBox<String> buildingFrom = new ComboBox<>();
        Label toLabel = new Label();
        ComboBox<String> buildingTo = new ComboBox<>();

        Label radioLabel = new Label();
        ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Shortest Distance");
        RadioButton rb2 = new RadioButton("Indoor Priority");

        Button submitButton = new Button();

        // Set spacings between elements
        VBox.setMargin(fromLabel, new Insets(10, 10, 0, 10));
        VBox.setMargin(buildingFrom, new Insets(5, 10, 0, 10));
        VBox.setMargin(toLabel, new Insets(10, 10, 0, 10));
        VBox.setMargin(buildingTo, new Insets(5, 10, 0, 10));
        VBox.setMargin(radioLabel, new Insets(20, 10, 0, 10));
        VBox.setMargin(rb1, new Insets(5, 10, 0, 10));
        VBox.setMargin(rb2, new Insets(5, 10, 0, 10));
        VBox.setMargin(submitButton, new Insets(20, 10, 0, 10));

        // Make elements take full width
        HBox.setHgrow(buildingFrom, Priority.ALWAYS);
        HBox.setHgrow(buildingTo, Priority.ALWAYS);
        HBox.setHgrow(submitButton, Priority.ALWAYS);
        HBox.setHgrow(radioLabel, Priority.ALWAYS);
        HBox.setHgrow(rb1, Priority.ALWAYS);
        HBox.setHgrow(rb2, Priority.ALWAYS);
        buildingFrom.setMaxWidth(Double.MAX_VALUE);
        buildingTo.setMaxWidth(Double.MAX_VALUE);
        submitButton.setMaxWidth(Double.MAX_VALUE);
        radioLabel.setMaxWidth(Double.MAX_VALUE);
        rb1.setMaxWidth(Double.MAX_VALUE);
        rb2.setMaxWidth(Double.MAX_VALUE);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Old Main",
                        "Life Sciences",
                        "Engineering",
                        "Family Life",
                        "Institute"
                );
        buildingFrom.setItems(options);
        buildingFrom.setPromptText("Select building");
        buildingTo.setItems(options);
        buildingTo.setPromptText("Select building");
        fromLabel.setText("From:");
        fromLabel.setTextFill(Color.WHITE);
        toLabel.setText("To:");
        toLabel.setTextFill(Color.WHITE);

        // Radio Buttons
        radioLabel.setTextFill(Color.WHITE);
        radioLabel.setText("Pathfinding Method:");
        rb1.setToggleGroup(group);
        rb1.setTextFill(Color.WHITE);
        rb1.setSelected(true);
        rb2.setToggleGroup(group);
        rb2.setTextFill(Color.WHITE);

        // Submit button
        submitButton.setText("Find Path");
        submitButton.setOnAction(e -> {
            map.findPath(buildingFrom.getValue(), buildingTo.getValue());
        });

        leftPane.getChildren().addAll(fromLabel, buildingFrom, toLabel, buildingTo, radioLabel, rb1, rb2, submitButton);
        return leftPane;
    }


    Pane buildCenterPane(MapView m) {
        return m.getPane();
    }


    private String getWeatherAsString() {
        try {
            return  "" + Math.round(Weather.getTempF());
        }
        catch (Weather.CannotGetTempException e) {
            return  "";
        }
    }
}
