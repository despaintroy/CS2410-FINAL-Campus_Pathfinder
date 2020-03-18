package CampusMapView.Graph;

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
                    double[] p1 = {nodes.get(n1).getX(), nodes.get(n1).getY()};
                    double[] p2 = {nodes.get(n2).getX(), nodes.get(n2).getY()};
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
            temp[i][0] = nodes.get(i).getX();
            temp[i][1] = nodes.get(i).getY();
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
                    line[0] = nodes.get(i).getX();
                    line[1] = nodes.get(i).getY();
                    line[2] = nodes.get(j).getX();
                    line[3] = nodes.get(j).getY();

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
     * Finds the closest node to the given coordinate
     *
     * @param x given x coordinate
     * @param y given y coordinate
     * @return the index of the closest node on the graph
     */
    public int getClosestNode(double x, double y) throws EmptyGraphException {

        if (nodes.size()<1) {
            throw new EmptyGraphException("No nodes in graph");
        }

        int closestNodeId = 0;
        double closestDist = dist(new double[]{nodes.get(0).getX(), nodes.get(0).getY()}, new double[]{x, y});

        for (int i=0; i<nodes.size(); i++) {
            Node n = nodes.get(i);
            double d = dist(new double[]{n.getX(), n.getY()}, new double[]{x, y});
            if (d < closestDist) {
                closestDist = d;
                closestNodeId = i;
            }
        }
        return closestNodeId;
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


    public static class EmptyGraphException extends Exception {
        EmptyGraphException (String message) {
            super(message);
        }
    }
}
