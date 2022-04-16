package edu.uncc.weather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

    /*
        InClass08
        Group19_InClass08
        Nicholas Wofford and Sierra Cubero
    */

public class ForecastsAdapter extends ArrayAdapter<Forecasts> {
    public ForecastsAdapter(@NonNull Context context, int resource, @NonNull List<Forecasts> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.forecast_view, parent, false);
        }
        Forecasts forecast = getItem(position);
        TextView temp, minTemp,maxTemp, date, humidity, description;
        ImageView icon;
        temp = convertView.findViewById(R.id.textViewForecastTemp);
        minTemp = convertView.findViewById(R.id.textViewForecastMin);
        maxTemp = convertView.findViewById(R.id.textViewForecastMax);
        date = convertView.findViewById(R.id.textViewDate);
        humidity = convertView.findViewById(R.id.textViewForecastHumidity);
        description = convertView.findViewById(R.id.textViewForecastDes);

        icon = convertView.findViewById(R.id.imageViewForecastIcon);

        temp.setText(forecast.temp + "F");
        minTemp.setText(forecast.minTemp + "F");
        maxTemp.setText(forecast.maxTemp + "F");
        date.setText(forecast.date);
        humidity.setText("Humidity: "+forecast.humidity + "%");
        description.setText(forecast.description);

        Picasso.get().load("https://openweathermap.org/img/wn/" + forecast.imageUrl + "@2x.png").resize(50,50).into(icon);
        return convertView;
    }
}
