package com.example.group19_inclass10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class GradesFragment extends Fragment implements GradesAdapter.GradeAdapterListener {
    int totalHours = 0;
    int totalGPA = 0;
    AppDatabase db;
    TextView gpa, creditHours;
    RecyclerView recyclerView;
    GradesAdapter adapter;
    LinearLayoutManager layoutManager;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GradesFragment() {
        // Required empty public constructor
    }

    public static GradesFragment newInstance(String param1, String param2) {
        GradesFragment fragment = new GradesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Grades");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void updateView(){
        totalHours = 0;
        totalGPA = 0;

        List<Grade> gradeList = db.gradeDao().getAll();
        adapter = new GradesAdapter(gradeList, this::trash);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        for(int i = 0; i<gradeList.size(); i++){
            switch(gradeList.get(i).grade){

                case 'A':
                    totalGPA += 4 * gradeList.get(i).creditHours;
                    break;
                case 'B':
                    totalGPA += 3 * gradeList.get(i).creditHours;
                    break;
                case 'C':
                    totalGPA += 2 * gradeList.get(i).creditHours;
                    break;
                case 'D':
                    totalGPA += 1 * gradeList.get(i).creditHours;
                    break;
                case 'F':
                    totalGPA += 0 * gradeList.get(i).creditHours;
                    break;
            }
            totalHours += gradeList.get(i).creditHours;
        }
        double gpaCalc = (double)totalGPA / (double)totalHours;
        gpa.setText(getText(R.string.gpa) + " " + df.format(gpaCalc));
        creditHours.setText(getText(R.string.credit_hours) + " " + totalHours);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        gpa = view.findViewById(R.id.textViewGradesGPA);
        creditHours = view.findViewById(R.id.textViewGradesHours);
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "grade.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        recyclerView = view.findViewById(R.id.recyclerViewGrades);

        updateView();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mListener.newGrade();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof GradesFragmentListener){
            mListener = (GradesFragmentListener) context;
        } else {
            throw new RuntimeException();
        }
    }

    GradesFragmentListener mListener;

    @Override
    public void trash(Grade trash) {
        db.gradeDao().delete(trash);
    }

    interface GradesFragmentListener{
        void newGrade();
    }
}