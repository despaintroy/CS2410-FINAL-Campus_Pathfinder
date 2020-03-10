package com.CampusGraph;

public class Node {

    private double x;
    private double y;

    Node (double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
