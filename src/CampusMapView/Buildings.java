package CampusMapView;

import CampusMapView.Graph.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class Buildings {

    final String BUILDINGS_FILENAME = "data/buildingList.csv";

    private static Map<String, String> buildings = new Hashtable<>();
    private static Map<String, ArrayList<Integer>> buildingNodes = new Hashtable<>();

    public Buildings() {

        try {
            File buildingFile = new File(BUILDINGS_FILENAME);
            Scanner sc = new Scanner(buildingFile);
            while (sc.hasNextLine()) {
                buildingNodes.put(sc.next(), new ArrayList<>());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Building file not found");
            e.printStackTrace();
        }
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

    public static ArrayList<String> getCodesWithNodes(){
        ArrayList<String> buildList = new ArrayList<>();
        for (String building : buildingNodes.keySet()) {
            if (buildingNodes.get(building).size()>0) {
                buildList.add(building);
            }
        }
        return buildList;
    }

        // TODO: Make this return the whole list as an array
    public static ArrayList<Integer> getNodes(String buildingCode) {
        if (buildingCode==null || buildingCode.equals("")) {
            return new ArrayList<Integer>();
        }
        return buildingNodes.get(buildingCode);
    }

    public static void addNode(String buildingCode, Node nodeToAdd) {
        buildingNodes.get(buildingCode).add(nodeToAdd.getId());
    }
}
