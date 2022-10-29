package com.example.coursebookingapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coursebookingapp.classes.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    Account currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //placeholder
        currentUser = new Admin("Name");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TextView welcomeText = findViewById(R.id.welcomeText);
        //set welcome text
        String welcomeMsg = "Welcome, " + currentUser.getUsername();
        if(currentUser instanceof Admin){
            welcomeMsg += "\nCurrent Role: Admin";
        }else if (currentUser instanceof Instructor){
            welcomeMsg += "\nCurrent Role: Instructor";
        }else{
            welcomeMsg += "\nCurrent Role: Student";
        }

        welcomeText.setText("Welcome, " + currentUser.getUsername());

        //set button visibility depending on role (only admin so far)
        if(currentUser instanceof Admin){
            findViewById(R.id.createCourseBtn).setEnabled(true);
            findViewById(R.id.deleteCourseBtn).setEnabled(true);
            findViewById(R.id.editCourseBtn).setEnabled(true);
            findViewById(R.id.deleteAccountBtn).setEnabled(true);
        }

        //button listeners
        Button createCourseBtn,editCourseBtn,deleteCourseBtn,deleteAccountBtn;

        createCourseBtn = findViewById(R.id.createCourseBtn);
        editCourseBtn = findViewById(R.id.editCourseBtn);
        deleteCourseBtn = findViewById(R.id.deleteCourseBtn);
        deleteAccountBtn = findViewById(R.id.deleteAccountBtn);

        createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this,CreateCourseActivity.class));
            }
        });

        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this,DeleteCourseActivity.class));
            }
        });


    }
}