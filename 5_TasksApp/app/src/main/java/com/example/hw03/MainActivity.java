package com.example.hw03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

    /*
        Homework 03
        Wofford_HW03
        Nicholas Wofford
    */

public class MainActivity extends AppCompatActivity implements
        HomeFragment.updateHomeFragmentListener, TaskFragment.TaskFragmentListener,
        CreateFragment.createFragmentListener{

    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tasks = new ArrayList<>();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, HomeFragment.newInstance(tasks), "HOME")
                .commit();
    }


    @Override
    public void returnTask(Task task) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, TaskFragment.newInstance(task), "TASK")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void create() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new CreateFragment(), "CREATE")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void delete(Task task) {
        for(int i  = 0; i < tasks.size(); i++){
            if(task.equals(tasks.get(i))){
                tasks.remove(i);
            }
        }
        getSupportFragmentManager().popBackStack();
        HomeFragment fragment = (HomeFragment)getSupportFragmentManager().findFragmentByTag("HOME");
        fragment.updateAccount(tasks);
    }

    @Override
    public void submitCreate(Task task) {
        tasks.add(task);
        Collections.sort(tasks);
        getSupportFragmentManager().popBackStack();
        HomeFragment fragment = (HomeFragment)getSupportFragmentManager().findFragmentByTag("HOME");
        fragment.updateAccount(tasks);
    }

    @Override
    public void cancelCreate() {
        getSupportFragmentManager().popBackStack();
    }
}