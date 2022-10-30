package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.coursebookingapp.classes.Course;

public class CreateCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        Button createBtn = findViewById(R.id.courseCreateSubmitBtn);

        EditText courseNameField = findViewById(R.id.createCourseNameField);
        EditText courseCodeField = findViewById(R.id.createCourseCodeField);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(courseCodeField.getText().toString().isEmpty() || courseNameField.getText().toString().isEmpty()) return;
                Course newCourse = new Course(courseNameField.getText().toString(),courseCodeField.getText().toString());
                addCourse(newCourse);
            }
        });

    }

    void addCourse(Course c){

    }
}