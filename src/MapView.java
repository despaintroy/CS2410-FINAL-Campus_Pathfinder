import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MapView {

    final double SCALE = 1.93;
    final int VIEWPORT_WIDTH;
    final int VIEWPORT_HEIGHT;

    Pane mapPane;
    Pane graphPane;
    Graph mapGraph;

    public MapView(int viewport_width, int viewport_height) {

        VIEWPORT_WIDTH = viewport_width;
        VIEWPORT_HEIGHT = viewport_height;

//        mapGraph = new Graph("savedNodes.txt", "savedEdges.txt");
        mapGraph = new Graph("nodes.json", "edges.json");

        mapPane = new StackPane();
        graphPane = new Pane();
        ImageView iv = new ImageView();

        try {
            FileInputStream inStream = new FileInputStream("campus_map.png");
            Image image = new Image(inStream);
            iv.setImage(image);
            iv.setFitWidth(VIEWPORT_WIDTH);
            iv.setFitHeight(VIEWPORT_HEIGHT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mapPane.getChildren().addAll(iv, graphPane);
    }


    public void showAllPaths() {

        graphPane.getChildren().clear();

        double [][] nodeCoords = mapGraph.getNodeCoords();
        ArrayList<Double[]> edgeCoords = mapGraph.getEdgeCoords();

        for (double[] coord : nodeCoords) {

            double x = coord[0];
            double y = coord[1];

            Circle temp = new Circle(x/SCALE, y/SCALE,2, Style.nodeColor);
            graphPane.getChildren().add(temp);
        }

        for (Double[] coords : edgeCoords) {
            double x1 = coords[0];
            double y1 = coords[1];
            double x2 = coords[2];
            double y2 = coords[3];

            Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
            temp.setStroke(Style.edgeColor);
            graphPane.getChildren().add(temp);
        }
    }

    public void findPath(String from, String to) {

        System.out.println("From (" + from + ") to (" + to + ")");

//        int start = (int)(Math.random()*810);
//        int end = (int)(Math.random()*810);

        int start = CampusBuildings.buildingNodes.get(from);
        int end = CampusBuildings.buildingNodes.get(to);

        Dijkstra pathFinder = new Dijkstra(mapGraph.adjacency, start, end);
        ArrayList<Integer> bestPath = pathFinder.findPath();

        // Mark with circles the endpoints
        Circle startCircle = new Circle(mapGraph.nodes.get(start).x/SCALE, mapGraph.nodes.get(start).y/SCALE, 5 , Style.edgeColor);
        Circle endCircle = new Circle(mapGraph.nodes.get(end).x/SCALE, mapGraph.nodes.get(end).y/SCALE, 5 , Style.edgeColor);

        graphPane.getChildren().clear();
        graphPane.getChildren().addAll(startCircle, endCircle);

        for (int i=0; i<bestPath.size()-1; i++) {

            // Draw the new path
            double x1 = mapGraph.nodes.get(bestPath.get(i)).x;
            double y1 = mapGraph.nodes.get(bestPath.get(i)).y;
            double x2 = mapGraph.nodes.get(bestPath.get(i+1)).x;
            double y2 = mapGraph.nodes.get(bestPath.get(i+1)).y;

            Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
            temp.setStroke(Style.edgeColor);
            temp.setStrokeWidth(2);
            graphPane.getChildren().add(temp);
        }
    }

    public Pane getPane() {
        return mapPane;
    }
}
