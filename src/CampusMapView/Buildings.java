package CampusMapView;

import CampusMapView.Graph.Node;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Buildings {

    private static Map<String, String> buildings = new Hashtable<>();
    private static Map<String, ArrayList<Integer>> buildingNodes = new Hashtable<>();

    public Buildings() {
        // TODO: Get rid of these and have them read in from file instead
        buildingNodes.put("LSB", new ArrayList<Integer>());
        buildingNodes.put("MAIN", new ArrayList<Integer>());
        buildingNodes.put("ENGR", new ArrayList<Integer>());
        buildingNodes.put("FL", new ArrayList<Integer>());
        buildingNodes.put("LDSI", new ArrayList<Integer>());
    }

    public static String[] getCodes() {

        String[] buildList = new String[buildingNodes.size()];

        int i=0;
        for (String building : buildingNodes.keySet()){
            buildList[i] = building;
            i++;
        }
        return buildList;
    }

    // TODO: Make this return the whole list as an array
    public static ArrayList<Integer> getNodes(String buildingCode) {
        return buildingNodes.get(buildingCode);
    }

    public static void addNode(String buildingCode, Node nodeToAdd) {
        buildingNodes.get(buildingCode).add(nodeToAdd.getId());
    }
}
