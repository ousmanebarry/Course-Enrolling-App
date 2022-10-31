package com.example.coursebookingapp.classes;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    protected String accountType;
    protected String username;
    protected String name;
    protected boolean isTeacher;


    public User() {
        this.accountType = this.getClass().getName();
    }
    public User(String username, String name) {
        this.username = username;
        this.name = name;
        this.accountType = this.getClass().getName();
        if(accountType.equals("Instructor")){
            isTeacher = true;
        }else {
            isTeacher = false;
        }
    }
    public User(String username, String name, String accountType){
        this.username = username;
        this.name = name;
        this.accountType = accountType;

        if(accountType.equals("Instructor")){
            isTeacher = true;
        }else {
            isTeacher = false;
        }
    }

    public String getAccountType(){
        return this.accountType;
    }
    public String getUsername(){return username;}

    public HashMap<String,Object> getMap(){
        HashMap<String,Object> out = new HashMap<String,Object>();
        out.put("Email",username);
        out.put("Name",name);
        out.put("AccountType",accountType);
        out.put("isTeacher",isTeacher);
        return  out;
    }
}