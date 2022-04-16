package com.example.wofford_midterm;

import java.io.Serializable;

    /*
        Midterm
        Wofford_Midterm
        Nicholas Wofford
    */

public class Expense implements Serializable {
    String name;
    double amount;
    String date;
    String category;
    Expense(String name, double amount, String date, String category){
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }
}