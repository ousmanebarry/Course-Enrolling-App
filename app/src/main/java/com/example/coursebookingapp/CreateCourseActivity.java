package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursebookingapp.classes.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.*;

public class CreateCourseActivity extends AppCompatActivity {
    FirebaseFirestore fStore;
    EditText courseNameField,courseCodeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        fStore = FirebaseFirestore.getInstance();

        Button createBtn = findViewById(R.id.courseCreateSubmitBtn);

        courseNameField = findViewById(R.id.createCourseNameField);
        courseCodeField = findViewById(R.id.createCourseCodeField);

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
        fStore.collection("courses").add(c.getMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateCourseActivity.this, "Successfully added Course!",Toast.LENGTH_SHORT).show();
                        courseCodeField.setText("");
                        courseNameField.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateCourseActivity.this, "Could not add Course.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}