package com.example.hw03;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

    /*
        Homework 03
        Wofford_HW03
        Nicholas Wofford
    */

public class TaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private Task task;

    public TaskFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(Task task) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, task);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //delete button
        view.findViewById(R.id.buttonTaskDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.delete(task);
            }
        });

        //cancel button
        view.findViewById(R.id.buttonTaskCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancel();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = (Task)getArguments().getSerializable(ARG_PARAM1);
        }
    }



    TextView displayName;
    TextView displayDate;
    TextView displayPriority;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        displayName = view.findViewById(R.id.textViewDisplayName);
        displayDate = view.findViewById(R.id.textViewDisplayDate);
        displayPriority = view.findViewById(R.id.textViewPriorityDisplay);

        displayName.setText(task.name);
        displayDate.setText(task.date);
        displayPriority.setText(task.priority);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof TaskFragment.TaskFragmentListener){
            mListener = (TaskFragment.TaskFragmentListener) context;
        } else {
            throw new RuntimeException((context.toString() + "Must Implement Listener"));
        }
    }

    TaskFragment.TaskFragmentListener mListener;

    interface TaskFragmentListener{
        void cancel();
        void delete(Task task);
    }
}