import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {

    float [][] adjacency;
    ArrayList<Node> nodes;
    boolean [][] edges;

    Graph(String nodesFile, String edgesFile) {

        nodes = new ArrayList<>();

        try {
            Scanner inFile = new Scanner(new File(nodesFile));
            while (inFile.hasNextLine()) {
                String line = inFile.nextLine();
                String [] coords = line.split(" ");
                float x = Float.parseFloat(coords[0]);
                float y = Float.parseFloat(coords[1]);
                nodes.add(new Node(x, y));
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        edges = new boolean[nodes.size()][nodes.size()];

        try {
            Scanner inFile = new Scanner(new File(edgesFile));
            for (int i=0; inFile.hasNextLine(); i++) {
                String line = inFile.nextLine();
                String [] inSplit = line.split(" ");
                for (int j=0; j<=i; j++) {
                    edges[i][j] = inSplit[j].equals("1");
                 }
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {

        String build = "";

        for (Node n : nodes) {
            build += n.toString() + '\n';
        }
        build += "\n\n";

        for (boolean[] line : edges) {
            for (boolean e : line) {
                build += (e ? "1" : "0") + " ";
            }
            build += "\n";
        }

        return build;
    }

    public float[][] getNodeCoords() {
        float[][] temp = new float[nodes.size()][2];
        for (int i=0; i<nodes.size(); i++) {
            temp[i][0] = nodes.get(i).x;
            temp[i][1] = nodes.get(i).y;
        }
        return temp;
    }

    public ArrayList<Float[]> getEdgeCoords() {
        ArrayList<Float[]> temp = new ArrayList<>();
        for (int i=0; i<edges.length; i++) {
            for (int j=0; j<edges[i].length; j++) {

                if (edges[i][j]) {
                    Float[] line = new Float[4];
                    line[0] = nodes.get(i).x;
                    line[1] = nodes.get(i).y;
                    line[2] = nodes.get(j).x;
                    line[3] = nodes.get(j).y;

                    temp.add(line);
                }
            }
        }
        return temp;
    }
}
