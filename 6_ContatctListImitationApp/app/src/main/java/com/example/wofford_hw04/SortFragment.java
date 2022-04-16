package com.example.wofford_hw04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

    /*
        Homework 04
        Wofford_HW04
        Nicholas Wofford
    */

public class SortFragment extends Fragment implements SortRecyclerViewAdapter.SortRecycler {

    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SortRecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        getActivity().setTitle(R.string.sort);
        ArrayList<String> sortList = new ArrayList<>();
        sortList.add("Age");
        sortList.add("Name");
        sortList.add("State");

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SortRecyclerViewAdapter(sortList, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof sortFragmentListener){
            mlistner = (sortFragmentListener) context;
        } else {
            throw new RuntimeException();
        }
    }

    sortFragmentListener mlistner;


    @Override
    public void clicked(String name, boolean direction) {
        Log.d("Demo", "type: " + name + " Direction: " + direction);
        mlistner.sort(name, direction);
    }


    interface sortFragmentListener{
        void sort(String type, boolean direction);
    }
}