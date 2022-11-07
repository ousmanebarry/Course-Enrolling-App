package com.example.coursebookingapp.classes;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    protected String accountType;
    protected String email;
    protected String name;
    protected boolean isInstructor;


    public User() {
        this.accountType = this.getClass().getSimpleName();
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
        this.accountType = this.getClass().getSimpleName();
        isInstructor = accountType.equals("Instructor");
    }


    public String getAccountType(){
        return this.accountType;
    }

    public String getEmail(){return this.email;}

    public HashMap<String,Object> getMap(){
        HashMap<String,Object> out = new HashMap<String,Object>();
        out.put("accountType", accountType);
        out.put("name", name);
        out.put("email", email);
        out.put("isInstructor", isInstructor);
        return out;
    }
}