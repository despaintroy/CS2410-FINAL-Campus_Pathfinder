import CampusMapView.Buildings;
import CampusMapView.MapView;
import Utilities.Weather;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PathfinderAdminController {

    private AdminMapView map;
    static final int TOP_HEIGHT        = 35;
    static final int BOTTOM_HEIGHT     = 35;
    static final int VIEWPORT_WIDTH    = 967;
    static final int VIEWPORT_HEIGHT   = 683;

    @FXML
    public Pane centerPane;
    public HBox topBar;
    public HBox bottomBar;
    public Text tempReadout;
    public ComboBox<String> actionChooser;
    public ComboBox<String> buildingActionChooser;


    @FXML
    public void initialize() {

        new Buildings();

        // Set the dimensions of the viewport
        topBar.setPrefHeight(TOP_HEIGHT);
        bottomBar.setPrefHeight(BOTTOM_HEIGHT);

        // Construct the map view
        map = new AdminMapView(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        centerPane.getChildren().setAll(map.getMasterPane());

        // Handle clicks on the graph
        centerPane.setOnMouseClicked(e -> {
            System.out.println("Click x:" + (e.getX() + "\ty:" + e.getY()));
        });

        // Populate the Combo Boxes
        buildingActionChooser.getItems().addAll(Buildings.getCodes());

        // Handle changes to the action combo box
        actionChooser.setOnAction(event -> {
            if (actionChooser.getValue().equals("Pathfinder")) {
                map.clearPaths();
            }
            else if (actionChooser.getValue().equals("Tag Buildings")) {
                map.showAllPaths();
            }
        });
    }


    static class AdminMapView extends MapView {

        /**
         * Initializes a MapView.MapView with just an image to display for the background.
         *
         * @param viewport_width  width of pane to create
         * @param viewport_height height of pane to create
         */
        public AdminMapView(int viewport_width, int viewport_height) {
            super(viewport_width, viewport_height);
        }
    }
}