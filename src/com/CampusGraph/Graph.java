package com.CampusGraph;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Graph {

    // TODO: Need to make these private
    public double [][] adjacency;
    public ArrayList<Node> nodes;
    private boolean [][] edges;

    public Graph(String nodesFile, String edgesFile) {

        nodes = new ArrayList<>();

        // Read nodes from file
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


        // Read the edges from file
        edges = new boolean[nodes.size()][nodes.size()];
        adjacency = new double[nodes.size()][nodes.size()];

        for (double[] line : adjacency) {
            Arrays.fill(line, -1);
        }

        try {
            Object obj = new JSONParser().parse(new FileReader(edgesFile));
            JSONArray ja = (JSONArray) obj;

            Iterator itr2 = ja.iterator();

            while (itr2.hasNext()) {
                JSONObject node = (JSONObject) itr2.next();
                int n1 = Integer.parseInt(node.get("n1").toString());
                int n2 = Integer.parseInt(node.get("n2").toString());
                boolean active = Boolean.parseBoolean(node.get("active").toString());

                if (active) {
                    edges[n1][n2] = true;

                    // Create the adjacency matrix
                    double [] p1 = {nodes.get(n1).x, nodes.get(n1).y};
                    double [] p2 = {nodes.get(n2).x, nodes.get(n2).y};
                    adjacency[n1][n2] = dist(p1, p2);
                }

            }

        } catch (IOException | ParseException e) {
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
