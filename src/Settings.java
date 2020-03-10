import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;


class Settings {

    private final static String SETTINGS_JSON = "data/settings.json";

    /**
     * Add a new value, or update an existing value in the settings file.
     *
     * @param key the key used to reference the value
     * @param value the value to store
     */
    static void set(String key, String value) {

        JSONParser parser = new JSONParser();
        JSONObject obj;

        // Create a JSON object from file
        try (Reader reader = new FileReader(SETTINGS_JSON)) {
            obj = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            obj = new JSONObject();
        }

        // Put the new value in the JSON object
        obj.put(key, value);

        // Write the JSON object back to file
        try (FileWriter file = new FileWriter(SETTINGS_JSON)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieve a value from the settings file
     *
     * @param key the key used to reference the value
     * @return the value stored at that key
     * @throws CannotGetValueException
     */
    static String get(String key) throws CannotGetValueException {

        JSONParser parser = new JSONParser();
        JSONObject obj;

        // Create a new JSON object from file
        try (Reader reader = new FileReader(SETTINGS_JSON)) {
            obj = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new CannotGetValueException("Settings file not accessible");
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
