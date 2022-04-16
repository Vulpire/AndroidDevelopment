package com.example.group19_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /*
    Homework 1
    Group19_HW1
    Sierra Cubero and Nicholas Wofford
    image for new icon taken from https://www.vecteezy.com/vector-art/1917054-calculator-math-with-magnifying-glass-and-money by Vecteezy
     */

    SeekBar seekBarCustom;
    TextView customPercentTracking;
    Double value = 0.00;
    TextView tipResult;
    TextView totalResult;
    TextView totalPerResult;
    int split = 1;
    int customPercentage = 40;
    Double total;
    Double tipValue;
    int checked_idTip;
    EditText editText;
    int checked_idSplit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.clearButton).setOnClickListener(this);

        RadioGroup splitByGroup = findViewById(R.id.splitByGroup);
        RadioGroup tipGroup = findViewById(R.id.tipChoiceGroup);
        checked_idSplit = splitByGroup.getCheckedRadioButtonId();
        checked_idTip = tipGroup.getCheckedRadioButtonId();
        tipResult = findViewById(R.id.TipResult);
        editText = findViewById(R.id.editTextBillTotal);
        totalResult = findViewById(R.id.totalResult);
        totalPerResult = findViewById(R.id.totalPerResult);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checked_idTip = tipGroup.getCheckedRadioButtonId();
                calculation();

            }

        });

        tipGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checked_idTip = tipGroup.getCheckedRadioButtonId();
                calculation();
            }
        });

        splitByGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checked_idSplit = splitByGroup.getCheckedRadioButtonId();

                if (checked_idSplit == R.id.one) {
                    split = 1;
                }
                else if (checked_idSplit == R.id.two) {
                    split = 2;
                }
                else if (checked_idSplit == R.id.three) {
                    split = 3;
                }
                else if (checked_idSplit == R.id.four) {
                 split = 4;
             }
                calculation();
            }
        });

        //making seek bar show it's sliding value

        seekBarCustom = findViewById(R.id.seekBar);
        customPercentTracking = findViewById(R.id.customPercentTracking);

        seekBarCustom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
               customPercentTracking.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                customPercentage = seekBar.getProgress();
                calculation();
            }
        });


    }

    public void calculation() {
        if (editText.getText().toString().matches("")) {
            value = 0.00;
        }
        else {
            value = Double.parseDouble(editText.getText().toString());
            Log.d("DEMO", String.valueOf(Double.parseDouble(editText.getText().toString())));
        }
        if (checked_idTip == R.id.Ten) {
            Log.d("Check", String.valueOf(split));
            tipValue = Math.round((value * 0.1) * 100.0) / 100.0;
            total = tipValue + value;
            Double totalPerPerson = (total / split);
            postValues(tipValue, total, totalPerPerson);
        }
        if (checked_idTip == R.id.Fifteen) {
            tipValue = Math.round((value * 0.15) * 100.0) / 100.0;
            total = tipValue + value;
            Double totalPerPerson = (total / split);
            postValues(tipValue, total, totalPerPerson);
        }
        if (checked_idTip == R.id.Eighteen) {
            tipValue = Math.round((value * 0.18) * 100.0) / 100.0;
            total = tipValue + value;
            Double totalPerPerson = (total / split);
            postValues(tipValue, total, totalPerPerson);
        }
        if (checked_idTip == R.id.Custom) {
            tipValue = Math.round((value * (customPercentage/ 100.0)) * 100.0) / 100.0;
            total = tipValue + value;
            Double totalPerPerson = (total / split);
            postValues(tipValue, total, totalPerPerson);
        }

    }

    public void postValues(Double tipValue, Double billTotal, Double perPerson) {
        totalResult.setText(String.format("$%.2f", billTotal));
        tipResult.setText(String.format("$%.2f", tipValue));
        totalPerResult.setText(String.format("$%.2f", perPerson));
    }

    @Override
    public void onClick(View view) {
        RadioGroup radioGroup = findViewById(R.id.splitByGroup);
        RadioGroup radioGroupTwo = findViewById(R.id.tipChoiceGroup);
        EditText editText = findViewById(R.id.editTextBillTotal);
        RadioButton button1 = findViewById(R.id.Ten);
        RadioButton button2 = findViewById(R.id.one);
        TextView total = findViewById(R.id.totalResult);
        TextView totalPerPerson = findViewById(R.id.totalPerResult);


        if(view.getId() == R.id.clearButton) {
            radioGroup.clearCheck();
            radioGroupTwo.clearCheck();
            button1.setChecked(true);
            button2.setChecked(true);
            seekBarCustom.setProgress(40);
            editText.getText().clear();
            tipResult.setText("$0.00");
            total.setText("$0.00");
            totalPerPerson.setText("$0.00");

        }
    }
}