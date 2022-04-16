package com.example.wofford_midterm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

    /*
        Midterm
        Wofford_Midterm
        Nicholas Wofford
    */

public class NewExpenseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewExpenseFragment() {
        // Required empty public constructor
    }

   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.add_new_expense));
        //if (getArguments() != null) {
        //    mParam1 = getArguments().getString(ARG_PARAM1);
        //    mParam2 = getArguments().getString(ARG_PARAM2);
        //}
    }

    int month;
    int day;
    int year;
    DatePickerDialog.OnDateSetListener setLisnener;
    TextView cat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_expense, container, false);

        Button cancel = view.findViewById(R.id.buttonCancel);
        Button submit = view.findViewById(R.id.buttonSubmit);
        Button selDate = view.findViewById(R.id.buttonSetDate);
        Button selCat = view.findViewById(R.id.buttonPickCat);
        EditText name = view.findViewById(R.id.editTextName);
        EditText cost = view.findViewById(R.id.editTextCost);
        TextView date = view.findViewById(R.id.textViewDateDisplay);
        cat = view.findViewById(R.id.textViewCatDisplay);
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        //select category
        selCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.newExpensePickCat();
            }
        });

        //select date button
        selDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog,
                        setLisnener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });

        //date picker
        setLisnener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String dateS = month + "/" + day + "/" + year;
                date.setText(dateS);
            }
        };

        //cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.newExpenseCancel();
            }
        });

        //submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameString = name.getText().toString();
                String costString = cost.getText().toString();
                String dateString = date.getText().toString();
                String catString = cat.getText().toString();
                Double costDouble;
                try {
                    costDouble = Double.parseDouble(costString);
                } catch (NumberFormatException nfe){
                    costDouble = 0.0;
                }

                if(nameString.length() == 0){
                    Toast.makeText(getContext(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
                } else if(costDouble == 0.0){
                    Toast.makeText(getContext(), "Please enter a valid cost", Toast.LENGTH_SHORT).show();
                } else if(dateString.length() == 0){
                    Toast.makeText(getContext(), "Please enter a date", Toast.LENGTH_SHORT).show();
                } else if(catString.length() == 0){
                    Toast.makeText(getContext(), "Please select a category", Toast.LENGTH_SHORT).show();
                } else {
                    Expense expense = new Expense(nameString, costDouble, dateString, catString);
                    mListener.newExpenseSubmit(expense);
                }
            }
        });

        return view;
    }

    public void updateFragment(String category){
        cat.setText(category);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof NewExpenseFragmentListener){
            mListener = (NewExpenseFragmentListener) context;
        } else {
            throw new RuntimeException();
        }
    }

    NewExpenseFragmentListener mListener;

    interface NewExpenseFragmentListener{
        void newExpensePickCat();
        void newExpenseCancel();
        void newExpenseSubmit(Expense expense);
    }
}