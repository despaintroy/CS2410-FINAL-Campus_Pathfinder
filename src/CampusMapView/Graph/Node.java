package CampusMapView.Graph;

public class Node {

    private double x;
    private double y;
    private int id;
    private boolean indoors;

    Node (double x, double y, int id, boolean indoors) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.indoors = indoors;
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

    public boolean isIndoors() {
        return indoors;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
