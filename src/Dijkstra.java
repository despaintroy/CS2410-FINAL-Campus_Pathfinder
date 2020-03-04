import java.util.ArrayList;

public class Dijkstra {

    double [][] graph;
    PathNode[] pathNodes;
    int start;
    int destination;

    public Dijkstra(double[][] graph, int start, int destination) {
        this.graph = graph;
        this.start = start;
        this.destination = destination;
        pathNodes = new PathNode[graph.length];

        for (int i=0; i<pathNodes.length; i++) {
            pathNodes[i] = new PathNode();
        }
    }

    public ArrayList<Integer> findPath() {

        ArrayList<Integer> best = new ArrayList<Integer>();

        pathNodes[start].distance = 0;
        pathNodes[start].parent = start;

        find(start);

        int curr = destination;

        while (curr != start) {
            best.add(0, curr);
            curr = pathNodes[curr].parent;
        }
        best.add(0, pathNodes[curr].parent);

        return best;
    }

    private void find(int curr) {

//        System.out.println(
//                "\n\nCurrent: " + curr +
//                "\nParent: " + pathNodes[curr].parent
//        );

        // Detect if the destination node is found
        if (curr == destination) {
//            System.out.println("My value is the destination!");
            return;
        }

        // Find all nodes connected to this one that we can travel to
        ArrayList<Integer> nextList = new ArrayList<>();

        for (int i=0; i<graph.length; i++) {
            for (int j=0; j<i; j++) {

                // Determine if this edge is on the graph
                if (graph[i][j] >= 0) {

                    int next = -1;

                    // Find if this is a next node
                    if (i == curr) {
                        next = j;
                    } else if (j == curr) {
                        next = i;
                    }

                    if (next>=0) {

                        // If this next node has a longer path, update it's parent
                        if (pathNodes[next].distance > pathNodes[curr].distance + graph[Math.max(curr,next)][Math.min(curr,next)]) {

                            // If it didn't have a parent, we'll add it to the next list
                            if (pathNodes[next].parent < 0) {
                                nextList.add(next);
//                                pathNodes[next].parent = curr;

//                            System.out.println("Added to next: " + j);
                            }

                            pathNodes[next].distance = pathNodes[curr].distance + graph[Math.max(curr,next)][Math.min(curr,next)];
                            pathNodes[next].parent = curr;
                        }
                    }
                }
            }
        }

        // For each node, traverse
        for (int n : nextList) {
//            System.out.println("Next search: " + n);
            find(n);
        }

        return;
    }

    private class PathNode {
        int parent;
        double distance;

        public PathNode() {
            parent = -1;
            distance = Double.MAX_VALUE;
        }

        public PathNode(int parent, double distance) {
            this.parent = parent;
            this.distance = distance;
        }
    }
}
