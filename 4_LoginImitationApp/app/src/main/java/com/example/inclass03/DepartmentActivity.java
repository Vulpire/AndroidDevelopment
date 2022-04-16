package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class DepartmentActivity extends AppCompatActivity {
    public static final String KEY_NAME = "Name";

/*
    Assignment: In class #3
    File Name: Wofford_InClass03
    Name: Nicholas Wofford
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        RadioGroup deptGroup = findViewById(R.id.deptGroup);


        findViewById(R.id.buttonDeptCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        findViewById(R.id.buttonDeptSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedId = deptGroup.getCheckedRadioButtonId();
                String temp = null;
                if(checkedId == R.id.radioButtonCS){
                    temp = "Computer Science";
                } else if(checkedId == R.id.radioButtonBI){
                    temp = "Bio Informatics";
                } else if(checkedId == R.id.radioButtonDS){
                    temp = "Data Science";
                } else if(checkedId == R.id.radioButtonSIS){
                    temp = "Software Info. Systems";
                }

                Intent intent = new Intent();
                intent.putExtra(KEY_NAME, temp);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }
}