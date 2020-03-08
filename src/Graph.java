import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Graph {

    double [][] adjacency;
    ArrayList<Node> nodes;
    boolean [][] edges;

    Graph(String nodesFile, String edgesFile) {

        nodes = new ArrayList<>();

        try {
            Object obj = new JSONParser().parse(new FileReader(nodesFile));
            JSONArray ja = (JSONArray) obj;

            Iterator itr2 = ja.iterator();

            while (itr2.hasNext()) {
                JSONObject node = (JSONObject) itr2.next();
                double x = Double.parseDouble(node.get("x").toString());
                double y = Double.parseDouble(node.get("y").toString());
                nodes.add(new Node(x, y));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        // Read the nodes from file
//        try {
//            Scanner inFile = new Scanner(new File(nodesFile));
//            while (inFile.hasNextLine()) {
//                String line = inFile.nextLine();
//                String [] coords = line.split(" ");
//                float x = Float.parseFloat(coords[0]);
//                float y = Float.parseFloat(coords[1]);
//                nodes.add(new Node(x, y));
//            }
//            inFile.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // Read the edges from file
        edges = new boolean[nodes.size()][nodes.size()];
        adjacency = new double[nodes.size()][nodes.size()];

        for (double[] line : adjacency) {
            Arrays.fill(line, -1);
        }

//        try {
//            Scanner inFile = new Scanner(new File(edgesFile));
//            for (int i=0; inFile.hasNextLine(); i++) {
//                String line = inFile.nextLine();
//                String [] inSplit = line.split(" ");
//                for (int j=0; j<=i; j++) {
//                    if (inSplit[j].equals("1")) {
//                        edges[i][j] = true;
//
//                        // Create the adjacency matrix
//                        double [] p1 = {nodes.get(i).x, nodes.get(i).y};
//                        double [] p2 = {nodes.get(j).x, nodes.get(j).y};
//                        adjacency[i][j] = dist(p1, p2);
//                    }
//                 }
//            }
//            inFile.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

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
        build += "\n\n";

        for (double[] line : adjacency) {
            for (double e : line) {
                build += Math.round(e) + "\t";
            }
            build += "\n";
        }

        return build;
    }

    public double[][] getNodeCoords() {
        double[][] temp = new double[nodes.size()][2];
        for (int i=0; i<nodes.size(); i++) {
            temp[i][0] = nodes.get(i).x;
            temp[i][1] = nodes.get(i).y;
        }
        return temp;
    }

    public ArrayList<Double[]> getEdgeCoords() {
        ArrayList<Double[]> temp = new ArrayList<>();
        for (int i=0; i<edges.length; i++) {
            for (int j=0; j<edges[i].length; j++) {

                if (edges[i][j]) {
                    Double[] line = new Double[4];
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

    void findPath() {
        return;
    }

    double dist(double[] p1, double[] p2) {
        return Math.sqrt(Math.pow(p1[0]-p2[0], 2) + Math.pow(p1[1]-p2[1], 2));
    }
}
