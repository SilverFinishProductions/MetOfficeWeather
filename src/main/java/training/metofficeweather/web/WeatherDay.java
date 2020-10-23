package training.metofficeweather.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherDay {

    private Date day;
    private List<WeatherClass> weather;

    public WeatherDay(Date day, List<WeatherClass> weather) {
        this.day = day;
        int count = 0;
        for (WeatherClass i : weather) {
            i.setTime(Integer.toString((24 - (weather.size() * 3)) + (count * 3)));
            count++;
        }
        this.weather = weather;
    }

    public String getDay() {
        return new SimpleDateFormat("EEEEEEEEE d MMMMMMMMM y").format(day);
    }

    public List<WeatherClass> getWeather() {
        return weather;
    }

}
