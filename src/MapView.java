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

    Pane viewPane;
    Pane graph;
    Graph newGraph;

    public MapView(int viewport_width, int viewport_height) {

        System.out.println("Loading mapview...");

        VIEWPORT_WIDTH = viewport_width;
        VIEWPORT_HEIGHT = viewport_height;

        newGraph = new Graph("savedNodes.txt", "savedEdges.txt");

//        System.out.println(newGraph);

        StackPane stack = new StackPane();
        graph = new Pane();

        try {
            FileInputStream inputstream = new FileInputStream("campus_map.png");
            Image image = new Image(inputstream);
            ImageView iv1 = new ImageView();
            iv1.setImage(image);
            iv1.setFitWidth(VIEWPORT_WIDTH);
            iv1.setFitHeight(VIEWPORT_HEIGHT);
            stack.getChildren().addAll(iv1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        float [][] nodeCoords = newGraph.getNodeCoords();
        ArrayList<Float[]> edgeCoords = newGraph.getEdgeCoords();

        for (float[] coord : nodeCoords) {

            float x = coord[0];
            float y = coord[1];

            Circle temp = new Circle(x/SCALE, y/SCALE,2, Style.nodeColor);
            graph.getChildren().add(temp);
        }

        for (Float[] coords : edgeCoords) {
            float x1 = coords[0];
            float y1 = coords[1];
            float x2 = coords[2];
            float y2 = coords[3];

            Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
            temp.setStroke(Style.edgeColor);
            graph.getChildren().add(temp);
        }

        stack.getChildren().add(graph);

        viewPane = stack;
    }

    public void findPath() {

        int start = (int)(Math.random()*810);
        int end = (int)(Math.random()*810);

        Dijkstra pathFinder = new Dijkstra(newGraph.adjacency, start, end);
        ArrayList<Integer> bestPath = pathFinder.findPath();

        // Mark with circles the endpoints
        Circle startCircle = new Circle(newGraph.nodes.get(start).x/SCALE, newGraph.nodes.get(start).y/SCALE, 5 , Style.edgeColor);
        Circle endCircle = new Circle(newGraph.nodes.get(end).x/SCALE, newGraph.nodes.get(end).y/SCALE, 5 , Style.edgeColor);

//        System.out.println("The best path is: " + bestPath);

        graph.getChildren().clear();

        graph.getChildren().addAll(startCircle, endCircle);

        for (int i=0; i<bestPath.size()-1; i++) {

            // Draw the new path
            float x1 = newGraph.nodes.get(bestPath.get(i)).x;
            float y1 = newGraph.nodes.get(bestPath.get(i)).y;
            float x2 = newGraph.nodes.get(bestPath.get(i+1)).x;
            float y2 = newGraph.nodes.get(bestPath.get(i+1)).y;

            Line temp = new Line(x1/SCALE, y1/SCALE, x2/SCALE, y2/SCALE);
            temp.setStroke(Style.edgeColor);
            graph.getChildren().add(temp);
        }
    }
}
