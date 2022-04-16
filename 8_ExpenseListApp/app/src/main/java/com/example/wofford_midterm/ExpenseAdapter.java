package com.example.wofford_midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

    /*
        Homework 05
        Wofford_HW05
        Nicholas Wofford
    */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.SortViewHolder> {
    ExpenseRecycler mListener;
    ArrayList<Expense> expenses;
    public ExpenseAdapter(ArrayList<Expense> list, ExpenseRecycler mlistner){
        expenses = list;
        this.mListener = mlistner;
    }

    @NonNull
    @Override
    public SortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_view, parent
        , false);
        SortViewHolder sortViewHolder = new SortViewHolder(view, mListener);
        return sortViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SortViewHolder holder, int position) {
        holder.expense = expenses.get(position);
        holder.name.setText(holder.expense.name);
        holder.date.setText(holder.expense.date);
        holder.category.setText(holder.expense.category);
        holder.amount.setText("$" + Double.toString(holder.expense.amount));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class SortViewHolder extends RecyclerView.ViewHolder{
        ExpenseRecycler mListener;
        TextView name;
        TextView date;
        TextView category;
        TextView amount;
        ImageView del;
        Expense expense;
        public SortViewHolder(@NonNull View itemView, ExpenseRecycler mListener) {
            super(itemView);
            this.mListener = mListener;
            name = itemView.findViewById(R.id.textViewName);
            date = itemView.findViewById(R.id.textViewDate);
            amount = itemView.findViewById(R.id.textViewAmount);
            category = itemView.findViewById(R.id.textViewCategory);
            del = itemView.findViewById(R.id.imageViewTrash);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.clicked(expense);
                }
            });

        }
    }
    interface ExpenseRecycler{
        void clicked(Expense expense);
    }
}
