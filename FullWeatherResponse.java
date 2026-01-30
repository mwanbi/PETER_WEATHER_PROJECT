package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FullWeatherResponse {
    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("current")
    private CurrentWeather current;
    @SerializedName("hourly")
    private List<HourlyWeather> hourly;
    @SerializedName("daily")
    private List<DailyWeather> daily;
    @SerializedName("alerts")
    private List<WeatherAlert> alerts;
    @SerializedName("minutely")
    private List<MinutelyWeather> minutely;

    // Getters
    public CurrentWeather getCurrent() { return current; }
    public List<HourlyWeather> getHourly() { return hourly; }
    public List<DailyWeather> getDaily() { return daily; }
    public List<WeatherAlert> getAlerts() { return alerts; }
    public List<MinutelyWeather> getMinutely() { return minutely; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
}

class CurrentWeather {
    @SerializedName("temp")
    private double temp;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("wind_speed")
    private double windSpeed;
    @SerializedName("weather")
    private List<WeatherCondition> weather;

    public double getTemp() { return temp; }
    public int getHumidity() { return humidity; }
    public double getWindSpeed() { return windSpeed; }
    public List<WeatherCondition> getWeather() { return weather; }
}

class HourlyWeather {
    @SerializedName("dt")
    private long dt;
    @SerializedName("temp")
    private double temp;
    @SerializedName("pop")
    private double pop; // Probability of precipitation
    @SerializedName("weather")
    private List<WeatherCondition> weather;

    public long getDt() { return dt; }
    public double getTemp() { return temp; }
    public double getPop() { return pop; }
    public List<WeatherCondition> getWeather() { return weather; }
}

class DailyWeather {
    @SerializedName("dt")
    private long dt;
    @SerializedName("temp")
    private DailyTemp temp;
    @SerializedName("weather")
    private List<WeatherCondition> weather;

    public long getDt() { return dt; }
    public DailyTemp getTemp() { return temp; }
    public List<WeatherCondition> getWeather() { return weather; }
}

class DailyTemp {
    @SerializedName("min")
    private double min;
    @SerializedName("max")
    private double max;
    public double getMin() { return min; }
    public double getMax() { return max; }
}

class WeatherAlert {
    @SerializedName("event")
    private String event;
    @SerializedName("description")
    private String description;
    public String getEvent() { return event; }
    public String getDescription() { return description; }
}

class MinutelyWeather {
    @SerializedName("dt")
    private long dt;
    @SerializedName("precipitation")
    private double precipitation;
    public long getDt() { return dt; }
    public double getPrecipitation() { return precipitation; }
}

class AirQualityResponse {
    @SerializedName("list")
    private List<AirQualityData> list;
    public List<AirQualityData> getList() { return list; }
}

class AirQualityData {
    @SerializedName("main")
    private AqiMain main;
    public AqiMain getMain() { return main; }
}

class AqiMain {
    @SerializedName("aqi")
    private int aqi;
    public int getAqi() { return aqi; }
}
