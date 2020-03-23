package CampusMapView.Graph;

import java.util.ArrayList;

class Dijkstra {

    private double [][] adjacency;  // Adjacency matrix that holds edge weights
    private Info [][] dMatrix;       // 2D Array of Info objects for running Dijkstra's algorithm
    private ArrayList<Integer> start;              // ID of the starting node
    private ArrayList<Integer> destination;        // ID of the destination node
    private double indoorWeight;


    /**
     * Constructor
     *
     * @param graph Adjacency matrix to use
     * @param start Index of the start node
     * @param destination Index of the end node
     */
    // TODO: Make this using arrays instead
    Dijkstra(Edge[][] graph, ArrayList<Integer> start, ArrayList<Integer> destination, double indoorWeight) {

        this.adjacency = new double[graph.length][graph.length];
        this.start = start;
        this.destination = destination;
        this.indoorWeight = indoorWeight;

        // Populate the adjacency matrix with weighted edges
        for (int i=0; i<adjacency.length; i++) {
            for (int j=0; j<adjacency[0].length; j++) {
                Edge edge = graph[i][j];
                adjacency[i][j] = getWeight(edge);
            }
        }

        // Initialize the Dijkstra matrix
        dMatrix = new Info[graph.length][graph.length];

        for (int i = 0; i< dMatrix.length; i++) {
            for (int j = 0; j< dMatrix.length; j++) {
                dMatrix[i][j] = new Info(-1, Double.MAX_VALUE);
            }
        }
    }

    // TODO: I think we can do this using only one level...lets try that. We just need to quit when nextList is empty
    /**
     * Finds the shortest path between two nodes
     *
     * @return List of integers representing the points along the way
     */
    Integer [] findPath() {

        long startTime = System.currentTimeMillis();

        ArrayList<Integer> currList = new ArrayList<>();    // List of all nodes working on in the current level
        ArrayList<Integer> nextList = new ArrayList<>();    // List of adjacent nodes that need to be checked next

//        int begin = start.get(0);
//        int end = destination.get(0);

        ArrayList<Integer> bestPath = new ArrayList<>();
        double shortestDistance = Double.MAX_VALUE;

        int totalCalculations = start.size() * destination.size();
        int counter = 1;

        for (int begin : start) {
            for (int end : destination) {

                System.out.print("Calculating " + counter++ + "/" + totalCalculations + "...\r");

                // Beginning at the start node
                currList.add(begin);
                dMatrix[0][begin] = new Info(begin, 0);

                for (int level = 1; level < dMatrix.length; level++) {

                    // Copy the Info objects down from the previous level
                    dMatrix[level] = dMatrix[level - 1].clone();

                    // For each current node, get adjacent nodes, then update their info if we've found a shorter distance
                    for (int curr : currList) {

                        ArrayList<Integer> conns = getAdjacentNodes(curr);

                        for (int c : conns) {
                            if (dMatrix[level][c].totalWeight > dMatrix[level - 1][curr].totalWeight + getWeight(curr, c)) {
                                dMatrix[level][c] = new Info(curr, dMatrix[level - 1][curr].totalWeight + getWeight(curr, c));
                                nextList.add(c);
                            }
                        }
                    }

                    currList = nextList;
                    nextList = new ArrayList<>();
                }

                // Store the distance
                double pathDistance = dMatrix[dMatrix.length - 1][end].totalWeight;

                // Assemble the path from the start to destination nodes
                ArrayList<Integer> path = new ArrayList<>();

                int curr = end;
                path.add(end);

                for (int level = dMatrix.length - 1; level >= 0; level--) {

                    //TODO: Why would it ever be -1?
                    if (curr==-1) {
                        break;
                    }
                    if (dMatrix[level][curr].pred == path.get(0)) {
                        break;
                    }
                    path.add(0, dMatrix[level][curr].pred);
                    curr = path.get(0);
                }

                if (pathDistance < shortestDistance) {
                    bestPath = path;
                    shortestDistance = pathDistance;
                }
            }
        }

        System.out.println("Done. (" + (System.currentTimeMillis()-startTime)/1000.0 +")");

        Integer[] arr = new Integer[bestPath.size()];
        arr = bestPath.toArray(arr);
        return arr;
    }

    // TODO: Make this a lot more faster by just checking the rows and columns in the adjacency
    /**
     * Finds all nodes directly connected to a specified node
     *
     * @param id the id of the specified node
     * @return a list of id's of adjacent nodes
     */
    private ArrayList<Integer> getAdjacentNodes(int id) {

        ArrayList<Integer> ints = new ArrayList<>();

        for (int i=id+1; i<adjacency.length; i++) {
            if (adjacency[i][id] >= 0) {
                ints.add(i);
            }
        }

        for (int i=0; i<id; i++) {
            if (adjacency[id][i] >= 0) {
                ints.add(i);
            }
        }

        return ints;
    }


    /**
     * Gets a weight from the adjacency matrix (distance between the given nodes)
     * @param n1 the ID of the first node
     * @param n2 the ID of the second node
     *
     * @return the distance between the nodes form the adjacency matrix
     */
    private double getWeight(int n1, int n2) {
        return adjacency[Math.max(n1, n2)][Math.min(n1, n2)];
    }


    private double getWeight(Edge edge) {
        double weight = edge.getLength();
        if (edge.isActive() && edge.isIndoors()) {weight *= indoorWeight;}
        return weight;
    }


//    TODO: Delete this, for debugging only
    private void printMatrix() {

        System.out.println("MATRIX:");
        for (Info[] row : dMatrix) {
            for (Info d : row) {
                System.out.print("(" + (d.pred == -1 ? "_" : d.pred) + ", " + (d.totalWeight == Double.MAX_VALUE ? "___" : Math.round(d.totalWeight)) + ")" + "\t");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }


    /**
     * Class to store a predecessor and accumulated distance
     */
    private class Info {
        int pred;
        double totalWeight;

        Info(int pred, double distance) {
            this.pred = pred;
            this.totalWeight = distance;
        }
    }
}
