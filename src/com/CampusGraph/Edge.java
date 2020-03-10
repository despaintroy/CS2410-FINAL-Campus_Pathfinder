package com.CampusGraph;

class Edge {

    private double length;
    private boolean active;

    Edge(boolean active, double length) {
        this.active = active;
        this.length = length;
    }

    double getLength() {
        return length;
    }

    boolean isActive() {
        return active;
    }
}