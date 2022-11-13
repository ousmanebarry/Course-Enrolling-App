package com.example.coursebookingapp.classes;

import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalTime;

public class Course {
    private String name;
    private String courseCode;
    private String desc;
    private LocalDate day;
    private LocalTime hours;
    private int capacity;
    private boolean hasInstructor;
    private String docID;


    public Course(String name, String courseCode){
        this.setName(name);
        this.setCode(courseCode);
        this.hasInstructor = false;
    }

    public Course(String name, String courseCode, String desc, LocalDate day, LocalTime hours, int capacity) {
        this.setName(name);
        this.setCode(courseCode);
        this.setDesc(desc);
        this.setDay(day);
        this.setHours(hours);
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
    public LocalDate getDay() { return this.day; }
    public LocalTime getHours() { return this.hours; }
    public int getCapacity() { return this.capacity; }
    public boolean getHasInstructor() { return this.hasInstructor; }


    public void setName(String name) {
        this.name = name;
    }
    public void setCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public void setDesc(String desc) { this.desc = desc; }
    public void setDay(LocalDate day) { this.day = day; }
    public void setHours(LocalTime hours) { this.hours = hours; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setHasInstructor(boolean hasInstructor) { this.hasInstructor = hasInstructor; }

    public HashMap<String,Object> getMap(){
        HashMap<String,Object> out = new HashMap<String,Object>();
        out.put("courseName", name);
        out.put("courseCode", courseCode);
        return out;
    }

    public String getDocID(){
        return getDocID();
    }

    public void setDocID(String docID){
        this.docID = docID;
    }
}