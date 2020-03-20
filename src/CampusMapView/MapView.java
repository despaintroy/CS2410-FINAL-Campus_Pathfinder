package CampusMapView;

import CampusMapView.Graph.Graph;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MapView {

    private final String EDGES_FILEPATH = "data/edges.json";
    private final String NODES_FILEPATH = "data/nodes.json";
    private final String MAP_FILEPATH = "data/campus_map.png";
    protected final Color PATH_COLOR = Color.NAVY;

    protected final double SCALE = 1.448;

    private Pane masterPane;
    protected Pane pathsPane;
    protected Graph graph;


    /**
     * Initializes a MapView.MapView with just an image to display for  tbe background.
     *
     * @param viewport_width width of pane to create
     * @param viewport_height height of pane to create
     */
    public MapView(int viewport_width, int viewport_height) {

        graph = new Graph(NODES_FILEPATH, EDGES_FILEPATH);

        masterPane = new StackPane();
        pathsPane = new Pane();
        ImageView iv = new ImageView();

        // Load the map background
        try {
            FileInputStream imageIn = new FileInputStream(MAP_FILEPATH);
            Image image = new Image(imageIn);
            iv.setImage(image);
            iv.setFitWidth(viewport_width);
            iv.setFitHeight(viewport_height);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        masterPane.getChildren().addAll(iv, pathsPane);
    }


    /**
     * Draw all paths and nodes from the graph
     */
    public void showAllPaths() {

        clearPaths();

        double [][] nodeCoords = graph.getAllNodeCoords();
        ArrayList<Double[]> edgeCoords = graph.getAllEdgeCoords();

        for (double[] coord : nodeCoords) {

            double x = coord[0];
            double y = coord[1];

            Circle temp = new Circle(x/SCALE, y/SCALE,2, PATH_COLOR);
            pathsPane.getChildren().add(temp);
        }

        for (Double[] coords : edgeCoords) {
            double x1 = coords[0];
            double y1 = coords[1];
            double x2 = coords[2];
            double y2 = coords[3];

            Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
            temp.setStroke(PATH_COLOR);
            pathsPane.getChildren().add(temp);
        }
    }


    /**
     * Draw the shortest path between two nodes
     *
     * @param from the name of the building to start at
     * @param to the name of the building to end at
     */
    public void drawShortestPath(String from, String to) {

        System.out.println("From (" + from + ") to (" + to + ")");

        // Find the node of the building, then call the other drawShortestPath function
        int start = Buildings.getBuildNodeId(from);
        int end = Buildings.getBuildNodeId(to);

        drawShortestPath(start, end);
    }


    /**
     * Draw the shortest path between two nodes
     *
     * @param start building name to start from
     * @param end building name to end at
     */
    public void drawShortestPath(int start, int end) {

        ArrayList<Integer> bestPath = graph.findPath(start, end);

        double [][] nodeCoords = graph.getAllNodeCoords();

        // Mark with circles the endpoints
        Circle startCircle = new Circle(nodeCoords[start][0]/SCALE, nodeCoords[start][1]/SCALE, 5 , PATH_COLOR);
        Circle endCircle = new Circle(nodeCoords[end][0]/SCALE, nodeCoords[end][1]/SCALE, 5 , PATH_COLOR);

        // Clear any existing graph from off the pane
        clearPaths();

        // Draw the path onto the pane
        for (int i=0; i<bestPath.size()-1; i++) {

            // Draw the new path
            double x1 = nodeCoords[bestPath.get(i)][0];
            double y1 = nodeCoords[bestPath.get(i)][1];
            double x2 = nodeCoords[bestPath.get(i+1)][0];
            double y2 = nodeCoords[bestPath.get(i+1)][1];

            Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
            temp.setStroke(PATH_COLOR);
            temp.setStrokeWidth(2);
            pathsPane.getChildren().add(temp);
        }

        pathsPane.getChildren().addAll(startCircle, endCircle);
    }


    /**
     * Finds the closest node to the given coordinate
     *
     * @param x given x coordinate
     * @param y given y coordinate
     * @return the index of the closest node on the graph
     */
    protected int getClosestNode(double x, double y) throws Graph.EmptyGraphException {
        return graph.getClosestNode(x*SCALE, y*SCALE);
    }


    public void clearPaths() {
        pathsPane.getChildren().clear();
    }


    /**
     * Gets the pane with the map drawn on it
     *
     * @return the constructed pane
     */
    public Pane getMasterPane() {
        return masterPane;
    }
}
