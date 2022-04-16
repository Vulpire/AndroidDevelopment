package edu.uncc.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.uncc.weather.databinding.FragmentCurrentWeatherBinding;
import edu.uncc.weather.databinding.FragmentWeatherForecastBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

    /*
        InClass08
        Group19_InClass08
        Nicholas Wofford and Sierra Cubero
    */

public class WeatherForecastFragment extends Fragment  {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;
    FragmentWeatherForecastBinding binding;
    ListView list;
    ForecastsAdapter forecastsAdapter;
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Forecasts> forecasts = new ArrayList<>();

    public WeatherForecastFragment() {
        // Required empty public constructor
    }


    public static WeatherForecastFragment newInstance(DataService.City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        list = binding.listViewForecast;
        forecastsAdapter = new ForecastsAdapter(getContext(), R.layout.forecast_view, forecasts);
        list.setAdapter(forecastsAdapter);
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/forecast?q=" + mCity.getCity() + "") //api key taken out
                .build();

        client.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        //get list from main json object
                        JSONArray listOfForecasts = json.getJSONArray("list");
                        //for each item in list (each three hour forecast)
                        for (int i = 0; i < listOfForecasts.length(); i++){
                            //make a new json object
                            JSONObject forecast = (JSONObject) listOfForecasts.get(i);
                            //new tempForecast obj to hold data
                            Forecasts tempForecast = new Forecasts();
                            //create new object for main data
                            JSONObject forecastMain = forecast.getJSONObject("main");
                            //create new Array for weather data
                            JSONArray forecastWeatherArray = forecast.getJSONArray("weather");
                            //get weather object from weather array
                            JSONObject forecastWeather = (JSONObject) forecastWeatherArray.get(0);
                            //set values
                            tempForecast.date = forecast.getString("dt_txt");
                            tempForecast.temp = forecastMain.getString("temp");
                            tempForecast.minTemp = forecastMain.getString("temp_min");
                            tempForecast.maxTemp = forecastMain.getString("temp_max");
                            tempForecast.humidity = forecastMain.getString("humidity");
                            tempForecast.description = forecastWeather.getString("description");
                            tempForecast.imageUrl = forecastWeather.getString("icon");
                            forecasts.add(tempForecast);
                        }


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                forecastsAdapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Weather Forecast");
    }
}