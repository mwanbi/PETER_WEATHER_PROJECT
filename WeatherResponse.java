package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {
    @SerializedName("name")
    private String name;

    @SerializedName("main")
    private MainData main;

    @SerializedName("weather")
    private List<WeatherCondition> weather;

    @SerializedName("wind")
    private WindData wind;

    @SerializedName("visibility")
    private int visibility;

    @SerializedName("coord")
    private Coord coord;

    public String getName() {
        return name;
    }

    public MainData getMain() {
        return main;
    }

    public List<WeatherCondition> getWeather() {
        return weather;
    }

    public WindData getWind() {
        return wind;
    }

    public int getVisibility() {
        return visibility;
    }

    public Coord getCoord() {
        return coord;
    }
}

class Coord {
    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}

class MainData {
    @SerializedName("temp")
    private double temp;

    @SerializedName("humidity")
    private int humidity;

    @SerializedName("pressure")
    private int pressure;

    @SerializedName("temp_min")
    private double tempMin;

    @SerializedName("temp_max")
    private double tempMax;

    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }
}

class WindData {
    @SerializedName("speed")
    private double speed;

    public double getSpeed() {
        return speed;
    }
}

class WeatherCondition {
    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String icon;

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
