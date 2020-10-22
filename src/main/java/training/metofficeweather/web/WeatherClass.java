package training.metofficeweather.web;

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

    public int getTemp() {
        return temp;
    }

    public int getTempFeels() {
        return tempFeels;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public int getWindGust() {
        return windGust;
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

    public int getHumidity() {
        return humidity;
    }

    public int getPrecipitation() {
        return precipitation;
    }

}
