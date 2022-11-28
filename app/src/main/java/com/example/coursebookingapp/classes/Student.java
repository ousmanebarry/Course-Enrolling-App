package com.example.coursebookingapp.classes;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private ArrayList<String> courses;

    public Student() {
        super();
    }

    public Student(String email, String name, List<String> courses) {
        super(email, name);
    }

    public Student(ArrayList<String> courses) {
        super();
    }

    public List<String> getCourses(){
        return courses;
    }
}