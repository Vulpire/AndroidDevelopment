package com.example.wofford_hw04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

    /*
        Homework 04
        Wofford_HW04
        Nicholas Wofford
    */

public class MainActivity extends AppCompatActivity implements  UsersFragment.UserFragmentListener,
        FilterFragment.filterFragmentListener, SortFragment.sortFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainLayout, new UsersFragment(), "USER")
                .commit();
    }

    @Override
    public void filter() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainLayout, new FilterFragment(), "FILTER")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sort() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainLayout, new SortFragment(), "SORT")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void state(String stateName) {
        getSupportFragmentManager().popBackStack();
        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("USER");
        fragment.filterFragment(stateName);
    }

    @Override
    public void sort(String type, boolean direction) {
        getSupportFragmentManager().popBackStack();
        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("USER");
        fragment.sortFragment(type, direction);

    }
}