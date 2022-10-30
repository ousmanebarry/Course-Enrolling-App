package com.example.coursebookingapp.classes;

public class Course {
    private String name;
    private String courseCode;
    private String desc;
    private String day;
    private String hours;
    private int capacity;
    private boolean hasInstructor;


    public Course(String name, String courseCode){
        this.setName(name);
        this.setCourseCode(courseCode);
        this.hasInstructor = false;
    }

    public Course(String name, String courseCode, String desc, String day, String hours,
                  int capacity) {
        this.setName(name);
        this.setCourseCode(courseCode);
        this.setDesc(desc);
        this.setDay(day);
        this.setHours(hours);
        this.setCapacity(capacity);
        this.hasInstructor = true;
    }


    public String getName() {
        return name;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public String getDesc() { return this.desc; }
    public String getDay() { return this.day; }
    public String getHours() { return this.hours; }
    public int getCapacity() { return this.capacity; }
    public boolean getHasInstructor() { return this.hasInstructor; }


    public void setName(String name) {
        this.name = name;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public void setDesc(String desc) { this.desc = desc; }
    public void setDay(String day) { this.day = day; }
    public void setHours(String hours) { this.hours = hours; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setHasInstructor(boolean hasInstructor) { this.hasInstructor = hasInstructor; }

}