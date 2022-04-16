package com.example.hw03;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

    /*
        Homework 03
        Wofford_HW03
        Nicholas Wofford
    */

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LIST = "param1";

    // TODO: Rename and change types of parameters
    private ArrayList<Task> taskList;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(ArrayList<Task> param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIST, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskList = (ArrayList<Task>) getArguments().getSerializable(ARG_LIST);
        }
    }

    TextView taskCount;
    TextView displayName;
    TextView displayDate;
    TextView displayPriority;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        taskCount = view.findViewById(R.id.textViewHomeTasks);
        displayName = view.findViewById(R.id.textViewHomeName);
        displayDate = view.findViewById(R.id.textViewHomeDate);
        displayPriority = view.findViewById(R.id.textViewHomePriority);


        taskCount.setText("You Have " + taskList.size() + " tasks");
        if(taskList.size() > 0){
            displayName.setText(this.taskList.get(0).name);
            displayDate.setText(this.taskList.get(0).date);
            displayPriority.setText(this.taskList.get(0).priority);
        } else {
            displayName.setText("");
            displayDate.setText("");
            displayPriority.setText("");
        }

        return view;
    }

    public void updateAccount(ArrayList<Task> tasks){
        this.taskList = tasks;

        if (taskList.size() > 0){
            displayName.setText(this.taskList.get(0).name);
            displayDate.setText(this.taskList.get(0).date);
            displayPriority.setText(this.taskList.get(0).priority);
        } else {
            displayName.setText("");
            displayDate.setText("");
            displayPriority.setText("");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //View button
        view.findViewById(R.id.buttonHomeView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                //convert array to char sequence
                CharSequence[] strings = new CharSequence[taskList.size()];
                for(int i = 0; i < taskList.size(); i++){
                    strings[i] = (taskList.get(i).name);
                }

                builder.setTitle(R.string.select_task)
                        //cancel button
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        //Task List
                        .setItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //implement new fragment
                                mListener.returnTask(taskList.get(i));
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //create button
        view.findViewById(R.id.buttonHomeCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.create();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof updateHomeFragmentListener){
            mListener = (updateHomeFragmentListener) context;
        } else {
            throw new RuntimeException((context.toString() + "Must Implement Listener"));
        }
    }

    updateHomeFragmentListener mListener;

    interface updateHomeFragmentListener {
        void returnTask(Task task);
        void create();
    }
}