import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class Weather {

    // Set coordinates to get weather from. USU'a quad is at 41.740826, -111.812780
    private final static String LAT = "41.740826";
    private final static String LON = "-111.812780";
    private final static Long REFRESH_TIME = (long) (60 * 20);
    private static URL apiURL;


    // TODO: use the feels like temperature
    /**
     * Public method for getting the current temperature at the USU Logan Campus
     *
     * @return the current temperature
     * @throws CannotGetTempException
     */
    static double getTempF() throws CannotGetTempException {

        long lastUpdated = 0;

        // Find the time that the weather was last updated
        try {
            lastUpdated = Long.parseLong(Persistence.get("Weather.lastUpdated"));
        } catch (Persistence.CannotGetValueException e) {
            updateTemp();
            try {
                lastUpdated = Long.parseLong(Persistence.get("Weather.lastUpdated"));
            } catch (Persistence.CannotGetValueException f) {
                f.printStackTrace();
            }
        }

        // Refresh weather if not updated recently enough
        if (lastUpdated - System.currentTimeMillis() > REFRESH_TIME) {
            updateTemp();
        }

        // Return the temperature
        try {
            String toReturn = Persistence.get("Weather.temperature");
            return Double.parseDouble(toReturn);
        } catch (Persistence.CannotGetValueException e) {
            updateTemp();
            try {
                String toReturn = Persistence.get("Weather.temperature");
                return Double.parseDouble(toReturn);
            } catch (Persistence.CannotGetValueException f) {
                throw new CannotGetTempException("Cannot get value from file");
            }
        }
    }


    /**
     * Updates the temperature in the persistence file with the new temperature
     *
     * @throws CannotGetTempException
     */
    private static void updateTemp() throws CannotGetTempException {

        System.out.println("Making an API call...");

        try {

            Persistence.set("Weather.lastUpdated", System.currentTimeMillis()+"");

            // Connect to the API
            apiURL = new URL("https://climacell-microweather-v1.p.rapidapi.com/weather/realtime?unit_system=us&fields=temp&lat=" + LAT + "&lon=" + LON);
            HttpURLConnection con = (HttpURLConnection) apiURL.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("x-rapidapi-host", "climacell-microweather-v1.p.rapidapi.com");
            con.setRequestProperty("x-rapidapi-key", "e8474ed0b6mshbaa0ce183838137p133febjsnec51952628de");

            // Check to make sure a 200 response code was received
            int status = con.getResponseCode();

            if (status / 100 != 2) {
                throw new CannotGetTempException(
                        "Connection status code " + status + " from server is not 2xx.\n" +
                                "URL = " + apiURL.toString()
                );
            }

            // Read the server response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Trim the temp out of the string
            String returned = content.toString();
            String temp = returned.substring(returned.indexOf("value") + 7, returned.indexOf("value") + 7 + 4);

            con.disconnect();
            System.out.println("Complete.");
            Persistence.set("Weather.temperature", temp);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new CannotGetTempException("Malformed URL\n" + "URL = " + apiURL.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CannotGetTempException("IO exception while reading server response.");
        }
    }

    static class CannotGetTempException extends Exception {
        CannotGetTempException(String message) {
            super(message);
        }
    }
}
