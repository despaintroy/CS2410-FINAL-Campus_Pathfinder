import com.CampusGraph.Buildings;
import com.CampusGraph.Dijkstra;
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

public class MapView {

    private final String EDGES_FILEPATH = "data/edges.json";
    private final String NODES_FILEPATH = "data/nodes.json";
    private final String MAP_FILEPATH = "data/campus_map.png";
    private final Color PATH_COLOR = Color.NAVY;

    private final double SCALE = 1.93;

    private Pane masterPane;
    private Pane graphPane;
    private Graph graph;

    /**
     * Initializes a MapView with just an image to display for  tbe background.
     *
     * @param viewport_width
     * @param viewport_height
     */
    MapView(int viewport_width, int viewport_height) {

        graph = new Graph(NODES_FILEPATH, EDGES_FILEPATH);

        masterPane = new StackPane();
        graphPane = new Pane();
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

        masterPane.getChildren().addAll(iv, graphPane);
    }


    void showAllPaths() {

        graphPane.getChildren().clear();

        double [][] nodeCoords = graph.getNodeCoords();
        ArrayList<Double[]> edgeCoords = graph.getEdgeCoords();

        for (double[] coord : nodeCoords) {

            double x = coord[0];
            double y = coord[1];

            Circle temp = new Circle(x/SCALE, y/SCALE,2, PATH_COLOR);
            graphPane.getChildren().add(temp);
        }

        for (Double[] coords : edgeCoords) {
            double x1 = coords[0];
            double y1 = coords[1];
            double x2 = coords[2];
            double y2 = coords[3];

            Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
            temp.setStroke(PATH_COLOR);
            graphPane.getChildren().add(temp);
        }
    }

    public void findPath(String from, String to) {

        System.out.println("From (" + from + ") to (" + to + ")");

//        int start = (int)(Math.random()*810);
//        int end = (int)(Math.random()*810);

        int start = Buildings.buildingNodes.get(from);
        int end = Buildings.buildingNodes.get(to);

        ArrayList<Integer> bestPath = graph.findPath(start, end);

        // Mark with circles the endpoints
        Circle startCircle = new Circle(graph.nodes.get(start).x/SCALE, graph.nodes.get(start).y/SCALE, 5 , PATH_COLOR);
        Circle endCircle = new Circle(graph.nodes.get(end).x/SCALE, graph.nodes.get(end).y/SCALE, 5 , PATH_COLOR);

        graphPane.getChildren().clear();
        graphPane.getChildren().addAll(startCircle, endCircle);

        for (int i=0; i<bestPath.size()-1; i++) {

            // Draw the new path
            double x1 = graph.nodes.get(bestPath.get(i)).x;
            double y1 = graph.nodes.get(bestPath.get(i)).y;
            double x2 = graph.nodes.get(bestPath.get(i+1)).x;
            double y2 = graph.nodes.get(bestPath.get(i+1)).y;

            Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
            temp.setStroke(PATH_COLOR);
            temp.setStrokeWidth(2);
            graphPane.getChildren().add(temp);
        }
    }

    public Pane getMasterPane() {
        return masterPane;
    }
}
