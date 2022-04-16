package com.example.group19_inclass06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

    /*
        Assignment: In class #6
        File Name: Group#19_InClass06
        Name: Nicholas Wofford and Sierra Cubero
     */

public class MainActivity extends AppCompatActivity {
    int complexityInt; //plz
    Handler handler;
    ArrayAdapter<Double> adapter;
    ArrayList<Double> results;
    ListView listView;
    ExecutorService threadPool;
    int iterator;
    Double sum = 0.0;
    int setInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView complexity = findViewById(R.id.textViewComplexity);
        TextView average = findViewById(R.id.textViewAverage);
        TextView progress = findViewById(R.id.textViewProgress);
        SeekBar seekBar = findViewById(R.id.seekBar);
        Button generate = findViewById(R.id.buttonGen);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        iterator = 0;
        listView = findViewById(R.id.listView);
        results = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1
                ,android.R.id.text1,results);
        listView.setAdapter(adapter);


        threadPool = Executors.newFixedThreadPool(2);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                iterator++;
                Double result = message.getData().getDouble(HeavyWork.PROGRESS_KEY);
                results.add(result);
                sum += result;
                progress.setText(Integer.toString(iterator) + "/" + Integer.toString(setInt));
                progressBar.setProgress(iterator);
                Double averageVal = sum / results.size();
                average.setText(getString(R.string.average) + Double.toString(averageVal));
                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1
                        ,android.R.id.text1,results);
                listView.setAdapter(adapter);
                if(iterator == setInt){
                    generate.setClickable(true);
                }
                return false;
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                complexity.setText(i + " Times");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                    complexityInt = seekBar.getProgress();
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInt = complexityInt;
                iterator = 0;
                sum = 0.0;
                listView.setAdapter(null);
                results.clear();
                generate.setClickable(false);
                progressBar.setMax(setInt);
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
                average.setText(getString(R.string.average) + Double.toString(0.0));
                progress.setText(Integer.toString(iterator) + "/" + Integer.toString(setInt));
                threadPool.execute(new HeavyWork(setInt, handler));
            }
        });


    }
}

