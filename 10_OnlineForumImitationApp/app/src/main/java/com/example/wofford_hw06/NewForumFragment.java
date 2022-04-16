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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
    Homework 06
    Wofford_HW06
    Nicholas Wofford
 */

public class NewForumFragment extends Fragment {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    public NewForumFragment() {
        // Required empty public constructor
    }

    private void saveData(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_forum, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        EditText title, des;
        Button submit;
        TextView cancel;

        title = view.findViewById(R.id.editTextNewForumTitle);
        des = view.findViewById(R.id.editTextNewForumDesc);
        submit = view.findViewById(R.id.buttonNewForumSubmit);
        cancel = view.findViewById(R.id.textViewNewForumCancel);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleS, descS;
                titleS = title.getText().toString();
                descS = des.getText().toString();

                if(titleS.isEmpty()){
                    Toast.makeText(getContext(),
                            "Please enter a title", Toast.LENGTH_SHORT).show();
                } else if(descS.isEmpty()){
                    Toast.makeText(getContext(),
                            "Please enter a description", Toast.LENGTH_SHORT).show();
                } else {
                    Date currentTime = Calendar.getInstance().getTime();
                    Map<String, Object> post = new HashMap<>();
                    post.put("name", mAuth.getCurrentUser().getDisplayName());
                    post.put("description", descS);
                    post.put("email", mAuth.getCurrentUser().getEmail());
                    post.put("id", "temp");
                    post.put("likes", 0);
                    post.put("title", titleS);
                    post.put("date", currentTime.toString());


                    db.collection("forums")
                            .add(post)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    mListener.newForumSuccess();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),
                                            "There was a problem making your post",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancel();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof newForumListener){
            mListener = (newForumListener) context;
        } else {
            throw new RuntimeException();
        }
    }

    newForumListener mListener;

    interface newForumListener{
        void cancel();
        void newForumSuccess();
    }
}