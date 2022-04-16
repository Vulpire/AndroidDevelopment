package com.example.wofford_hw04;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

    /*
        Homework 04
        Wofford_HW04
        Nicholas Wofford
    */

public class SortRecyclerViewAdapter extends RecyclerView.Adapter<SortRecyclerViewAdapter.SortViewHolder> {
    ArrayList<String> types;
    SortRecycler mListener;
    public SortRecyclerViewAdapter(ArrayList<String> data, SortRecycler mListener){
        this.mListener = mListener;
        types = data;
    }

    @NonNull
    @Override
    public SortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_row_view,parent,false);
        SortViewHolder sortViewHolder = new SortViewHolder(view, mListener);
        return sortViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SortViewHolder holder, int position) {
        holder.stringType = types.get(position);
        holder.type.setText(holder.stringType);
    }

    @Override
    public int getItemCount() {
        return this.types.size();
    }

    public static class SortViewHolder extends RecyclerView.ViewHolder{
        TextView type;
        ImageView asc;
        ImageView des;
        SortRecycler mListener;
        String stringType;
        public SortViewHolder(@NonNull View itemView, SortRecycler mListener) {
            super(itemView);
            this.mListener = mListener;
            type = itemView.findViewById(R.id.textViewType);
            asc = itemView.findViewById(R.id.imageViewAscending);
            des = itemView.findViewById(R.id.imageViewDescending);
            asc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.clicked(stringType,true);
                }
            });

            des.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.clicked(stringType, false);
                }
            });
        }
    }

    interface SortRecycler{
        void clicked(String name, boolean direction);
    }
}
