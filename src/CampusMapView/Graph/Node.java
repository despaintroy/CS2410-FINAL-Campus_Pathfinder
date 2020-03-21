package CampusMapView.Graph;

public class Node {

    private double x;
    private double y;
    private int id;
    private boolean indoors;
    private String buildingCode;

    Node (double x, double y, int id, String buildingCode) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.indoors = buildingCode.length()>0;
        this.buildingCode = buildingCode;
    }

    // TODO: Create a method to determine if the node is a building entrance

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
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

    public void setIndoors(boolean indoors) {
        this.indoors = indoors;
    }

    public boolean isIndoors() {
        return indoors;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
