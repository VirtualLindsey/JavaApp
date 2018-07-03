package cfpb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class JsonReader {

    // HTTP GET request
    public String sendGet(String name) throws Exception {

        String url = "https://data.consumerfinance.gov/resource/jhzv-w97w.json?company="+
                URLEncoder.encode(name, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("X-App-Token", "z7A8MAj2zPIpDZ7PaggWlaYDL");

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

}