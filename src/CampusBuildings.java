import java.util.Hashtable;
import java.util.Map;

public class CampusBuildings {

    static Map<String, String> buildings = new Hashtable<>();
    static Map<String, Integer> buildingNodes = new Hashtable<>();

    CampusBuildings() {
        buildingNodes.put("Life Sciences", 438);
        buildingNodes.put("Old Main", 37);
        buildingNodes.put("Engineering", 345);
        buildingNodes.put("Family Life", 12);
        buildingNodes.put("Institute", 833);
    }
}
