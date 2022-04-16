package com.example.group19_inclass10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class NewGradeFragment extends Fragment {
    public NewGradeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_grade, container, false);
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "grade.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        EditText courseNumber, courseName, creditHours;
        RadioGroup grade;
        Button submit;
        TextView cancel;

        courseNumber = view.findViewById(R.id.editTextCourseNumber);
        courseName = view.findViewById(R.id.editTextCourseName);
        creditHours = view.findViewById(R.id.editTextCreditHours);
        grade = view.findViewById(R.id.radioGroupGrade);
        submit = view.findViewById(R.id.buttonNewSubmit);
        cancel = view.findViewById(R.id.textViewCancel);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cNum, cHours;
                String cName;
                char letter = 'Z';
                int id = grade.getCheckedRadioButtonId();
                cName = courseName.getText().toString();

                switch (id){
                    case R.id.radioButtonA:
                        letter = 'A';
                        break;
                    case R.id.radioButtonB:
                        letter = 'B';
                        break;
                    case R.id.radioButtonC:
                        letter = 'C';
                        break;
                    case R.id.radioButtonD:
                        letter = 'D';
                        break;
                    case R.id.radioButtonF:
                        letter = 'F';
                        break;
                }

                if(letter == 'Z'){
                    Toast.makeText(getContext(), "Please select a grade", Toast.LENGTH_SHORT).show();
                } else if(courseNumber.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please enter a course Number", Toast.LENGTH_SHORT).show();
                }else if(creditHours.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please enter a number of credit hours", Toast.LENGTH_SHORT).show();
                } else if(cName.isEmpty()){
                    Toast.makeText(getContext(), "Please enter a course name", Toast.LENGTH_SHORT).show();
                } else {
                    cNum = Integer.parseInt(courseNumber.getText().toString());
                    cHours = Integer.parseInt(creditHours.getText().toString());
                    Grade temp = new Grade(cNum, cName,letter,cHours);
                    db.gradeDao().insertAll(temp);
                    mListener.newCourseSubmit();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.newCourseCancel();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof NewCourseListener){
            mListener = (NewCourseListener) context;
        }else{
            throw new RuntimeException();
        }
    }

    NewCourseListener mListener;

    interface NewCourseListener{
        void newCourseSubmit();
        void newCourseCancel();
    }
}