import CampusMapView.Graph.Graph;
import CampusMapView.MapView;
import CampusMapView.Buildings;
import Utilities.Weather;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class CampusPathfinderController {

    private UserMapView map;
    static final int TOP_HEIGHT        = 35;
    static final int LEFT_WIDTH        = 180;
    static final int VIEWPORT_WIDTH    = 967;
    static final int VIEWPORT_HEIGHT   = 683;

    @FXML
    public Pane centerPane;
    public HBox topBar;
    public VBox leftBar;
    public Button findPathSubmit;
    public Text tempReadout;
    public ComboBox<String> buildingFrom;
    public ComboBox<String> buildingTo;

    @FXML
    public void findPathClicked() {
        map.drawShortestPath(buildingFrom.getValue(), buildingTo.getValue());
        centerPane = map.getMasterPane();
    }

    @FXML
    public void initialize() {

        new Buildings();

        // Set the dimensions of the viewport
        topBar.setPrefHeight(TOP_HEIGHT);
        leftBar.setPrefWidth(LEFT_WIDTH);

        // Display the temperature
        try {
            tempReadout.setText("Temperature is " + Math.round(Weather.getTempF()) + " ºF");
        } catch (Weather.CannotGetTempException e) {
            tempReadout.setText("Temperature is -- ºF");
            e.printStackTrace();
        }

        // Construct the map view
        map = new UserMapView(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        centerPane.getChildren().setAll(map.getMasterPane());

        // Handle clicks on the graph
        centerPane.setOnMouseClicked(e -> {
            map.click(e.getX(), e.getY());
        });

        // Populate the Combo Boxes
        buildingFrom.getItems().addAll(Buildings.getCodes());
        buildingTo.getItems().addAll(Buildings.getCodes());
    }


    static class UserMapView extends MapView {

        private double[] lastClick = {-1,-1};

        /**
         * Initializes a MapView.MapView with just an image to display for the background.
         *
         * @param viewport_width  width of pane to create
         * @param viewport_height height of pane to create
         */
        public UserMapView(int viewport_width, int viewport_height) {
            super(viewport_width, viewport_height);
        }

        // TODO: This class handles clicks on the map.
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