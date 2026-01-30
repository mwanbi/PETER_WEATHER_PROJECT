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

    public String getName() {
        return name;
    }

    public MainData getMain() {
        return main;
    }

    public List<WeatherCondition> getWeather() {
        return weather;
    }
}

class MainData {
    @SerializedName("temp")
    private double temp;

    public double getTemp() {
        return temp;
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