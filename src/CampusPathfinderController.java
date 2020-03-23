import CampusMapView.Graph.Graph;
import CampusMapView.MapView;
import CampusMapView.Buildings;
import Utilities.Weather;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;

public class CampusPathfinderController {

    static final int TOP_HEIGHT        = 35;
    static final int LEFT_WIDTH        = 180;
    static final int VIEWPORT_WIDTH    = 967;
    static final int VIEWPORT_HEIGHT   = 683;
    private UserMapView map;

    @FXML
    public HBox topBar;
    public Text tempReadout;
    public VBox leftBar;
    public ComboBox<String> buildingFrom;
    public ComboBox<String> buildingTo;
    public Slider indoorSlider;
//    public Button findPathSubmit;
    public Pane centerPane;


    /**
     * This code automatically runs when the FXML is rendered
     */
    @FXML
    public void initialize() {

        new Buildings();

        // Set the dimensions of the UI
        topBar.setPrefHeight(TOP_HEIGHT);
        leftBar.setPrefWidth(LEFT_WIDTH);

        // Display the temperature
        try {
            tempReadout.setText("Temperature is " + Math.round(Weather.getTempF()) + " ºF");
        } catch (Weather.CannotGetTempException e) {
            tempReadout.setText("Temperature is -- ºF");
            System.out.println("Not able to read temperature");
            e.printStackTrace();
        }

        // Construct the map view
        map = new UserMapView(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        centerPane.getChildren().setAll(map.getMasterPane());

        // Handle clicks on the graph
        centerPane.setOnMouseClicked(e -> {
            buildingFrom.setValue("");
            buildingTo.setValue("");
            map.click(e.getX(), e.getY());
        });

        // Populate the Combo Boxes
        ArrayList<String> buildingCodes = getFilteredBuildings();
        buildingFrom.getItems().addAll(buildingCodes);
        buildingTo.getItems().addAll(buildingCodes);
        buildingFrom.setOnAction(event -> findPathClicked());
        buildingTo.setOnAction(event -> findPathClicked());

        new AutoCompleteComboBoxListener<>(buildingFrom);
        new AutoCompleteComboBoxListener<>(buildingTo);

        // Changes to the indoor weight slider
        indoorSlider.setValue(1);
        indoorSlider.setOnMouseReleased(event -> {
            map.setIndoorWeight(indoorSlider.getValue());
            map.redraw();
        });
    }


    static ArrayList<String> getFilteredBuildings() {
        ArrayList<String> filtered = Buildings.getCodesWithNodes();
        Collections.sort(filtered);
        return filtered;
    }


    /**
     * Handles the 'Find Path' button being clicked
     */
    @FXML
    public void findPathClicked() {
        map.drawShortestPath(buildingFrom.getValue(), buildingTo.getValue());
        centerPane = map.getMasterPane();
    }


    /**
     * This class extends MapView to implement functionality specific to the Admin window
     */
    static class UserMapView extends MapView {

        private double[] lastClick = {-1,-1};


        /**
         * Calls super to initialize MapView
         *
         * @param viewport_width  width of pane to create
         * @param viewport_height height of pane to create
         */
        public UserMapView(int viewport_width, int viewport_height) {
            super(viewport_width, viewport_height);
        }


        /**
         * Processes clicks on the graph
         *
         * @param x the x coordinate the mouse was clicked at
         * @param y the y coordinate the mouse was clicked at
         */
        public void click(double x, double y) {

            // First Click
            if (lastClick[0] == -1) {
                lastClick = new double[]{x, y};
                // Draw a line extending from first click to pointer
                pathsPane.setOnMouseMoved(event -> {
                    clearPaths();
                    Line myLine = new Line(x, y, event.getX(), event.getY());
                    myLine.setStroke(PATH_COLOR);
                    myLine.setStrokeWidth(2);
                    pathsPane.getChildren().add(myLine);
                });
            }

            // Second Click
            else {
                pathsPane.setOnMouseMoved(null);
                try {
                    drawShortestPath(
                            getClosestNode(lastClick[0], lastClick[1]).getId(),
                            getClosestNode(x, y).getId()
                    );
                } catch (Graph.EmptyGraphException e) {
                    e.printStackTrace();
                }
                lastClick = new double[]{-1,-1};
            }
        }
    }
}