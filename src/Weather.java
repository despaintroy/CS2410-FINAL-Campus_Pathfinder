import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather {

    // Set coordinates to get weather from. USU'a quad is at 41.740826, -111.812780
    private final static String LAT = "41.740826";
    private final static String LON = "-111.812780";
    static URL url;

    static float getTempF() throws CannotGetTempException {

        try {

            // Connect to the API
            url = new URL("https://climacell-microweather-v1.p.rapidapi.com/weather/realtime?unit_system=us&fields=temp&lat="+LAT+"&lon="+LON);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("x-rapidapi-host", "climacell-microweather-v1.p.rapidapi.com");
            con.setRequestProperty("x-rapidapi-key", "e8474ed0b6mshbaa0ce183838137p133febjsnec51952628de");

            // Handle is the server response is not 2xx
            int status = con.getResponseCode();

            if (status/100 != 2) {
                throw new CannotGetTempException(
                        "Connection status code " + status + " from server is not 2xx.\n" +
                        "URL = " + url.toString()
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
            String temp = returned.substring(returned.indexOf("value")+7, returned.indexOf("value")+7+4);

            con.disconnect();
            return Float.parseFloat(temp);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new CannotGetTempException("Malformed URL\n" + "URL = " + url.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CannotGetTempException("IO exception while reading server response.");
        }
    }

    static class CannotGetTempException extends Exception {
        public CannotGetTempException(String message) {
            super(message);
        }
    }
}
