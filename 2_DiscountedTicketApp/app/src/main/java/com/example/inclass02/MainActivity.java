package com.example.inclass02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/*
    Assignment: In class #2
    File Name: Group19_InClass02
    Names: Nicholas Wofford, Sierra Cubero
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.calculate).setOnClickListener(this);
        findViewById(R.id.buttonClear).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        double base;
        String tag = "demo";
        EditText editText = findViewById(R.id.editTextPrice);
        Button submit = findViewById(R.id.calculate);
        TextView textView = findViewById(R.id.output);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        int checked_id = radioGroup.getCheckedRadioButtonId();

        //Checks if the input is a number
        try{
            base = Double.parseDouble(editText.getText().toString());
        }catch(Exception e){
            base = 0;
            Toast.makeText(this, R.string.toast, Toast.LENGTH_SHORT).show();
        }

        //checks if the number is negative
        if(base < 0){
            Toast.makeText(this, R.string.toast, Toast.LENGTH_SHORT).show();
        }
        //
        else if(view.getId() == R.id.calculate){
            if(checked_id == R.id.radioButtonFive){
                double newVal = Math.round((base - (base * .05)) * 100.0) /100.0;
                //textView.setText("Discount price: " + newVal);
                textView.setText(String.format("Discount Price: %.2f",newVal));
            } else if(checked_id == R.id.radioButtonTen){
                double newVal = Math.round((base - (base * .1)) * 100.0) /100.0;
                //textView.setText("Discount price: " + newVal);
                textView.setText(String.format("Discount Price: %.2f",newVal));
            } else if(checked_id == R.id.radioButtonFifteen){
                double newVal = Math.round((base - (base * .15)) * 100.0) /100.0;
                textView.setText(String.format("Discount Price: %.2f",newVal));
            } else if(checked_id == R.id.radioButtonTwenty){
                double newVal = Math.round((base - (base * .2)) * 100.0) /100.0;
                textView.setText(String.format("Discount Price: %.2f",newVal));
            } else if(checked_id == R.id.radioButtonFifty){
                double newVal = Math.round((base - (base * .5)) * 100.0) /100.0;
                textView.setText(String.format("Discount Price: %.2f",newVal));
            }
        } else if(view.getId() == R.id.buttonClear){
            radioGroup.clearCheck();
            textView.setText("Discount price: ");
            editText.getText().clear();
        }
    }
}