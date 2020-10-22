package training.metofficeweather.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.unbescape.json.JsonEscapeLevel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WeatherInfo {

    private final String locName;
    private final List<WeatherClass> data;
    private final int umbrellaChanceChange;

    public WeatherInfo(String locName,String site) {

        this.locName = locName;

        List<WeatherClass> weatherEvents = new ArrayList<>();
        HashMap<String, String> mapWeather = importJson("src\\main\\resources\\WeatherTypes.json");
        HashMap<String, String> mapVisibility = importJson("src\\main\\resources\\VisibilityTypes.json");
        generateWeatherList(site, weatherEvents, mapWeather, mapVisibility);

        data = weatherEvents;
        umbrellaChanceChange = calcUmbrellaChanceChange(weatherEvents);

    }

    private int calcUmbrellaChanceChange(List<WeatherClass> weatherEvents) {
        final int umbrellaChanceChange;
        //umbrella chance percentage change
        int currentUmbrellaChance = weatherEvents.get(0).getPrecipitation();
        int finalUmbrellaChance = weatherEvents.get(weatherEvents.size()-1).getPrecipitation();
        return finalUmbrellaChance - currentUmbrellaChance;
    }

    private void generateWeatherList(String site, List<WeatherClass> weatherEvents, HashMap<String, String> mapWeather, HashMap<String, String> mapVisibility) {
        for(JsonElement i : getJsonElementsFromLocationUrl(site)){//per day
            JsonArray rep = i.getAsJsonObject().get("Rep").getAsJsonArray();//series of dates
            String date = i.getAsJsonObject().get("value").getAsString().replace("Z","");
            Integer time = 0;
            for (JsonElement i2 : rep) { // 3 HOURS
                JsonObject period = i2.getAsJsonObject();
                WeatherClass event = new WeatherClass(date, period.get("T").getAsInt(), period.get("F").getAsInt(),
                        mapWeather.get(period.get("W").getAsString()), period.get("S").getAsInt(), period.get("G").getAsInt(),
                        period.get("D").getAsString(), mapVisibility.get(period.get("V").getAsString()), period.get("U").getAsInt(),
                        period.get("H").getAsInt(), period.get("Pp").getAsInt(), time);
                weatherEvents.add(event);
                time += 3;
            }
        }
    }

    private JsonArray getJsonElementsFromLocationUrl(String site) {
        String result = request("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/" + site + "?res=3hourly&key=d9db0ba4-7eac-46da-a83c-fb074bb8015d");
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(result).getAsJsonObject();
        JsonArray days = object.get("SiteRep").getAsJsonObject().get("DV").getAsJsonObject().get("Location").getAsJsonObject().get("Period").getAsJsonArray();
        return days;
    }

    public String getLocName() {
        return locName;
    }

    public String getUmbrellaChanceChange(){
        return Integer.toString(umbrellaChanceChange);
    }

    public static HashMap<String, String> importJson(String fileName) {
        HashMap<String, String> map = new HashMap<>();
        JsonParser parser = new JsonParser();
        try {
            JsonObject json = parser.parse(new FileReader(fileName)).getAsJsonObject();
            Set<String> keys = json.keySet();
            for (String i : keys) {
                map.put(i, json.get(i).getAsString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred importing JSON file: " + e.getMessage());
        }
        return map;
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
