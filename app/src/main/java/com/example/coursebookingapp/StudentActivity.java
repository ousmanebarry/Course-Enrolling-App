package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StudentActivity extends AppCompatActivity implements StudentRecyclerViewInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
    }

    @Override
    public void onViewClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {

    }
}