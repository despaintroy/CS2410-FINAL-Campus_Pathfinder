package CampusMapView;

import java.util.Hashtable;
import java.util.Map;

public class Buildings {

    private static Map<String, String> buildings = new Hashtable<>();
    private static Map<String, Integer> buildingNodes = new Hashtable<>();

    public Buildings() {
        // TODO: Get rid of these and have them read in from file instead
        buildingNodes.put("LSB", 438);
        buildingNodes.put("MAIN", 37);
        buildingNodes.put("ENGR", 345);
        buildingNodes.put("FL", 12);
        buildingNodes.put("LDSI", 833);
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

    public static int getBuildNodeId(String buidingCode) {
        return buildingNodes.get(buidingCode);
    }
}
