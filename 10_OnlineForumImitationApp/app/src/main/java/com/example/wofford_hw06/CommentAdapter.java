package com.example.wofford_hw06;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;

/*
    Homework 06
    Wofford_HW06
    Nicholas Wofford
 */

public class CommentAdapter extends  RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    FirebaseAuth mAuth;
    ArrayList<Comment> comments;
    CommentListener mListener;

    public CommentAdapter(ArrayList<Comment> comments, CommentListener mListener, FirebaseAuth mAuth){
        this.mAuth = mAuth;
        this.comments = comments;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view, parent, false);
        CommentViewHolder commentViewHolder = new CommentViewHolder(view, mListener);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.comment = comments.get(position);
        holder.name.setText(holder.comment.name);
        holder.desc.setText(holder.comment.text);
        holder.date.setText(holder.comment.date);

        if(mAuth.getCurrentUser().getEmail().equals(holder.comment.email)){
            holder.trash.setVisibility(View.VISIBLE);
        } else {
            holder.trash.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView name, desc, date;
        ImageView trash;
        Comment comment;
        public CommentViewHolder(@NonNull View itemView, CommentListener mListener) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewCommentName);
            desc = itemView.findViewById(R.id.textViewCommentText);
            date = itemView.findViewById(R.id.textViewCommentDate);
            trash = itemView.findViewById(R.id.imageViewCommentTrash);

            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.trash(comment);
                }
            });
        }
    }

    interface CommentListener{
        void trash(Comment comment);
    }
}
