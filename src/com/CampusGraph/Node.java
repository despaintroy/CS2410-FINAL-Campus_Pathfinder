package com.CampusGraph;

public class Node {

    // TODO: Need to make these private
    public double x;
    public double y;

    Node (double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
