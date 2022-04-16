package com.example.inclass03;

import java.io.Serializable;

public class User implements Serializable {

    /*
        Assignment: In class #3
        File Name: Wofford_InClass03
        Name: Nicholas Wofford
     */

    String name;
    String email;
    String dept;
    int id;

    public User(String name, String email, String dept, int id){
        this.name = name;
        this.email = email;
        this.dept = dept;
        this.id = id;
    }

}
