package CampusMapView.Graph;

class Edge {

    private double length;
    private boolean active;
    private boolean indoors;

    // TODO: add indoors to constructor
    Edge(boolean active, double length) {
        this.active = active;
        this.length = length;
        this.indoors = false;
    }

    double getLength() {
        return length;
    }

    boolean isIndoors() {
        return this.indoors;
    }

    boolean isActive() {
        return active;
    }

    void setIndoors(Boolean indoors) {
        this.indoors = indoors;
    }
}