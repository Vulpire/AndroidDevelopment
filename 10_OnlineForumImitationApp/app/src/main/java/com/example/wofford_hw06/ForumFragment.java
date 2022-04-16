package com.example.wofford_hw06;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
    Homework 06
    Wofford_HW06
    Nicholas Wofford
 */

public class ForumFragment extends Fragment implements  CommentAdapter.CommentListener{

    private static final String ARG_PARAM1 = "param1";
    private Forum forum;
    RecyclerView recyclerView;
    ArrayList<Comment> allComments = new ArrayList<>();
    ArrayList<Comment> comments = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CommentAdapter adapter;
    LinearLayoutManager layoutManager;


    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment newInstance(Forum param1) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forum = (Forum)getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        mAuth = FirebaseAuth.getInstance();
        TextView title, name, desc, commentCount;

        EditText comment;
        Button post;

        title = view.findViewById(R.id.textViewForumTitle);
        name = view.findViewById(R.id.textViewForumAuthor);
        desc = view.findViewById(R.id.textViewForumDesc);
        commentCount = view.findViewById(R.id.textViewForumCommentCount);
        comment = view.findViewById(R.id.editTextComment);
        post = view.findViewById(R.id.buttonPostComment);

        title.setText(forum.title);
        name.setText(forum.name);
        desc.setText(forum.description);


        recyclerView = view.findViewById(R.id.recyclerViewForum);
        adapter = new CommentAdapter(comments, this, mAuth);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        db.collection("comments")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        allComments.clear();
                        comments.clear();
                        for(QueryDocumentSnapshot document: value){
                            allComments.add(new Comment(

                                    document.getString("name"),
                                    document.getString("text"),
                                    document.getString("date"),
                                    document.getString("email"),
                                    document.getString("postID")
                            ));
                        }
                        for(int i = 0; i< allComments.size(); i++){
                            if(forum.ID.equals(allComments.get(i).postID)){
                                comments.add(allComments.get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        int commentNum = comments.size();
                        commentCount.setText(commentNum + " Comments");
                    }
                });



        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = comment.getText().toString();
                if(commentText.isEmpty()){
                    Toast.makeText(getContext(), "Please enter some text",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Date currentTime = Calendar.getInstance().getTime();
                    Map<String, Object> comment = new HashMap<>();
                    comment.put("name", mAuth.getCurrentUser().getDisplayName());
                    comment.put("text", commentText);
                    comment.put("date", currentTime.toString());
                    comment.put("email", mAuth.getCurrentUser().getEmail());
                    comment.put("postID", forum.ID);

                    db.collection("comments")
                            .add(comment);
                }
            }
        });


        return view;
    }

    @Override
    public void trash(Comment comment) {
       Task<QuerySnapshot> query = db.collection("comments")
                .whereEqualTo("date", comment.date).get()
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
}