package com.example.wofford_midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

    /*
        Midterm
        Wofford_Midterm
        Nicholas Wofford
    */

public class GroupAdapter extends ArrayAdapter<Groups> {
    public GroupAdapter(@NonNull Context context, int resource, @NonNull List<Groups> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.group_view, parent, false);
        }
        Groups group = getItem(position);
        TextView monthYear = convertView.findViewById(R.id.textViewMonthYear);
        TextView total= convertView.findViewById(R.id.textViewTotal);
        monthYear.setText(group.GroupName());
        total.setText(Double.toString(group.value));

        return convertView;
    }
}
