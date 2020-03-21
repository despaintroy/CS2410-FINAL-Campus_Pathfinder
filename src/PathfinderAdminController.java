import CampusMapView.Buildings;
import CampusMapView.Graph.Graph;
import CampusMapView.Graph.Node;
import CampusMapView.Graph.Edge;
import CampusMapView.MapView;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

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

        // Populate the Combo Boxes
        buildingActionChooser.getItems().addAll(Buildings.getCodes());

        // Handle changes to the action combo box
        actionChooser.setOnAction(event -> {
            if (actionChooser.getValue().equals("Tag Buildings")) {
                map.showAllPaths();

                // Clicks on the graph
                centerPane.setOnMouseClicked(e -> {
                    map.click(e.getX(), e.getY());
                });
            }
        });
    }


    static class AdminMapView extends MapView {

        protected final Color BUILDING_COLOR = Color.RED;

        /**
         * Initializes a MapView.MapView with just an image to display for the background.
         *
         * @param viewport_width  width of pane to create
         * @param viewport_height height of pane to create
         */
        public AdminMapView(int viewport_width, int viewport_height) {
            super(viewport_width, viewport_height);
        }

        void click(double x, double y) {

            x *= SCALE;
            y *= SCALE;

            try {
                Node closest = graph.getClosestNode(x, y);
                Circle temp = new Circle(closest.getX()/SCALE, closest.getY()/SCALE,2, Color.RED);
                pathsPane.getChildren().add(temp);
            } catch (Graph.EmptyGraphException e) {
                System.out.println("Empty graph");
            }
        }


        /**
         * Draw all paths and nodes from the graph
         */
        public void showAllPaths() {

            clearPaths();

            Node[] nodes = graph.getAllNodes();
            Edge[] edgeCoords = graph.getAllEdges();

            for (Node n : nodes) {
                Circle temp = new Circle(n.getX()/SCALE, n.getY()/SCALE,2, PATH_COLOR);
                pathsPane.getChildren().add(temp);
            }

            for (Edge e : edgeCoords) {

                if (!e.isActive()) {
                    continue;
                }

                double x1 = e.getN1().getX();
                double y1 = e.getN1().getY();
                double x2 = e.getN2().getX();
                double y2 = e.getN2().getY();

                Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
                temp.setStroke(PATH_COLOR);
                pathsPane.getChildren().add(temp);
            }
        }
    }
}