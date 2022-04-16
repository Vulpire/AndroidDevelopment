package edu.uncc.weather;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

    /*
        InClass08
        Group19_InClass08
        Nicholas Wofford and Sierra Cubero
    */

public class Forecasts {
    String date, temp, maxTemp, minTemp, humidity, description, imageUrl;
    Forecasts(){

    }

    public String toString(){
        return this.date + "\n" + "Max Temp: " + this.maxTemp;
    }
}
