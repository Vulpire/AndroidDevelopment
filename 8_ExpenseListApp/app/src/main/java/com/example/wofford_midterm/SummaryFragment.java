package com.example.wofford_midterm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

    /*
        Midterm
        Wofford_Midterm
        Nicholas Wofford
    */


public class SummaryFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private ArrayList<Expense> expenseArrayList;

    public SummaryFragment() {
        // Required empty public constructor
    }

    public static SummaryFragment newInstance(ArrayList<Expense> param1) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.exp_summary));
        if (getArguments() != null) {
            expenseArrayList = (ArrayList) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        ListView summary = view.findViewById(R.id.listViewSummary);
        String cur_year;
        String cur_month;
        if(expenseArrayList.size() > 0){
            ArrayList<Groups> values = new ArrayList<>();
            String[] splitFirst = expenseArrayList.get(0).date.split("/");
            values.add(new Groups(splitFirst[2],splitFirst[0], expenseArrayList.get(0).amount));

            for(int i = 1; i< this.expenseArrayList.size(); i++){
                Log.d("Demo", "i: " + i);
                for(int j = 0; j<values.size(); j++){
                    Log.d("Demo", "j: " + j);
                    String[] split = expenseArrayList.get(i).date.split("/");
                     if(split[2].equals(values.get(j).year) && split[0].equals(values.get(j).month)){
                        values.get(j).value += expenseArrayList.get(i).amount;
                        break;
                    } else if(j == values.size() - 1){
                         Log.d("Demo", "adding: ");
                         values.add(new Groups(split[2],split[0], expenseArrayList.get(i).amount));
                         break;
                     }
                }
            }

            Collections.sort(values, new Sort.SortMonthYear());

            GroupAdapter adapter = new GroupAdapter(getContext(),R.layout.group_view, values);
            summary.setAdapter(adapter);
        }

        return view;
    }

}