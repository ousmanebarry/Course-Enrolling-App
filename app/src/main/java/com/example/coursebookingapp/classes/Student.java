package com.example.coursebookingapp.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student extends User {
    private ArrayList<String> courses;

    public Student() {
        super();
    }

    public Student(String email, String name, ArrayList<String> courses) {
        this.email = email;
        this.name = name;
        this.accountType = this.getClass().getSimpleName();
        this.courses = courses;
        isInstructor = accountType.equals("Instructor");
    }

    public Student(ArrayList<String> courses) {
        super();
    }

    public List<String> getCourses(){
        return courses;
    }

    public HashMap<String,Object> getMap(){
        HashMap<String,Object> out = new HashMap<String,Object>();
        out.put("accountType", accountType);
        out.put("name", name);
        out.put("email", email);
        out.put("isInstructor", isInstructor);
        out.put("course", courses);
        return out;
    }
}