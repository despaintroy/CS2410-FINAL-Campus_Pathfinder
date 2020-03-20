package CampusMapView.Graph;

public class Node {

    private double x;
    private double y;
    private int id;

    Node (double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
