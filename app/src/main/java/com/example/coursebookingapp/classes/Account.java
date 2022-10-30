package com.example.coursebookingapp.classes;

public abstract class Account {
    protected String username;
    public Account() {

    }

    public Account(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
}
