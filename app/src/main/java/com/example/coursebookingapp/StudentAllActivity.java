package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StudentAllActivity extends AppCompatActivity implements StudentAllRecyclerViewInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_all);
    }

    @Override
    public void onViewClick(int position) {

    }

    @Override
    public void onEnrollClick(int position) {

    }
}