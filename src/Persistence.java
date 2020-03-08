import com.CampusGraph.Node;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

// TODO: Implement get() and set() methods of the Persistence class

public class Persistence {

    public static String get(String key) {

//        try {
//            Object obj = new JSONParser().parse(new FileReader("data/persist.json"));
//            JSONArray ja = (JSONArray) obj;
//
//            Iterator itr2 = ja.iterator();
//
//            while (itr2.hasNext()) {
//                JSONObject node = (JSONObject) itr2.next();
//                double x = Double.parseDouble(node.get("x").toString());
//                double y = Double.parseDouble(node.get("y").toString());
//                nodes.add(new Node(x, y));
//            }
//
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }

        return "";
    }
}
