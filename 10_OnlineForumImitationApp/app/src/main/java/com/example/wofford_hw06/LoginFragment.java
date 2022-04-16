package com.example.wofford_hw06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/*
    Homework 06
    Wofford_HW06
    Nicholas Wofford
 */

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();

        EditText email, password;
        TextView newAcct;
        Button login;
        email = view.findViewById(R.id.editTextLoginEmail);
        password = view.findViewById(R.id.editTextLoginPassword);
        newAcct = view.findViewById(R.id.textViewLoginCreateNewAcct);
        login = view.findViewById(R.id.buttonLoginLogin);

        //login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailS, passwordS;
                emailS = email.getText().toString();
                passwordS = password.getText().toString();
                if(emailS.isEmpty()){
                    Toast.makeText(getContext(),
                            "Please enter an email", Toast.LENGTH_SHORT).show();
                } else if(passwordS.isEmpty()){
                    Toast.makeText(getContext(),
                            "Please enter a password", Toast.LENGTH_SHORT).show();
                } else {
                    //Api Call
                    mAuth.signInWithEmailAndPassword(emailS, passwordS)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        mListener.loginSuccess();
                                    } else {
                                        Toast.makeText(getContext(),
                                                "Your account could not be found",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        //create new account
        newAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.loginNewAcct();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof loginListener){
            mListener = (loginListener) context;
        } else {
            throw new RuntimeException();
        }
    }

    loginListener mListener;

    interface loginListener{
        void loginSuccess();
        void loginNewAcct();
    }
}