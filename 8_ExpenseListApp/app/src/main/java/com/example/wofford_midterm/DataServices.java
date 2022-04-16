package com.example.wofford_midterm;

import java.io.Serializable;
import java.util.ArrayList;

public class DataServices {
    private static final ArrayList<String> categories = new ArrayList<String>(){{
        add("Debt Payments");
        add("Education");
        add("Entertainment");
        add("Food");
        add("Housing");
        add("Insurance");
        add("Investments");
        add("Medical & Healthcare");
        add("Miscellaneous");
        add("Personal Spending");
        add("Shopping");
        add("Transportation");
        add("Travel");
        add("Utilities");
    }};

    public static ArrayList<String> getCategories(){
        return new ArrayList<>(categories);
    }
}
