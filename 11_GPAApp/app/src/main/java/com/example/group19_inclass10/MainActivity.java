package com.example.group19_inclass10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements
        GradesFragment.GradesFragmentListener, NewGradeFragment.NewCourseListener {
    private final String TAG = "Demo";
    private final String grades = "GRADES";
    private final String newGrade = "NEW";
    int root = R.id.rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(root, new GradesFragment(), grades)
                .commit();
    }

    @Override
    public void newGrade() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(root, new NewGradeFragment(), newGrade)
                .commit();
    }

    @Override
    public void newCourseSubmit() {
        getSupportFragmentManager().popBackStack();
        GradesFragment fragment = (GradesFragment) getSupportFragmentManager().findFragmentByTag(grades);
        fragment.updateView();
    }

    @Override
    public void newCourseCancel() {
        getSupportFragmentManager().popBackStack();
    }
}