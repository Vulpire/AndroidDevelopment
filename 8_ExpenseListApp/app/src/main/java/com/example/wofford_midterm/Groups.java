package com.example.wofford_midterm;

    /*
        Midterm
        Wofford_Midterm
        Nicholas Wofford
    */

public class Groups{
    String year;
    String month;
    double value;
    int monthValue;
    int yearValue;
    Groups(String year, String month, double value){
        this.year = year;
        this.month = month;
        this.value = value;
        monthValue = Integer.parseInt(month);
        yearValue = Integer.parseInt(year);
    }

    public String GroupName(){
        String[] months = {"January","February", "March", "April", "May", "June", "July", "August",
        "September", "October", "November", "December"};
        int month = Integer.parseInt(this.month);
        return months[month-1] + " " + year;
    }
}
