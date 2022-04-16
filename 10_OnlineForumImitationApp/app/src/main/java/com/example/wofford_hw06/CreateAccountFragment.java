package com.example.wofford_hw06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/*
    Homework 06
    Wofford_HW06
    Nicholas Wofford
 */

public class CreateAccountFragment extends Fragment {
    FirebaseAuth mAuth;
    FirebaseUser user;
    public CreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        EditText name, email, password;
        TextView cancel;
        Button submit;

        name = view.findViewById(R.id.editTextCreateAcctName);
        email = view.findViewById(R.id.editTextCreateAcctEmail);
        password = view.findViewById(R.id.editTextCreateAcctPassword);
        cancel = view.findViewById(R.id.textViewCreateAcctCancel);
        submit = view.findViewById(R.id.buttonCreateAcctSubmit);

        //create new user
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameS, emailS, passwordS;
                nameS = name.getText().toString();
                emailS = email.getText().toString();
                passwordS = password.getText().toString();

                if(nameS.isEmpty()){
                    Toast.makeText(getContext(),
                            "Please enter a name", Toast.LENGTH_SHORT).show();
                } else if(emailS.isEmpty()){
                    Toast.makeText(getContext(),
                            "Please enter an email", Toast.LENGTH_SHORT).show();
                } else if(passwordS.isEmpty()){
                    Toast.makeText(getContext(),
                            "Please enter a password", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(emailS, passwordS)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(nameS)
                                                .build();
                                        user = mAuth.getCurrentUser();
                                        user.updateProfile(profileUpdate)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mListener.registerSuccess();
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(getContext(),
                                                "There was an error creating this user",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        //cancel
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.registerCancel();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof registerListener){
            mListener = (registerListener) context;
        } else {
            throw new RuntimeException();
        }
    }

    registerListener mListener;

    interface registerListener{
        void registerCancel();
        void registerSuccess();
    }
}