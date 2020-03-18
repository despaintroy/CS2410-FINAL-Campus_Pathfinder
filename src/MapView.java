import com.CampusGraph.Buildings;
import com.CampusGraph.Graph;
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

class MapView {

    private final String EDGES_FILEPATH = "data/edges.json";
    private final String NODES_FILEPATH = "data/nodes.json";
    private final String MAP_FILEPATH = "data/campus_map.png";
    private final Color PATH_COLOR = Color.NAVY;

    private final double SCALE = 1.448;

    private Pane masterPane;
    private Pane pathsPane;
    private Graph graph;

    private double[][] clickLocations = {{-1,-1},{-1,-1}};


    /**
     * Initializes a MapView with just an image to display for  tbe background.
     *
     * @param viewport_width width of pane to create
     * @param viewport_height height of pane to create
     */
    MapView(int viewport_width, int viewport_height) {

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
    void showAllPaths() {

        clearPaths();

        double [][] nodeCoords = graph.getNodeCoords();
        ArrayList<Double[]> edgeCoords = graph.getEdgeCoords();

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
    void drawShortestPath(String from, String to) {

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
    void drawShortestPath(int start, int end) {

        ArrayList<Integer> bestPath = graph.findPath(start, end);

        double [][] nodeCoords = graph.getNodeCoords();

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
    int getClosestNode(double x, double y) throws Graph.EmptyGraphException {
        return graph.getClosestNode(x*SCALE, y*SCALE);
    }


    // TODO: This class handles clicks on the map.
    void click(double x, double y) {

        // First Click
        clearPaths();

        if (clickLocations[0][0] == -1) {
            clickLocations[0] = new double[]{x, y};
            pathsPane.setOnMouseMoved(event -> {
                pathsPane.getChildren().clear();
                Line myLine = new Line(x, y, event.getX(), event.getY());
                myLine.setStroke(PATH_COLOR);
                myLine.setStrokeWidth(2);
                pathsPane.getChildren().add(myLine);
            });
        }
        // Second Click
        else if (clickLocations[1][0] == -1) {
            clickLocations[1] = new double[]{x, y};
            pathsPane.setOnMouseMoved(null);
            try {
                drawShortestPath(
                    getClosestNode(
                        clickLocations[0][0],
                        clickLocations[0][1]
                    ),
                    getClosestNode(
                        clickLocations[1][0],
                        clickLocations[1][1]
                    ));
            } catch (Graph.EmptyGraphException e) {
                e.printStackTrace();
            }
            clickLocations = new double[][]{{-1,-1},{-1,-1}};
        }
    }

    void clearPaths() {
        pathsPane.getChildren().clear();
    }


    /**
     * Gets the pane with the map drawn on it
     *
     * @return the constructed pane
     */
    Pane getMasterPane() {
        return masterPane;
    }
}
