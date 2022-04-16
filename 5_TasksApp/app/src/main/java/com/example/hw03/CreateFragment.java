package com.example.hw03;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

    /*
        Homework 03
        Wofford_HW03
        Nicholas Wofford
    */

public class CreateFragment extends Fragment {


    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //set date button

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        view.findViewById(R.id.buttonCreateSetDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog,
                        setListener, year, month, day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);

            }
        });

        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                displayDate.setText(date);
            }
        };

        //submit button
        view.findViewById(R.id.buttonCreateSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = input.getText().toString();
                int checked = radioGroup.getCheckedRadioButtonId();
                String date = displayDate.getText().toString();
                String priority = null;
                if(checked == R.id.radioButtonHigh){
                    priority = "High";
                } else if(checked == R.id.radioButtonMedium){
                    priority = "Medium";
                } else if(checked == R.id.radioButtonLow){
                    priority = "Low";
                }

                if(name.isEmpty()){
                    Toast.makeText(getActivity(),R.string.name_toast, Toast.LENGTH_SHORT).show();
                } else if(date.isEmpty()){
                    Toast.makeText(getActivity(),R.string.date_toast, Toast.LENGTH_SHORT).show();
                } else if(priority.isEmpty()){
                    Toast.makeText(getActivity(),R.string.prior_toast, Toast.LENGTH_SHORT).show();
                } else {
                    mListener.submitCreate(new Task(name, date, priority));
                }
            }
        });

        //cancel button
        view.findViewById(R.id.buttonCreateCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelCreate();
            }
        });
    }


    TextView displayDate;
    EditText input;
    RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        displayDate = view.findViewById(R.id.textViewCreateDisplayDate);
        input = view.findViewById(R.id.editTextCreateTaskName);
        radioGroup = view.findViewById(R.id.radioGroup);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CreateFragment.createFragmentListener){
            mListener = (CreateFragment.createFragmentListener) context;
        } else {
            throw new RuntimeException((context.toString() + "Must Implement Listener"));
        }
    }

    CreateFragment.createFragmentListener mListener;

    interface createFragmentListener{
        void submitCreate(Task task);
        void cancelCreate();
    }
}