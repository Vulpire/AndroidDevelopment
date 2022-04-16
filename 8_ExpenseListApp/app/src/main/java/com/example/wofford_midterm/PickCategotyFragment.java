package com.example.wofford_midterm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

    /*
        Midterm
        Wofford_Midterm
        Nicholas Wofford
    */

public class PickCategotyFragment extends Fragment {


    public PickCategotyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_categoty, container, false);
        ListView list = view.findViewById(R.id.listViewCategories);
        ArrayList categories = DataServices.getCategories();
        ListAdapter adapter;
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1
        ,android.R.id.text1, categories);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mlistener.category(categories.get(i).toString());
            }
        });


        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof PickCategoryFragmentListener){
            mlistener = (PickCategoryFragmentListener) context;
        } else {
            throw new RuntimeException();
        }

    }

    PickCategoryFragmentListener mlistener;

    interface PickCategoryFragmentListener{
        void category(String cat);
    }
}