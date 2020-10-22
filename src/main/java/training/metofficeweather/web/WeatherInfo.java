package training.metofficeweather.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class WeatherInfo {

    private final String locName;
    private final String data;

    public WeatherInfo(String locName) {

        System.out.println("Constructor started");
        this.locName = locName;

        JsonParser parser = new JsonParser();
        HashMap<String, String> sites = new HashMap<>();
        String siteList = request("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=d9db0ba4-7eac-46da-a83c-fb074bb8015d");
        JsonObject objectSites = parser.parse(siteList).getAsJsonObject();
        JsonArray arraySites = objectSites.get("Locations").getAsJsonObject().get("Location").getAsJsonArray();
        for (JsonElement i : arraySites) { // SITE
            sites.put(i.getAsJsonObject().get("name").getAsString(), i.getAsJsonObject().get("id").getAsString());
        }

        String result = request("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/" + sites.get(locName) + "?res=3hourly&key=d9db0ba4-7eac-46da-a83c-fb074bb8015d");

        data = result;
        System.out.println("Constructor finished");
    }

    public String getLocName() {
        return locName;
    }

    public String getData() {
        return data;
    }

    public static String request(String link) {

        try {

            URL url = new URL(link);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();

        } catch (Exception e) {

        }

        return null;

    }



}
