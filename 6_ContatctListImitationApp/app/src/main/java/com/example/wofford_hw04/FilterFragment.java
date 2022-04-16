package com.example.wofford_hw04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

    /*
        Homework 04
        Wofford_HW04
        Nicholas Wofford
    */

public class FilterFragment extends Fragment {
    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        getActivity().setTitle(R.string.filter_by_state);
        ListView listView = view.findViewById(R.id.filterListView);
        ArrayAdapter<String> adapter;
        ArrayList<DataServices.User> users = DataServices.getAllUsers();
        ArrayList<String> states = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            states.add(users.get(i).state);
        }
        HashSet<String> hSet = new HashSet<String>(states);
        ArrayList<String> uniqueStates = new ArrayList<>(hSet);
        Collections.sort(uniqueStates);
        uniqueStates.add(0, "All States");
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1
                ,android.R.id.text1,uniqueStates);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mlistner.state(uniqueStates.get(i));
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof filterFragmentListener){
            mlistner = (filterFragmentListener) context;
        } else {
            throw new RuntimeException();
        }
    }

    filterFragmentListener mlistner;

    interface filterFragmentListener{
        void state(String stateName);
    }
}