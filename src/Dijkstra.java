import java.util.ArrayList;

public class Dijkstra {

    double [][] graph;
    ArrayList<Integer> traversed;
    int start;
    int destination;

    public Dijkstra(double[][] graph, int start, int destination) {
        this.graph = graph;
        this.start = start;
        this.destination = destination;
        traversed = new ArrayList<>();
    }

    public ArrayList<Integer> findPath() {

        ArrayList<Integer> best = new ArrayList<Integer>();

        Node N = new Node(0, null);

        Node found = find(N);

        while (found != null) {
            best.add(0, found.value);
            found = found.parent;
        }

        return best;
    }

    private Node find(Node curr) {

        traversed.add(curr.value);

//        System.out.println(
//                "\n\nCurrent: " + curr.value +
//                "\nParent: " + curr.parent
//        );

        if (curr.value == destination) {
//            System.out.println("My value is the destination!");
            return curr;
        }

        // Find all nodes connected to this one
        ArrayList<Node> nextNodes = new ArrayList<>();

        for (int i=0; i<graph.length; i++) {
            for (int j=0; j<graph[i].length; j++) {
                int temp;
                if (graph[i][j]!=-1 && i==curr.value && j!=i) {
                    nextNodes.add(new Node(j, curr));
                }
                if (graph[i][j]!=-1 && j==curr.value && i!=j) {
                    nextNodes.add(new Node(i, curr));
                }
            }
        }

        // Eliminate all nodes already traversed
        for (int i=0; i<nextNodes.size(); i++) {
            if (traversed.contains(nextNodes.get(i).value)) {
                nextNodes.remove(i);
                i--;
            }
        }

        // For each node, traverse
        for (Node n : nextNodes) {
//            System.out.println("Next search: " + n.value);
            Node found = find(n);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    private class Node {
        int value;
        Node parent;

        public Node(int value, Node parent) {
            this.value = value;
            this.parent = parent;
        }
    }

    private boolean alreadyTraversed(int i) {
        return traversed.contains(i);
    }
}
