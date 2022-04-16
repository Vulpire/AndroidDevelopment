package com.example.wofford_midterm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

    /*
        Midterm
        Wofford_Midterm
        Nicholas Wofford
    */

public class ExpenseFragment extends Fragment implements ExpenseAdapter.ExpenseRecycler{
    private static final String ARG_PARAM1 = "param1";
    private ArrayList<Expense> expensesList;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ExpenseAdapter adapter;
    TextView noRec;
    TextView totalExp;

    public ExpenseFragment() {
        // Required empty public constructor
    }

    public static ExpenseFragment newInstance(ArrayList<Expense> list) {
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.expenses));
        if (getArguments() != null) {
            expensesList = (ArrayList)getArguments().getSerializable(ARG_PARAM1);
        }
    }

    public void updateExpenseFragment(ArrayList<Expense> updatedList){
        expensesList = updatedList;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ExpenseAdapter(expensesList, this);
        recyclerView.setAdapter(adapter);

        updateText();

    }

    public void updateText(){
        if(this.expensesList.size() == 0){
            noRec.setText(getString(R.string.zeroInt));
            totalExp.setText(getString(R.string.zeroDouble));
        } else {
            int noRecNum = this.expensesList.size();
            double totalExpNum = 0.0;
            for(int i = 0; i<this.expensesList.size();i++){
                totalExpNum += this.expensesList.get(i).amount;
            }

            double total_expense = Math.round(totalExpNum * 100.0) / 100.0;
            noRec.setText(Integer.toString(noRecNum));
            totalExp.setText(getString(R.string.dollar) +Double.toString(total_expense));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        noRec = view.findViewById(R.id.textViewNoRecNum);
        totalExp = view.findViewById(R.id.textViewTotalExpenseNum);

        updateText();

        Button newExpense;
        Button summary;
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ExpenseAdapter(expensesList, this);
        recyclerView.setAdapter(adapter);

        newExpense = view.findViewById(R.id.buttonAddExpense);
        newExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.addNew();
            }
        });

        summary = view.findViewById(R.id.buttonExpSummary);
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.summary();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ExpenseFragmentListener){
            mListener = (ExpenseFragmentListener) context;
        } else {
            throw new RuntimeException();
        }
    }

    ExpenseFragmentListener mListener;

    @Override
    public void clicked(Expense expense) {
        mListener.delete(expense);
    }

    interface ExpenseFragmentListener{
        void delete(Expense expense);
        void addNew();
        void summary();
    }
}