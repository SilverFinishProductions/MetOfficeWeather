package training.metofficeweather.web;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static training.metofficeweather.web.WeatherInfo.parseDate;

public class WeatherDay {

    private Date day;
    private List<WeatherClass> weather;
    private int precipitationChance;
    private int tempHigh;
    private int tempLow;

    public WeatherDay(Date day, List<WeatherClass> weather) {
        this.day = day;
        Date dateCur = new Date();
        int count = 0;
        int precipChances = 0;
        int tempHighCur = -99;
        int tempLowCur = 99;
        for (WeatherClass i : weather) {
            // High Temp
            if (i.getTempInt() > tempHighCur) {
                tempHighCur = i.getTempInt();
            }
            // Low Temp
            if (i.getTempInt() < tempLowCur) {
                tempLowCur = i.getTempInt();
            }
            // Precip Chance
            precipChances += i.getPrecipitationInt();
            // Time Formatting
            i.setTime(Integer.toString((24 - (weather.size() * 3)) + (count * 3)));
            count++;
        }
        this.precipitationChance = Math.round(precipChances / weather.size());
        this.weather = weather;
        this.tempHigh = tempHighCur;
        this.tempLow = tempLowCur;
    }

    public String getDay() {
        return new SimpleDateFormat("EEEEEEEEE d MMMMMMMMM y").format(day);
    }

    public List<WeatherClass> getWeather() {
        return weather;
    }

    public int getPrecipitationChance() {
        return precipitationChance;
    }

    public int getTempHigh() {
        return tempHigh;
    }

    public int getTempLow() {
        return tempLow;
    }

}
