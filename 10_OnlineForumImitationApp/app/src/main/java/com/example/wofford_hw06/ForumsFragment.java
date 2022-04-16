package com.example.wofford_hw06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
    Homework 06
    Wofford_HW06
    Nicholas Wofford
 */

public class ForumsFragment extends Fragment implements ForumsAdapter.ForumRecycler{
    RecyclerView recyclerView;
    ArrayList<Forum> forums = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ForumsAdapter adapter;
    LinearLayoutManager layoutManager;

    public ForumsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void updateRecycler(){
        adapter = new ForumsAdapter(forums, ForumsFragment.this, mAuth);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forums, container, false);
        mAuth = FirebaseAuth.getInstance();
        Button newForum, logout;
        newForum = view.findViewById(R.id.buttonForumsNewForum);
        logout = view.findViewById(R.id.buttonForumsLogout);
        recyclerView = view.findViewById(R.id.recyclerViewForums);
        adapter = new ForumsAdapter(forums, this, mAuth);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //get forums
        db.collection("forums")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        forums.clear();
                        for(QueryDocumentSnapshot document: value){
                            forums.add(new Forum(document.getString("title"),
                                    document.getString("name"),
                                    document.getString("description"),
                                    document.get("date").toString(),
                                    document.getString("email"),
                                    document.getId(),
                                    document.getLong("likes").intValue()));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        //get likes
        db.collection("likes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(int i = 0; i < forums.size(); i++){
                            for(QueryDocumentSnapshot document: value){
                                if(forums.get(i).ID.equals(document.get("postID"))){
                                    forums.get(i).likesList.add(new Likes(
                                            document.getString("email"),
                                            document.getString("postID"),
                                            document.getId()
                                    ));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        db.collection("likes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                    }
                });
        adapter.notifyDataSetChanged();

        //create new forum button
        newForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.newForum();
            }
        });

        //logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.logout();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof forumsListener){
            mListener = (forumsListener) context;
        } else {
            throw new RuntimeException();
        }
    }

    forumsListener mListener;

    @Override
    public void clickedTrash(Forum clicked) {
        db.collection("forums").document(clicked.ID)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateRecycler();
                    }
                });

        Task<QuerySnapshot> query = db.collection("likes")
                .whereEqualTo("postID", clicked.ID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                document.getReference().delete();
                            }
                        }
                    }
                });

    }

    @Override
    public void clickedHeart(Forum clicked, Boolean liked, Likes like) {
        if(liked){
            db.collection("forums").document(clicked.ID)
                    .update("likes", clicked.likes - 1)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            db.collection("likes").document(like.likeID)
                                    .delete();
                            updateRecycler();
                        }
                    });
        } else {
            db.collection("forums").document(clicked.ID)
                    .update("likes", clicked.likes + 1)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Map<String, Object> like = new HashMap<>();
                            like.put("email", mAuth.getCurrentUser().getEmail());
                            like.put("postID", clicked.ID);
                            db.collection("likes")
                                    .add(like);
                            updateRecycler();
                        }
                    });
        }

    }

    @Override
    public void clickedRow(Forum clicked) {
        mListener.selectForum(clicked);
    }

    interface forumsListener{
        void logout();
        void newForum();
        void selectForum(Forum clicked);
    }
}