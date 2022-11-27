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

    public String toString() {
        return this.name + " "  + this.courseCode;
    }

    //convert hours to doubles 0 (inclusive) - 24 (exclusive)
    //minutes converted to fractions of an hour (:30 = .5)
    //result[0] is start, result[1] is end
    public double[] getHoursAsDoubles(){
        String startStr,endStr;
        startStr = hours.split("-")[0];
        endStr = hours.split("-")[1];
        double start,end;

        //get hours
        start = Double.parseDouble(startStr.split(":")[0]);
        end = Double.parseDouble(endStr.split(":")[0]);

        //add minutes
        start += (Double.parseDouble(startStr.split(":")[1]))/60.0;
        end += (Double.parseDouble(endStr.split(":")[1]))/60.0;

        double[] out = new double[2];
        out[0] = start;
        out[1] = end;
        return out;
    }

    public boolean checkIfIntersects(Course other){
        double[] times,otherTimes;
        times = getHoursAsDoubles();
        otherTimes = other.getHoursAsDoubles();

        //check if start or end time is within range
        if((otherTimes[0] < times[0] && times[0] < otherTimes[1]) || (otherTimes[0] < times[1] && times[1] < otherTimes[1])){
            return true;
        }

        //same of other class
        if((times[0] < otherTimes[0] && otherTimes[0] < times[1]) || (times[0] < otherTimes[1] && otherTimes[1] < times[1])){
            return  true;
        }
        return false;
    }

}