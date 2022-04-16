package com.example.wofford_hw06;

import java.io.Serializable;
import java.util.ArrayList;

/*
    Homework 06
    Wofford_HW06
    Nicholas Wofford
 */

public class Forum implements Serializable {
    String title, name, description, date, email, ID;
    int likes;
    ArrayList<Likes> likesList;
    ArrayList<Comment> comments;
    public Forum(){

    }

    public Forum(String title, String name, String description,
                 String date, String email,String ID, int likes){
        this.title = title;
        this.name = name;
        this.description = description;
        this.date = date;
        this.email = email;
        this.ID = ID;
        this.likes = likes;
        likesList = new ArrayList<>();
        comments = new ArrayList<>();
    }
}
