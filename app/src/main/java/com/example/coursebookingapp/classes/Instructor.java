package com.example.coursebookingapp.classes;

import java.util.ArrayList;

public class Instructor extends User {
    private ArrayList<String> courses;

    public Instructor() {
        super();
    }

    public Instructor(String email, String name) {
        super(email, name);
    }

}