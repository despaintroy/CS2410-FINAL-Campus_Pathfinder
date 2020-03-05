import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PathFinderViewer extends Application {

    final int TOP_HEIGHT = 30;
    final int LEFT_WIDTH = 200;
    final int VIEWPORT_HEIGHT = 512;
    final int VIEWPORT_WIDTH = 725;

    MapView map = new MapView(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

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
        Button showPaths = new Button();
        Text tempText = new Text();
        CheckBox weatherCheckBox = new CheckBox();

        HBox topPane = new HBox();
        topPane.setStyle("-fx-background-color: rgb(50, 75, 100);");
        topPane.setPrefHeight(TOP_HEIGHT);
        topPane.getChildren().addAll(showPaths, weatherCheckBox, tempText);

        // Show Paths Button
        showPaths.setText("Show All Paths");
        showPaths.setOnAction(e -> {map.showAllPaths();});

        // Checkbox
        weatherCheckBox.setTextFill(Color.WHITE);
        weatherCheckBox.setText("Use Weather");
        weatherCheckBox.setOnAction(e -> {

            if (weatherCheckBox.isSelected()) {
                Weather.setActive(true);
                tempText.setText(getWeatherAsString() + "ºF");
            }
            else {
                Weather.setActive(false);
                tempText.setText(getWeatherAsString());
            }
        });

        // Temperature readout
        tempText.setFill(Color.WHITE);
        tempText.setFont(Font.font("helvetica", 20));
        tempText.setText("");

        return topPane;
    }

    Pane buildLeftPane() {

        // Build left pane
        Label fromLabel = new Label();
        Label toLabel = new Label();
        ComboBox<String> buildingFrom = new ComboBox<>();
        ComboBox<String> buildingTo = new ComboBox<String>();
        Button submitButton = new Button();

        Label radioLabel = new Label();
        ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Shortest Distance");
        RadioButton rb2 = new RadioButton("Indoor Priority");

        VBox leftPane = new VBox();
        leftPane.setStyle("-fx-background-color: rgb(65, 90, 100);");
        leftPane.setPrefWidth(LEFT_WIDTH);

        leftPane.setSpacing(0);

        VBox.setMargin(fromLabel, new Insets(10, 10, 0, 10));
        VBox.setMargin(buildingFrom, new Insets(5, 10, 0, 10));
        VBox.setMargin(toLabel, new Insets(10, 10, 0, 10));
        VBox.setMargin(buildingTo, new Insets(5, 10, 0, 10));
        VBox.setMargin(radioLabel, new Insets(20, 10, 0, 10));
        VBox.setMargin(rb1, new Insets(5, 10, 0, 10));
        VBox.setMargin(rb2, new Insets(5, 10, 0, 10));
        VBox.setMargin(submitButton, new Insets(20, 10, 0, 10));

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

        leftPane.getChildren().addAll(fromLabel, buildingFrom, toLabel, buildingTo, radioLabel, rb1, rb2, submitButton);

        // Dropdown box
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Old Main",
                        "Life Sciences Building",
                        "Engineering Building",
                        "Family Life"
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
        submitButton.setOnAction(e -> map.findPath());

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
        catch (Weather.WeatherNotActiveException e) {
            return  "";
        }
    }
}
