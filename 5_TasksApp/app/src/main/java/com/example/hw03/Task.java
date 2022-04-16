package com.example.hw03;

import android.util.Log;

import java.io.Serializable;

    /*
        Homework 03
        Wofford_HW03
        Nicholas Wofford
    */

public class Task implements Serializable, Comparable{
    String name;
    String date;
    String priority;

    Task(String name, String date, String prior){
        this.name = name;
        this.date = date;
        this.priority = prior;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return this.name + " " + this.date + " " + this.priority;
    }

    @Override
    public int compareTo(Object o) {
        Task other = (Task)o;
        return this.date.compareTo(other.date);
    }

    @Override
    public boolean equals(Object o){
        Task other = (Task)o;
        Log.d("Test", "This name: " + this.name + " other name: " + other.name);
        Log.d("Test", "This date: " + this.date + " other date: " + other.date);
        Log.d("Test", "This p: " + this.priority + " other p: " + other.priority);
        if(this.toString().equals(other.toString())){
            return true;
        } else {
            return false;
        }
    }

}
