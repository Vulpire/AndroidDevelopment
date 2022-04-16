package com.example.group19_inclass10;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradeViewHolder>{
    List<Grade> gradeList;
    GradeAdapterListener mListener;


    public GradesAdapter(List<Grade> incomingGrades, GradeAdapterListener mListener){
        gradeList = incomingGrades;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_view, parent, false);

        GradeViewHolder gradeViewHolder = new GradeViewHolder(view, mListener);
        return gradeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        holder.letterGrade.setText(String.valueOf(gradeList.get(position).grade));
        holder.courseName.setText(gradeList.get(position).className);
        String creditH = String.valueOf(gradeList.get(position).creditHours) + " " + holder.itemView.getContext().getText(R.string.credit_hours);
        holder.creditHours.setText(creditH);
        String courseNum = holder.itemView.getContext().getText(R.string.itis) + " " + String.valueOf(gradeList.get(position).classAbv);
        holder.courseNumber.setText(courseNum);
        holder.grade = gradeList.get(position);
        holder.trash.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    public static class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView creditHours, courseName, letterGrade, courseNumber;
        ImageView trash;
        Grade grade;
        public GradeViewHolder(@NonNull View itemView, GradeAdapterListener mListener) {
            super(itemView);
            creditHours = itemView.findViewById(R.id.textViewGradeCreditHours);
            courseName = itemView.findViewById(R.id.textViewGradeCourseName);
            letterGrade = itemView.findViewById(R.id.textViewGradeLetter);
            courseNumber = itemView.findViewById(R.id.textViewGradeCourseNumber);
            trash = itemView.findViewById(R.id.imageViewTrash);
            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.trash(grade);
                }
            });
        }
    }

    public interface GradeAdapterListener{
        void trash(Grade trash);
    }
}
