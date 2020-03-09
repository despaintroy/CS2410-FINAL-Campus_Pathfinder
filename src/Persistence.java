import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;


class Persistence {

    final static String JSON_FILE_PATH = "data/persist.json";

    /**
     * Add a new value, or update an existing value in the persistence file.
     *
     * @param key the key used to reference the value
     * @param value the value to store
     */
    static void set(String key, String value) {

        JSONParser parser = new JSONParser();
        JSONObject obj;

        // Create a JSON object from file
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            obj = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            obj = new JSONObject();
        }

        // Put the new value in the JSON object
        obj.put(key, value);

        // Write the JSON object back to file
        try (FileWriter file = new FileWriter(JSON_FILE_PATH)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieve a value from the persistence file
     *
     * @param key the key used to reference the value
     * @return the value stored at that key
     * @throws CannotGetValueException
     */
    static String get(String key) throws CannotGetValueException {

        JSONParser parser = new JSONParser();
        JSONObject obj;

        // Create a new JSON object from file
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            obj = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new CannotGetValueException("Persistence file not accessible");
        }


        // Return the specified key
        String toReturn = obj.get(key).toString();

        if (toReturn.equals("null")) {
            throw new CannotGetValueException("Key not found in file");
        }

        return toReturn;
    }

    static class CannotGetValueException extends Exception {
        public CannotGetValueException(String message) {
            super(message);
        }
    }
}
