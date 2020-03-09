import com.CampusGraph.Node;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

// TODO: Implement get() and set() methods of the Persistence class

public class Persistence {

    public static void set(String key, String value) throws CannotGetValueException {

        JSONParser parser = new JSONParser();
        JSONObject obj;

        try (Reader reader = new FileReader("data/persist.json")) {
            obj = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            obj = new JSONObject();
//            throw new CannotGetValueException("Cannot access file");
        }

        obj.put(key, value);

        try (FileWriter file = new FileWriter("data/persist.json")) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) throws CannotGetValueException {

        JSONParser parser = new JSONParser();
        JSONObject obj;

        try (Reader reader = new FileReader("data/persist.json")) {
            obj = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new CannotGetValueException("Cannot access file");
        }

        String toReturn = obj.get(key)+"";

        if (toReturn.equals("null")) {
            throw new CannotGetValueException("Key doesn't exist in file");
        }

        return toReturn;

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
    }

    static class CannotGetValueException extends Exception {
        public CannotGetValueException(String message) {
            super(message);
        }
    }
}
