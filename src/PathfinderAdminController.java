import CampusMapView.Buildings;
import CampusMapView.Graph.Graph;
import CampusMapView.Graph.Node;
import CampusMapView.Graph.Edge;
import CampusMapView.MapView;
import Utilities.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class PathfinderAdminController {

    static final int TOP_HEIGHT        = 35;
    static final int BOTTOM_HEIGHT     = 35;
    static final int VIEWPORT_WIDTH    = 967;
    static final int VIEWPORT_HEIGHT   = 683;
    private AdminMapView map;

    @FXML
    public HBox topBar;
    public Text tempReadout;
    public HBox bottomBar;
    public ComboBox<String> actionChooser;
    public ComboBox<String> buildingActionChooser;
    public Button saveButton;
    public Pane centerPane;


    /**
     * This code automatically runs when the FXML is rendered
     */
    @FXML
    public void initialize() {

        new Buildings();

        // Set the dimensions of the UI
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

        // Handle button clicks
        saveButton.setOnAction(event -> map.save());
    }


    /**
     * This class extends MapView to implement functionality specific to the Admin window
     */
    class AdminMapView extends MapView {

        // Colors used to draw paths
        private final Color PATH_COLOR        = Color.NAVY;
        private final Color BUILDING_COLOR    = Color.RED;


        /**
         * Calls super to initialize MapView
         *
         * @param viewport_width  width of pane to create
         * @param viewport_height height of pane to create
         */
        public AdminMapView(int viewport_width, int viewport_height) {
            super(viewport_width, viewport_height);
        }


        /**
         * Processes clicks on the graph
         *
         * @param x the x coordinate the mouse was clicked at
         * @param y the y coordinate the mouse was clicked at
         */
        void click(double x, double y) {

            x *= SCALE;
            y *= SCALE;

            try {
                Node closest = graph.getClosestNode(x, y);

                // Find the closest node, and toggle it's indoors property
                // TODO: Need a way to highlight all building nodes, and just the nodes of a specific building
                if (closest.isIndoors()) {
                    closest.setIndoors(false);
                    closest.setBuildingCode("");
                }
                else {
                    closest.setIndoors(true);
                    String building = buildingActionChooser.getValue();
                    closest.setBuildingCode(building == null ? "" : building);
                }
            } catch (Graph.EmptyGraphException e) {
                System.out.println("Empty graph");
                e.printStackTrace();
            }

            // Redraw
            showAllPaths();
        }


        /**
         * Draw all paths and nodes from the graph with proper colors
         */
        public void showAllPaths() {

            clearPaths();

            Node[] nodes = graph.getAllNodes();
            Edge[] edgeCoords = graph.getAllEdges();

            // Draw each edge
            for (Edge e : edgeCoords) {
                if (e.isActive()) {
                    double x1 = e.getN1().getX();
                    double y1 = e.getN1().getY();
                    double x2 = e.getN2().getX();
                    double y2 = e.getN2().getY();

                    Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
                    temp.setStroke(e.isIndoors() ? BUILDING_COLOR : PATH_COLOR);
                    pathsPane.getChildren().add(temp);
                }
            }

            // Draw each node
            for (Node n : nodes) {
                Circle temp = new Circle(n.getX()/SCALE, n.getY()/SCALE,2);
                temp.setFill(n.isIndoors() ? BUILDING_COLOR : PATH_COLOR);
                pathsPane.getChildren().add(temp);
            }
        }


        /**
         * Save the edges and nodes to file
         */
        public void save() {

//            final String NODES_FILE = "data/nodes.json";
//            final String EDGES_FILE = "data/edges.json";

            Node [] nodes = graph.getAllNodes();
            Edge [] edges = graph.getAllEdges();

            // Write nodes to file
            JSONArray nodeListJSON = new JSONArray();
            for (Node n : nodes) {
                JSONObject nodeJSON = new JSONObject();
                nodeJSON.put("x",n.getX());
                nodeJSON.put("y",n.getY());
                nodeJSON.put("in",n.isIndoors());
                nodeJSON.put("build",n.getBuildingCode());
                nodeListJSON.add(nodeJSON);
            }
            try (FileWriter file = new FileWriter(NODES_FILEPATH)) {
                file.write(nodeListJSON.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Write edges to file
            JSONArray edgeListJSON = new JSONArray();
            for (Edge e : edges) {
                JSONObject edgeJSON = new JSONObject();
                if(e.isActive()) {
                    edgeJSON.put("n1", e.getN1().getId());
                    edgeJSON.put("n2", e.getN2().getId());
                    edgeListJSON.add(edgeJSON);
                }
            }
            try (FileWriter file = new FileWriter(EDGES_FILEPATH)) {
                file.write(edgeListJSON.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("\nSave complete.");
        }
    }
}