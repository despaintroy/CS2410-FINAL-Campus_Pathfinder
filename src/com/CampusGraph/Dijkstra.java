package com.CampusGraph;

import java.util.ArrayList;

public class Dijkstra {

    private double [][] adjacency;  // Adjacency matrix that holds distances between nodes
    private Info [][] matrix;   // 2D Array of Info objects for running Dijkstra's algorithm
    private int start;          // ID of the starting node
    private int destination;    // ID of the destination node


    public Dijkstra(double[][] graph, int start, int destination) {

        this.adjacency = graph;
        this.start = start;
        this.destination = destination;

        // Initialize the adjacency matrix
        matrix = new Info[graph.length][graph.length];

        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix.length; j++) {
                matrix[i][j] = new Info(-1, Double.MAX_VALUE);
            }
        }
    }

    // TODO: I think we can do this using only one level...lets try that. We just need to quit when nextList is empty
    public ArrayList<Integer> findPath() {

        ArrayList<Integer> currList = new ArrayList<>();    // List of all nodes working on in the current level
        ArrayList<Integer> nextList = new ArrayList<>();    // List of adjacent nodes that need to be checked next

        // Beginning at the start node
        currList.add(start);
        matrix[0][start] = new Info(start, 0);

        for (int level=1; level<matrix.length; level++) {

            // Copy the Info objects down from the previous level
            matrix[level] = matrix[level - 1].clone();

            // For each current node, get adjacent nodes, then update their info if we've found a shorter distance
            for (int curr : currList) {

                ArrayList<Integer> conns = getAdjacentNodes(curr);

                for (int c : conns) {
                    if (matrix[level][c].distance > matrix[level - 1][curr].distance + getAdjacency(curr, c)) {
                        matrix[level][c] = new Info(curr, matrix[level - 1][curr].distance + getAdjacency(curr, c));
                        nextList.add(c);
                    }
                }
            }

            currList = nextList;
            nextList = new ArrayList<>();
        }

        // Assemble the path from the start to destination nodes
        ArrayList<Integer> path = new ArrayList<>();

        int curr = destination;
        path.add(destination);
        for (int level=matrix.length-1; level>=0; level--) {
            if (matrix[level][curr].pred == path.get(0)) {break;}
            path.add(0, matrix[level][curr].pred);
            curr = path.get(0);
        }

        return path;
    }

    /**
     * Finds all nodes directly connected to a specified node
     *
     * @param id the id of the specified node
     * @return a list of id's of adjacent nodes
     */
    private ArrayList<Integer> getAdjacentNodes(int id) {

        ArrayList<Integer> ints = new ArrayList<>();

        for (int i = 0; i< adjacency.length; i++) {
            for (int j=0; j<i; j++){
                if (adjacency[i][j]>=0){

                    if (i==id) {
                        ints.add(j);
                    }
                    if (j==id){
                        ints.add(i);
                    }
                }
            }
        }

        return ints;
    }


    /**
     * Gets a value from the adjacency matrix (distance between the given nodes)
     * @param n1 the ID of the first node
     * @param n2 the ID of the second node
     *
     * @return the distance between the nodes form the adjacency matrix
     */
    private double getAdjacency(int n1, int n2) {
        return adjacency[Math.max(n1, n2)][Math.min(n1, n2)];
    }


//    TODO: Delete this, for debugging only
    private void printMatrix() {

        System.out.println("MATRIX:");
        for (Info [] row : matrix) {
            for (Info d : row) {
                System.out.print("("+(d.pred==-1?"_":d.pred)+", "+(d.distance==Double.MAX_VALUE ? "___" : Math.round(d.distance))+")" + "\t");
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
        double distance;

        public Info(int pred, double distance) {
            this.pred = pred;
            this.distance = distance;
        }
    }
}
