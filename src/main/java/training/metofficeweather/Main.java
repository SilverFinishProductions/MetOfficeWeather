package training.metofficeweather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    public static String getInput(Scanner scanner, String question) {
        System.out.println(question);
        return scanner.nextLine();
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

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
        JsonParser parser = new JsonParser();
        HashMap<String, String> sites = new HashMap<>();
        String siteList = request("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=d9db0ba4-7eac-46da-a83c-fb074bb8015d");
        JsonObject objectSites = parser.parse(siteList).getAsJsonObject();
        JsonArray arraySites = objectSites.get("Locations").getAsJsonObject().get("Location").getAsJsonArray();
        for (JsonElement i : arraySites) { // SITE
            sites.put(i.getAsJsonObject().get("name").getAsString(), i.getAsJsonObject().get("id").getAsString());
        }

        while (true) {

            String location = getInput(scanner, "Enter location: ");

            if (sites.containsKey(location)) {

                String result = request("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/" + sites.get(location) + "?res=3hourly&key=d9db0ba4-7eac-46da-a83c-fb074bb8015d");

                JsonObject object = parser.parse(result).getAsJsonObject();
                JsonArray array = object.get("SiteRep").getAsJsonObject().get("DV").getAsJsonObject().get("Location").getAsJsonObject().get("Period").getAsJsonArray();
                for (JsonElement i : array) { // DAY
                    JsonArray rep = i.getAsJsonObject().get("Rep").getAsJsonArray();
                    System.out.println(i.getAsJsonObject().get("value").toString());
                    Integer time = 0;
                    for (JsonElement i2 : rep) { // 3 HOURS
                        JsonObject period = i2.getAsJsonObject();
                        System.out.println("Time: " + Integer.toString(time) + ":00" +
                                " | Temp: " + period.get("T").getAsString() + "°C" + " (" + period.get("F").getAsString() + ")" +
                                " | Weather: " + period.get("W").getAsString() +
                                " | Wind: " + period.get("S").getAsString() + " MPH" + " (" + period.get("G").getAsString() + ")" + " " + period.get("D").getAsString() +
                                " | Visibility: " + period.get("V").getAsString() +
                                " | Humidity: " + period.get("H").getAsString() + "%" +
                                " | Precipitation: " + period.get("Pp").getAsString() + "%"
                        );
                        time += 3;
                    }
                }

            } else {

                System.out.println("Wrong");

            }
        }
    }
}	
