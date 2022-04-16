package com.example.wofford_hw04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

    /*
        Homework 04
        Wofford_HW04
        Nicholas Wofford
    */

public class UserAdapter extends ArrayAdapter<DataServices.User> {
    public UserAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_view, parent, false);
        }


        DataServices.User user = getItem(position);
        TextView name = convertView.findViewById(R.id.textViewName);
        TextView state = convertView.findViewById(R.id.textViewState);
        TextView age = convertView.findViewById(R.id.textViewAge);
        TextView group = convertView.findViewById(R.id.textViewGroup);
        ImageView pfp = convertView.findViewById(R.id.imageViewPFP);

        name.setText(user.name);
        state.setText(user.state);
        String ageString = user.age + " Years Old";
        age.setText(ageString);
        group.setText(user.group);

        if(user.gender == "Male"){
            pfp.setImageResource(R.drawable.avatar_male);
        } else {
            pfp.setImageResource(R.drawable.avatar_female);
        }

        return convertView;
    }
}
