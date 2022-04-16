package com.example.inclass03;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RegistrationActivity extends AppCompatActivity {

    /*
        Assignment: In class #3
        File Name: Wofford_InClass03
        Name: Nicholas Wofford
    */


    final static public String USER_KEY = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        TextView dept = findViewById(R.id.textViewDept);

        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result != null && result.getResultCode() == RESULT_OK){
                    if(result.getData() != null && result.getData().getStringExtra(DepartmentActivity.KEY_NAME) != null){
                        dept.setText(result.getData().getStringExtra(DepartmentActivity.KEY_NAME));
                    }
                }
            }
        });

        //dept button
        findViewById(R.id.buttonDept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, DepartmentActivity.class);
                startForResult.launch(intent);
            }
        });

        //submit button
        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = findViewById(R.id.editTextName);
                EditText email = findViewById(R.id.editTextEmail);
                EditText id = findViewById(R.id.editTextId);
                TextView dept = findViewById(R.id.textViewDept);

                String nameString = name.getText().toString();
                String emailString = email.getText().toString();
                String deptString = dept.getText().toString();
                int idString = 0;
                if(id.length() != 0 ){
                    idString = Integer.parseInt(id.getText().toString());
                }

                if(name.length() != 0 && email.length() != 0 && deptString.length() != 0 && id.length() != 0){
                    Intent intent = new Intent(RegistrationActivity.this, ProfileActivity.class);
                    intent.putExtra(USER_KEY, new User(nameString,emailString,deptString,idString));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.toast, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}