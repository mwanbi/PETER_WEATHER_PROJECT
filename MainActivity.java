package com.example.weatherapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText etCity;
    private ImageButton btnToggleTheme;
    private TextView tvCityName, tvTemperature, tvCondition, tvTime, tvAppName, tvHighLow;
    private TextView tvAirQuality, tvHumidity, tvWind, tvPressure, tvVisibility, tvUVIndex, tvWeatherChannel;
    private CardView currentWeatherCard;
    private ImageView ivWeatherIcon;

    private WeatherApiService weatherApiService;
    private static final String API_KEY = "3584c7b7040b273f183a0b932d4aa3f6"; 
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private final Handler timeHandler = new Handler(Looper.getMainLooper());
    private Runnable timeRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRetrofit();
        setupClock();
        setupMovableTime();

        tvWeatherChannel.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.weather.com")));
        });

        etCity.setOnEditorActionListener((v, actionId, event) -> {
            getWeatherData(etCity.getText().toString().trim());
            return true;
        });

        // Default city
        getWeatherData("Kericho");
    }

    private void initializeViews() {
        etCity = findViewById(R.id.etCity);
        btnToggleTheme = findViewById(R.id.btnToggleTheme);
        tvCityName = findViewById(R.id.tvCityName);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvCondition = findViewById(R.id.tvCondition);
        tvTime = findViewById(R.id.tvTime);
        tvAppName = findViewById(R.id.tvAppName);
        tvHighLow = findViewById(R.id.tvHighLow);
        tvAirQuality = findViewById(R.id.tvAirQuality);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvWind = findViewById(R.id.tvWind);
        tvPressure = findViewById(R.id.tvPressure);
        tvVisibility = findViewById(R.id.tvVisibility);
        tvUVIndex = findViewById(R.id.tvUVIndex);
        tvWeatherChannel = findViewById(R.id.tvWeatherChannel);
        currentWeatherCard = findViewById(R.id.currentWeatherCard);
        ivWeatherIcon = findViewById(R.id.ivWeatherIcon);
    }

    private void setupRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApiService = retrofit.create(WeatherApiService.class);
    }

    private void getWeatherData(String city) {
        if (city.isEmpty()) return;
        
        weatherApiService.getWeather(city, API_KEY, "metric").enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse res = response.body();
                    displayWeatherData(res);
                } else {
                    Log.e(TAG, "Response error: " + response.code());
                    showError("Error: Unable to fetch weather data");
                }
            }
            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                showError("Network Error: " + t.getMessage());
            }
        });
    }

    private void displayWeatherData(WeatherResponse res) {
        tvCityName.setText(res.getName());
        
        // Use real temperature from API
        if (res.getMain() != null) {
            double temp = res.getMain().getTemp();
            tvTemperature.setText(String.format(Locale.US, "%.0f°", temp));
            
            double high = res.getMain().getTempMax();
            double low = res.getMain().getTempMin();
            tvHighLow.setText(String.format(Locale.US, "Day %.0f° • Night %.0f°", high, low));
            
            tvHumidity.setText(res.getMain().getHumidity() + "%");
            tvPressure.setText(res.getMain().getPressure() + " mb");
        }
        
        if (res.getWind() != null) {
            tvWind.setText(String.format(Locale.US, "%.1f km/h", res.getWind().getSpeed() * 3.6));
        }

        tvVisibility.setText(String.format(Locale.US, "%.0f km", (double)res.getVisibility() / 1000));
        
        if (res.getWeather() != null && !res.getWeather().isEmpty()) {
            WeatherCondition condition = res.getWeather().get(0);
            tvCondition.setText(condition.getDescription());
            
            String iconUrl = "https://openweathermap.org/img/wn/" + condition.getIcon() + "@2x.png";
            Picasso.get().load(iconUrl).into(ivWeatherIcon);
        }
        
        // Mocking AQI and UV as they require different API endpoints
        tvAirQuality.setText("Good");
        tvUVIndex.setText("3 of 10");
    }

    private void setupClock() {
        timeRunnable = new Runnable() {
            @Override
            public void run() {
                tvTime.setText(new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date()));
                timeHandler.postDelayed(this, 1000);
            }
        };
        timeHandler.post(timeRunnable);
    }

    private void setupMovableTime() {
        tvTime.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    view.setX(event.getRawX() + dX);
                    view.setY(event.getRawY() + dY);
                }
                return true;
            }
        });
    }

    private void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
