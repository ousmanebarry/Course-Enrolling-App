package com.example.coursebookingapp.classes;

import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalTime;

public class Course {
    private String name;
    private String courseCode;
    private String desc;
    private String day;
    private String hours;
    private String capacity;
    private boolean hasInstructor;
    private String docID;


    public Course(String name, String courseCode){
        this.setName(name);
        this.setCode(courseCode);
        this.hasInstructor = false;
    }

    public Course(String name, String courseCode, String docID){
        this.setName(name);
        this.setCode(courseCode);
        this.setDocID(docID);
        this.hasInstructor = false;
    }

    public Course(String name, String courseCode, String docID, String capacity){
        this.setName(name);
        this.setCode(courseCode);
        this.setDocID(docID);
        this.setCapacity(capacity);
        this.hasInstructor = true;
    }


    public String getName() {
        return name;
    }
    public String getCode() {
        return courseCode;
    }
    public String getDesc() { return this.desc; }
    public String getDay() { return this.day; }
    public String getHours() { return this.hours; }
    public String getCapacity() { return this.capacity; }
    public boolean getHasInstructor() { return this.hasInstructor; }


    public void setName(String name) {
        this.name = name;
    }
    public void setCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public void setDesc(String desc) { this.desc = desc; }
    public void setDay(String day) { this.day = day; }
    public void setHours(String hours) { this.hours = hours; }
    public void setCapacity(String capacity) { this.capacity = capacity; }
    public void setHasInstructor(boolean hasInstructor) { this.hasInstructor = hasInstructor; }

    public HashMap<String,Object> getMap(){
        HashMap<String,Object> out = new HashMap<String,Object>();
        out.put("name", name);
        out.put("code", courseCode);
        out.put("hasInstructor", hasInstructor);
        return out;
    }

    public String getDocID(){
        return this.docID;
    }

    public void setDocID(String docID){
        this.docID = docID;
    }

}