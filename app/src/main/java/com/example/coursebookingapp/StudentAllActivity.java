package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

<<<<<<< HEAD
public class StudentAllActivity extends AppCompatActivity {
=======
public class StudentAllActivity extends AppCompatActivity implements StudentAllRecyclerViewInterface {
>>>>>>> f1cedc217f65d01206e7467726faf2f503c28497

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_all);
    }
<<<<<<< HEAD
=======

    @Override
    public void onViewClick(int position) {

    }

    @Override
    public void onEnrollClick(int position) {

    }
>>>>>>> f1cedc217f65d01206e7467726faf2f503c28497
}