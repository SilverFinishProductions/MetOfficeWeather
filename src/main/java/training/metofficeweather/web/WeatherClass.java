package training.metofficeweather.web;

import java.text.SimpleDateFormat;

import static training.metofficeweather.web.WeatherInfo.parseDate;

public class WeatherClass {

    private int temp;
    private int tempFeels;
    private String weatherType;
    private int windSpeed;
    private int windGust;
    private String windDir;
    private String visibility;
    private int uvIndex;
    private int humidity;
    private int precipitation;
    private String time = "##";

    public WeatherClass(int temp, int tempFeels, String weatherType, int windSpeed, int windGust, String windDir, String visibility, int uvIndex, int humidity, int precipitation) {
        this.temp = temp;
        this.tempFeels = tempFeels;
        this.weatherType = weatherType;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
        this.windDir = windDir;
        this.visibility = visibility;
        this.uvIndex = uvIndex;
        this.humidity = humidity;
        this.precipitation = precipitation;
    }

    public String getTemp() {
        return temp + "°C";
    }

    public String getTempFeels() {
        return tempFeels + "°C";
    }

    public String getWeatherType() {
        return weatherType;
    }

    public String getWindSpeed() {
        return windSpeed + " MPH";
    }

    public String getWindGust() {
        return windGust + " MPH";
    }

    public String getWindDir() {
        return windDir;
    }

    public String getVisibility() {
        return visibility;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public String getHumidity() {
        return humidity + "%";
    }

    public String getPrecipitation() {
        return precipitation + "%";
    }

    public String getTime() {
        return time.toUpperCase();
    }

    public void setTime(String time) {
        this.time = new SimpleDateFormat("h a").format(parseDate(time, "HH"));
    }

    public String getEventString() {
        return "Time: " + time + ":00" +
                " | Temp: " + getTemp() + "°C" + " (" + getTempFeels() + ")" +
                " | Weather: " + getWeatherType() +
                " | Wind: " + getWindSpeed() + " MPH" + " (" + getWindGust() + ")" + " " + getWindDir() +
                " | Visibility: " + getVisibility() +
                " | UV index: " + getUvIndex() +
                " | Humidity: " + getHumidity() + "%" +
                " | Precipitation: " + getPrecipitation() + "%";
    }

}
