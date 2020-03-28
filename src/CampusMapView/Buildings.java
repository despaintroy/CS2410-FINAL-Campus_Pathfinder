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

        // Read in a list of all the building codes from file
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

    /**
     * Gets all of the building codes
     *
     * @return array of all the building codes
     */
    public static String[] getCodes() {

        String[] buildList = new String[buildingNodes.size()];

        int i=0;
        for (String building : buildingNodes.keySet()){
            buildList[i] = building;
            i++;
        }
        return buildList;
    }


    /**
     * Returns all the building codes with non-empty ArrayLists of nodes
     *
     * @return array of all codes with nodes
     */
    public static ArrayList<String> getCodesWithNodes(){
        ArrayList<String> buildList = new ArrayList<>();
        for (String building : buildingNodes.keySet()) {
            if (buildingNodes.get(building).size()>0) {
                buildList.add(building);
            }
        }
        return buildList;
    }


    /**
     * Returns the list of nodes from teh specified building code
     *
     * @param buildingCode the code for the specified building
     * @return the list of nodes for that building
     */
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
