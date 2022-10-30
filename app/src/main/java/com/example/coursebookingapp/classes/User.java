package com.example.coursebookingapp.classes;

import java.util.ArrayList;

public class User {
    private String accountType;

    public User() {
        this.accountType = this.getClass().getName();
    }
    public User(String accountType) {

    }

    public String getAccountType(){
        return this.accountType;
    }
}