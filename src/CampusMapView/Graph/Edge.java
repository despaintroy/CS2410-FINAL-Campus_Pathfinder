package CampusMapView.Graph;

public class Edge {

    private double length;
    private boolean active;
    private Node n1;
    private Node n2;

    Edge(boolean active, double length, Node n1, Node n2) {
        this.active = active;
        this.length = length;
        this.n1 = n1;
        this.n2 = n2;
    }

    double getLength() {
        return length;
    }

    // TODO: This is indoors if both of the nodes it connects are indoors
    boolean isIndoors() {
        return n1.isIndoors() && n2.isIndoors();
    }

    public boolean isActive() {
        return active;
    }

    public Node getN1() {
        return n1;
    }

    public Node getN2() {
        return n2;
    }
}