package com.example.wofford_hw04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

    /*
        Homework 04
        Wofford_HW04
        Nicholas Wofford
    */

public class UsersFragment extends Fragment {
    public UsersFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.buttonFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.filter();
            }
        });

        //sort button
        view.findViewById(R.id.buttonSort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sort();
            }
        });
    }


    UserAdapter adapter;
    ListView listView;
    ArrayList<DataServices.User> users;
    ArrayList<DataServices.User> newUsers = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        getActivity().setTitle(R.string.users);
        listView = view.findViewById(R.id.userListView);
        users = DataServices.getAllUsers();
        newUsers = users;


        adapter = new UserAdapter(getContext(), R.layout.user_view, users);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof UsersFragment.UserFragmentListener){
            mListener = (UsersFragment.UserFragmentListener) context;
        } else {
            throw new RuntimeException();
        }
    }


    public void filterFragment(String state){
        if(state.toLowerCase().equals("all states")){
            adapter = new UserAdapter(getContext(), R.layout.user_view, users);
        } else {
            ArrayList<DataServices.User> newUsers = new ArrayList<>();
            for(int i = 0; i< users.size(); i++){
                if(users.get(i).state.equals(state)){
                    newUsers.add(users.get(i));
                }
            }
            adapter = new UserAdapter(getContext(), R.layout.user_view, newUsers);
        }
        listView.setAdapter(adapter);
    }


    public void sortFragment(String name, boolean direction){
        if(name.equals("Age")){
            if(direction){
                Collections.sort(newUsers, new Sort.SortAgeAsc());
            } else {
                Collections.sort(newUsers, new Sort.SortAgeDes());
            }
        } else if(name.equals("Name")){
            if(direction){
                Collections.sort(newUsers, new Sort.SortNameAsc());
            } else {
                Collections.sort(newUsers, new Sort.SortNameDes());
            }
        } else if(name.equals("State")){
            if(direction){
                Collections.sort(newUsers, new Sort.SortStateAsc());
            } else {
                Collections.sort(newUsers, new Sort.SortStateDes());
            }
        }
        adapter = new UserAdapter(getContext(), R.layout.user_view, newUsers);
        listView.setAdapter(adapter);
    }

    UsersFragment.UserFragmentListener mListener;

    interface UserFragmentListener{
            void filter();
            void sort();
    }
}