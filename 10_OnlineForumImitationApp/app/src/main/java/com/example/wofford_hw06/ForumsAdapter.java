package com.example.wofford_hw06;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/*
    Homework 06
    Wofford_HW06
    Nicholas Wofford
 */

public class ForumsAdapter extends RecyclerView.Adapter<ForumsAdapter.ForumViewHolder> {
    FirebaseAuth mAuth;
    ArrayList<Forum> forums;
    ForumRecycler mListener;
    public ForumsAdapter(ArrayList<Forum> list, ForumRecycler mListener, FirebaseAuth mAuth){
        this.forums = list;
        this.mListener = mListener;
        this.mAuth = mAuth;
    }


    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forumview, parent, false);
        ForumViewHolder forumViewHolder = new ForumViewHolder(view, mListener);
        return forumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        holder.forum = forums.get(position);
        holder.title.setText(holder.forum.title);
        holder.author.setText(holder.forum.name);
        holder.text.setText(holder.forum.description);
        holder.likes.setText(Integer.toString(holder.forum.likes));
        holder.date.setText(holder.forum.date);

        holder.liked = false;
        for(int i = 0; i < holder.forum.likesList.size(); i++){
            Log.d("Demo", "onBindViewHolder: " + mAuth.getCurrentUser().getEmail());
            Log.d("Demo", "onBindViewHolder: " + holder.forum.likesList.get(i).email);
            if(mAuth.getCurrentUser().getEmail().equals(holder.forum.likesList.get(i).email)){
                holder.liked = true;
                holder.like = holder.forum.likesList.get(i);
                break;
            }
        }


        if(holder.liked == true){
            holder.heart.setImageResource(R.drawable.like_favorite);
        } else {
            holder.heart.setImageResource(R.drawable.like_not_favorite);
        }

        if(holder.forum.email.equals(mAuth.getCurrentUser().getEmail())){
            holder.trash.setVisibility(View.VISIBLE);
        } else {
            holder.trash.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return forums.size();
    }

    public static class ForumViewHolder extends RecyclerView.ViewHolder{
        Forum forum;
        TextView title, author, text, likes, date;
        ImageView trash, heart;
        Boolean liked;
        Likes like;

        public ForumViewHolder(@NonNull View itemView, ForumRecycler mListener) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewForumListTitle);
            author = itemView.findViewById(R.id.textViewForumListAuthor);
            text = itemView.findViewById(R.id.textViewForumListText);
            likes = itemView.findViewById(R.id.textViewForumListLikes);
            date = itemView.findViewById(R.id.textViewForumListDate);
            trash = itemView.findViewById(R.id.imageViewForumListTrash);
            heart = itemView.findViewById(R.id.imageViewForumListHeart);

            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.clickedTrash(forum);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.clickedRow(forum);
                }
            });

            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.clickedHeart(forum, liked, like);
                }
            });
        }
    }

    interface ForumRecycler{
        void clickedTrash(Forum clicked);
        void clickedHeart(Forum clicked, Boolean liked, Likes like);
        void clickedRow(Forum clicked);
    }
}
