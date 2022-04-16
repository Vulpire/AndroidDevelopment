package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    /*
        Assignment: In class #3
        File Name: Wofford_InClass03
        Name: Nicholas Wofford
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(getIntent() != null && getIntent().getExtras() != null){
            User user = (User)getIntent().getSerializableExtra(RegistrationActivity.USER_KEY);


            TextView nameDisplay = findViewById(R.id.textViewNameDisplay);
            nameDisplay.setText(user.name);

            TextView emailDisplay = findViewById(R.id.textViewEmailDisplay);
            emailDisplay.setText(user.email);

            TextView idDisplay = findViewById(R.id.textViewIdDisplay);
            idDisplay.setText(String.valueOf(user.id));

            TextView deptDisplay = findViewById(R.id.textViewDeptDisplay);
            deptDisplay.setText(user.dept);
        }
    }
}