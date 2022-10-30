package com.example.coursebookingapp.classes;

import java.util.ArrayList;

public class User {
    protected String accountType;
    protected String username;

    public User() {
        this.accountType = this.getClass().getName();
    }
    public User(String username) {
        this.username = username;
        this.accountType = this.getClass().getName();
    }
    public User(String username,String accountType){
        this.username = username;
        this.accountType = accountType;
    }

    public String getAccountType(){
        return this.accountType;
    }
    public String getUsername(){return username;}
}