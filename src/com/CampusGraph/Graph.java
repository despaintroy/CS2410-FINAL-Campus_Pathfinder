package com.CampusGraph;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Graph {

    private Edge [][] adjacency;     // Lengths of edges
    private ArrayList<Node> nodes;   // List of nodes


    /**
     * Construct a graph from file
     *
     * @param nodesFile the path to the file containing nodes
     * @param edgesFile the path to the file containing edges
     */
    public Graph(String nodesFile, String edgesFile) {

        nodes = new ArrayList<>();

        // Read nodes from file
        try {
            Object obj = new JSONParser().parse(new FileReader(nodesFile));
            JSONArray ja = (JSONArray) obj;

            for (Object o : ja) {
                JSONObject node = (JSONObject) o;
                double x = Double.parseDouble(node.get("x").toString());
                double y = Double.parseDouble(node.get("y").toString());
                nodes.add(new Node(x, y));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        // Read the edges from file
        adjacency = new Edge[nodes.size()][nodes.size()];

        for (Edge[] line : adjacency) {
            Arrays.fill(line, new Edge(false, -1));
        }

        try {
            Object obj = new JSONParser().parse(new FileReader(edgesFile));
            JSONArray ja = (JSONArray) obj;

            for (Object o : ja) {
                JSONObject node = (JSONObject) o;
                int n1 = Integer.parseInt(node.get("n1").toString());
                int n2 = Integer.parseInt(node.get("n2").toString());
                boolean active = Boolean.parseBoolean(node.get("active").toString());

                if (active) {

                    // Create the adjacency matrix
                    double[] p1 = {nodes.get(n1).x, nodes.get(n1).y};
                    double[] p2 = {nodes.get(n2).x, nodes.get(n2).y};
                    adjacency[n1][n2] = new Edge(true, dist(p1, p2));
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

        for (Edge [] line : adjacency) {
            for (Edge e : line) {
                build += (e.isActive() ? "1" : "0") + " ";
            }
            build += "\n";
        }
        build += "\n\n";

        for (Edge [] line : adjacency) {
            for (Edge e : line) {
                build += Math.round(e.getLength()) + "\t";
            }
            build += "\n";
        }

        return build;
    }


    /**
     * Gets an array of node coordinates. First subscript is index, second subscript is x/y
     *
     * @return array of coordinates
     */
    public double[][] getNodeCoords() {
        double[][] temp = new double[nodes.size()][2];
        for (int i=0; i<nodes.size(); i++) {
            temp[i][0] = nodes.get(i).x;
            temp[i][1] = nodes.get(i).y;
        }
        return temp;
    }


    /**
     * Gets a list of edge startpoints and endpoints {x1, y1, x2, y2}
     *
     * @return array of edge points
     */
    public ArrayList<Double[]> getEdgeCoords() {
        ArrayList<Double[]> temp = new ArrayList<>();
        for (int i=0; i<adjacency.length; i++) {
            for (int j=0; j<adjacency[i].length; j++) {

                if (adjacency[i][j].isActive()) {
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


    /**
     * Find a path using Dijkstra's Algorithm
     *
     * @param start id of start node
     * @param end id of end node
     * @return list of node id's that make up the path
     */
    public ArrayList<Integer> findPath(int start, int end) {
        Dijkstra pathFinder = new Dijkstra(adjacency, start, end);
        return pathFinder.findPath();
    }


    /**
     * Find the distance between two points
     *
     * @param p1 point 1
     * @param p2 point 2
     * @return the distance
     */
    private double dist(double[] p1, double[] p2) {
        return Math.sqrt(Math.pow(p1[0]-p2[0], 2) + Math.pow(p1[1]-p2[1], 2));
    }
}
