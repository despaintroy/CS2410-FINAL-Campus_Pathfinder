package com.CampusGraph;

import java.util.Hashtable;
import java.util.Map;

public class Buildings {

    private static Map<String, String> buildings = new Hashtable<>();
    private static Map<String, Integer> buildingNodes = new Hashtable<>();

    public Buildings() {
        // TODO: Get rid of these and have them read in from file instead
        buildingNodes.put("Life Sciences", 438);
        buildingNodes.put("Old Main", 37);
        buildingNodes.put("Engineering", 345);
        buildingNodes.put("Family Life", 12);
        buildingNodes.put("Institute", 833);
    }

    public static int getBuildNodeId(String buildingName) {
        return buildingNodes.get(buildingName);
    }
}
