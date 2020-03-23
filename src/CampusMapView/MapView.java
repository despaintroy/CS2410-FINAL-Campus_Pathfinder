package CampusMapView;

import CampusMapView.Graph.Graph;
import CampusMapView.Graph.Node;
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

    protected final String EDGES_FILEPATH   = "data/edges.json";
    protected final String NODES_FILEPATH   = "data/nodes.json";
    private final String MAP_FILEPATH       = "data/campus_map.png";
    protected final Color PATH_COLOR        = Color.NAVY;

    // TODO: dynamically calculate the scale
    protected final double SCALE = 1.448;
    private double indoorWeight;

    private Pane masterPane;
    protected Pane pathsPane;
    protected Graph graph;


    /**
     * Initializes a MapView with just an image to display for  tbe background.
     *
     * @param viewport_width width of pane to create
     * @param viewport_height height of pane to create
     */
    public MapView(int viewport_width, int viewport_height) {

        graph = new Graph(NODES_FILEPATH, EDGES_FILEPATH);
        indoorWeight = 1;

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
     * Draw the shortest path between two nodes
     *
     * @param startCode the building code to start at
     * @param endCode the building code to end at
     */
    public void drawShortestPath(String startCode, String endCode) {

        System.out.println("\nFrom " + startCode + " to " + endCode + ":");

        // Find the nodes of the building, then call the other drawShortestPath function
        ArrayList<Integer> startIDs = Buildings.getNodes(startCode);
        ArrayList<Integer> endIDs = Buildings.getNodes(endCode);

        if (startIDs.size()==0 || endIDs.size()==0) {
            System.out.println("No nodes assigned to building");
            return;
        }

        drawShortestPath(startIDs, endIDs);
    }


    /**
     * Draw the shortest path between two nodes
     *
     * @param startID the node id to start at
     * @param endID the node id to end at
     */
    public void drawShortestPath(int startID, int endID) {

        System.out.println("\nFrom " + startID + " to " + endID + ":");

        ArrayList<Integer> startList = new ArrayList<Integer>(){{add(startID);}};
        ArrayList<Integer> endList = new ArrayList<Integer>(){{add(endID);}};
        drawShortestPath(startList, endList);
    }


    /**
     * Draw the shortest path between two nodes
     *
     * @param start building name to start from
     * @param end building name to end at
     */
    // TODO: Change this to use sets instead of array lists
    public void drawShortestPath(ArrayList<Integer> start, ArrayList<Integer> end) {

        Integer[] bestPath = graph.findPath(start, end, indoorWeight);
        Node[] nodes = graph.getAllNodes();

        // Clear any existing graph from off the pane
        clearPaths();

        // Draw the path onto the pane
        for (int i=0; i<bestPath.length-1; i++) {

            // Draw the new path
            double x1 = nodes[bestPath[i]].getX();
            double y1 = nodes[bestPath[i]].getY();
            double x2 = nodes[bestPath[i+1]].getX();
            double y2 = nodes[bestPath[i+1]].getY();

            Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
            temp.setStroke(PATH_COLOR);
            temp.setStrokeWidth(2);
            pathsPane.getChildren().add(temp);
        }

        // Mark the endpoints with circles
        int startNode = bestPath[0];
        int endNode = bestPath[bestPath.length-1];
        Circle startCircle = new Circle(nodes[startNode].getX()/SCALE, nodes[startNode].getY()/SCALE, 5 , PATH_COLOR);
        Circle endCircle = new Circle(nodes[endNode].getX()/SCALE, nodes[endNode].getY()/SCALE, 5 , PATH_COLOR);
        pathsPane.getChildren().addAll(startCircle, endCircle);
    }


    /**
     * Finds the closest node to the given coordinate
     *
     * @param x given x coordinate
     * @param y given y coordinate
     * @return the index of the closest node on the graph
     */
    protected Node getClosestNode(double x, double y) throws Graph.EmptyGraphException {
        return graph.getClosestNode(x*SCALE, y*SCALE);
    }


    /**
     * Clears everything that was drawn onto the paths pane
     */
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
