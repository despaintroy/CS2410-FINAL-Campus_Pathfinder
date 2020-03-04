import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Arrays;

public class Dijkstra {

    double [][] graph;
    Info [][] matrix;
    int start;
    int destination;

    public Dijkstra(double[][] graph, int start, int destination) {

        this.graph = graph;
        this.start = start;
        this.destination = destination;

        matrix = new Info[graph.length][graph.length];

        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix.length; j++) {
                matrix[i][j] = new Info(-1, Double.MAX_VALUE);
            }
        }
    }

    public ArrayList<Integer> findPath() {

        ArrayList<Integer> currList = new ArrayList<>();
        ArrayList<Integer> nextList = new ArrayList<>();

        currList.add(start);

        matrix[0][start] = new Info(start, 0);

//        printMatrix();

        for (int level=1; level<matrix.length; level++) {

            matrix[level] = matrix[level - 1].clone();

//            printMatrix();

            for (int curr : currList) {

                ArrayList<Integer> conns = getConnections(curr);
//                System.out.println("Conns: " + conns);

                for (int c : conns) {
                    if (matrix[level][c].distance > matrix[level - 1][curr].distance + getGraph(curr, c)) {
                        matrix[level][c] = new Info(curr, matrix[level - 1][curr].distance + getGraph(curr, c));
                        nextList.add(c);
                    }
                }

//                printMatrix();
            }

            currList = nextList;
            nextList = new ArrayList<>();
        }

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


    private ArrayList<Integer> getConnections(int num) {

        ArrayList<Integer> ints = new ArrayList<>();

        for (int i=0; i<graph.length; i++) {
            for (int j=0; j<i; j++){
                if (graph[i][j]>=0){

                    if (i==num) {
                        ints.add(j);
                    }
                    if (j==num){
                        ints.add(i);
                    }
                }
            }
        }

        return ints;
    }

    private double getGraph(int i, int j) {
        return graph[Math.max(i, j)][Math.min(i, j)];
    }


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

    private class Info {
        int pred;
        double distance;

        public Info(int pred, double distance) {
            this.pred = pred;
            this.distance = distance;
        }
    }
}
