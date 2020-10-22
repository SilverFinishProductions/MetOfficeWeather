package training.metofficeweather.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

import static training.metofficeweather.web.WeatherInfo.request;

@Controller
@EnableAutoConfiguration
public class Website {

    @RequestMapping("/")
    ModelAndView home() {
        return new ModelAndView("index");
    }

    @RequestMapping("/weatherInfo")
    ModelAndView weatherInfo(@RequestParam("locName") String locName) {
        boolean isTyped = false;
        JsonParser parser = new JsonParser();
        HashMap<String, String> sites = new HashMap<>();
        String siteList = request("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=d9db0ba4-7eac-46da-a83c-fb074bb8015d");
        JsonObject objectSites = parser.parse(siteList).getAsJsonObject();
        JsonArray arraySites = objectSites.get("Locations").getAsJsonObject().get("Location").getAsJsonArray();
        for (JsonElement i : arraySites) { // SITE
            sites.put(i.getAsJsonObject().get("name").getAsString(), i.getAsJsonObject().get("id").getAsString());
        }
        //while (!isTyped) {
            String site = sites.get(locName);
            if (site == null) {
                System.out.println("NO");
                return new ModelAndView("error");
            }
        //}
        return new ModelAndView("info", "weatherInfo", new WeatherInfo(locName,site));
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Website.class, args);
    }

}