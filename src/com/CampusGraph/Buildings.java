package com.CampusGraph;

import java.util.Hashtable;
import java.util.Map;

public class Buildings {

    // TODO: Need to make these private
    public static Map<String, String> buildings = new Hashtable<>();
    public static Map<String, Integer> buildingNodes = new Hashtable<>();

    public Buildings() {
        buildingNodes.put("Life Sciences", 438);
        buildingNodes.put("Old Main", 37);
        buildingNodes.put("Engineering", 345);
        buildingNodes.put("Family Life", 12);
        buildingNodes.put("Institute", 833);
    }
}
