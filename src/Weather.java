//package okhttp3.guide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
import org.omg.CORBA.Request;

import javax.xml.ws.Response;

public class Weather {

    static float getTempF() {

        try {
            URL url = new URL("https://climacell-microweather-v1.p.rapidapi.com/weather/realtime?unit_system=us&fields=temp&lat=41.740826&lon=-111.812780");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("x-rapidapi-host", "climacell-microweather-v1.p.rapidapi.com");
            con.setRequestProperty("x-rapidapi-key", "e8474ed0b6mshbaa0ce183838137p133febjsnec51952628de");

            int status = con.getResponseCode();

            if (status/100 == 2) {

                BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                String returned = content.toString();

                String temp = returned.substring(returned.indexOf("value")+7, returned.indexOf("value")+7+4);
                System.out.println(temp);

                con.disconnect();
                return Float.parseFloat(temp);
            }
            else {
                System.out.println("Not a good status from the server");
            }
            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("There is an error!!!!!!!!!!!!!!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There is an error!!!!!!!!!!!!!!");
        }

//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("https://climacell-microweather-v1.p.rapidapi.com/weather/nowcast?fields=precipitation&unit_system=si&lat=42.8237618&lon=-71.2216286")
//                .get()
//                .addHeader("x-rapidapi-host", "climacell-microweather-v1.p.rapidapi.com")
//                .addHeader("x-rapidapi-key", "e8474ed0b6mshbaa0ce183838137p133febjsnec51952628de")
//                .build();
//
//        Response response = client.newCall(request).execute();

        return -1;
    }
}
