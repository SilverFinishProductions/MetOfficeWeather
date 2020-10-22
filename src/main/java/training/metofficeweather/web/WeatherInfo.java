package training.metofficeweather.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.unbescape.json.JsonEscapeLevel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeatherInfo {

    private final String locName;
    private final List<WeatherClass> data;

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

        JsonObject object = parser.parse(result).getAsJsonObject();
        JsonArray days = object.get("SiteRep").getAsJsonObject().get("DV").getAsJsonObject().get("Location").getAsJsonObject().get("Period").getAsJsonArray();
        List<WeatherClass> weatherEvents = new ArrayList<>();
        for(JsonElement i : days){//per day
            JsonArray rep = i.getAsJsonObject().get("Rep").getAsJsonArray();//series of dates
            String date = i.getAsJsonObject().get("value").getAsString();
            Integer time = 0;
            for (JsonElement i2 : rep) { // 3 HOURS
                JsonObject period = i2.getAsJsonObject();
                WeatherClass event = new WeatherClass(date,period.get("T").getAsInt(),period.get("F").getAsInt(),
                        period.get("W").getAsString(), period.get("S").getAsInt(),period.get("G").getAsInt(),
                        period.get("D").getAsString(),period.get("V").getAsString(), period.get("U").getAsInt(),
                        period.get("H").getAsInt(),period.get("Pp").getAsInt(),time);
                weatherEvents.add(event);
                time += 3;
            }
        }
        data = weatherEvents;

        //System.out.println(days);
        System.out.println("Constructor finished");
    }

    public String getLocName() {
        return locName;
    }

    public List<WeatherClass> getData() {
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
