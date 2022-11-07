package com.example.coursebookingapp.classes;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    protected String accountType;
    protected String email;
    protected String name;
    protected boolean isTeacher;


    public User() {
        this.accountType = this.getClass().getSimpleName();
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
        this.accountType = this.getClass().getSimpleName();

        isTeacher = accountType.equals("Instructor");
    }


    public String getAccountType(){
        return this.accountType;
    }

    public String getEmail(){return this.email;}

    public HashMap<String,Object> getMap(){
        HashMap<String,Object> out = new HashMap<String,Object>();
        out.put("AccountType", accountType);
        out.put("Name", name);
        out.put("Email", email);
        out.put("isTeacher", isTeacher);
        return out;
    }
}