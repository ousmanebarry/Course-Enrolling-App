package com.example.coursebookingapp.models;

public class AdminCourseModel {
    String courseCode;
    String courseName;


    public AdminCourseModel(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }


    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }


}
