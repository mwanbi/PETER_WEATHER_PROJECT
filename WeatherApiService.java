package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

    // OpenWeatherMap One Call API 3.0 (Requires subscription, but fits the request)
    @GET("onecall")
    Call<FullWeatherResponse> getFullWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("exclude") String exclude,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

    // Air Quality API
    @GET("air_pollution")
    Call<AirQualityResponse> getAirQuality(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apiKey
    );
}
