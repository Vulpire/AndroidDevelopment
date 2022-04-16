package edu.uncc.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.weather.databinding.FragmentCurrentWeatherBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

    /*
        InClass08
        Group19_InClass08
        Nicholas Wofford and Sierra Cubero
    */

public class CurrentWeatherFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;
    private final OkHttpClient client = new OkHttpClient();
    FragmentCurrentWeatherBinding binding;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance(DataService.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
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

    String body;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false);
        TextView temp, maxTemp, minTemp, des, humid, windSpeed, windDegree, cloudiness, cityName;
        Button checkForecast;
        ImageView weatherIcon;
        temp = binding.textViewTemp;
        maxTemp = binding.textViewMaxTemp;
        minTemp = binding.textViewMinTemp;
        des = binding.textViewDes;
        humid = binding.textViewHumidity;
        windSpeed = binding.textViewWindSpeed;
        windDegree = binding.textViewWindDegree;
        cloudiness = binding.textViewCloudiness;
        checkForecast = binding.buttonCheckForecast;
        weatherIcon = binding.imageViewWeather;
        cityName = binding.textViewCityName;

        cityName.setText(mCity.getCity() + "," + mCity.getCountry());


        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=" + mCity.getCity() + "") //api key taken out
                .build();

        ArrayList<Weather> weathers = new ArrayList<>();
        String temperature;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    //ResponseBody responseBody = response.body();
                    //body = responseBody.string();

                    try{
                        JSONObject json = new JSONObject(response.body().string());
                        JSONArray weatherArray = json.getJSONArray("weather");

                        for(int i =0; i <weatherArray.length(); i++){
                            JSONObject weatherArrayObj = weatherArray.getJSONObject(i);
                            JSONObject main = json.getJSONObject("main");

                            JSONObject wind = json.getJSONObject("wind");
                            JSONObject clouds = json.getJSONObject("clouds");

                            Weather weather = new Weather();
                            weather.temp = main.getString("temp");
                            weather.minTemp = main.getString("temp_min");
                            weather.maxTemp = main.getString("temp_max");
                            weather.humid = main.getString("humidity");
                            weather.des = weatherArrayObj.getString("description");
                            weather.icon = weatherArrayObj.getString("icon");
                            weather.windSpeed = wind.getString("speed");
                            weather.degree = wind.getString("deg");
                            weather.cloudiness = clouds.getString("all");
                            weathers.add(weather);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    temp.setText(weather.temp + " F");
                                    maxTemp.setText(weather.maxTemp + " F");
                                    minTemp.setText(weather.minTemp + " F");
                                    des.setText(weather.des);
                                    humid.setText(weather.humid + "%");
                                    windSpeed.setText(weather.windSpeed + " miles/hr");
                                    windDegree.setText(weather.degree + " degrees");
                                    cloudiness.setText(weather.cloudiness + "%");
                                    Picasso.get().load("https://openweathermap.org/img/wn/" + weather.icon + "@2x.png").resize(50,50).into(weatherIcon);

                                }
                            });
                        }
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        checkForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoForecast(mCity);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Current Weather");
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CurrentWeatherFragment.CurrentWeatherListener) context;
    }

    CurrentWeatherListener mListener;

    interface CurrentWeatherListener{
        void gotoForecast(DataService.City city);
    }
}