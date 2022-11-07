package com.example.coursebookingapp.classes;

import java.util.ArrayList;

public class Student extends User {
    private ArrayList<String> courses;

    public Student() {
        super();
    }

    public Student(String email, String name) {
        super(email, name);
    }

    public Student(ArrayList<String> courses) {
        super();
    }
}