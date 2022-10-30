package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursebookingapp.classes.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class EditCourseActivity extends AppCompatActivity {
    Button findBtn, submitbtn, backBtn;
    EditText findField,courseNameField,courseCodeField;

    String courseUUID;

    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        fstore = FirebaseFirestore.getInstance();

        //assign components
        findBtn = findViewById(R.id.findCourseToEditBtn);
        submitbtn = findViewById(R.id.sumbitEditBtn);
        backBtn = findViewById(R.id.editActivityBackBtn);

        findField = findViewById(R.id.findCourseByCodeField);
        courseNameField = findViewById(R.id.editCourseNameField);
        courseCodeField = findViewById(R.id.editCourseCodeField);

        courseNameField.setEnabled(false);
        courseCodeField.setEnabled(false);
        submitbtn.setEnabled(false);

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //search for course
                fstore.collection("courses").
                        whereEqualTo("courseCode",findField.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(EditCourseActivity.this,"Found Course",Toast.LENGTH_SHORT).show();
                                    //get first result)
                                    courseUUID = task.getResult().getDocuments().get(0).getId();
                                    courseNameField.setEnabled(true);
                                    courseNameField.setText(task.getResult().getDocuments().get(0).get("courseName",String.class));

                                    courseCodeField.setEnabled(true);
                                    courseCodeField.setText(task.getResult().getDocuments().get(0).get("courseCode",String.class));
                                    submitbtn.setEnabled(true);
                                } else {
                                    Toast.makeText(EditCourseActivity.this,"Could not find course",Toast.LENGTH_SHORT).show();
                                    courseNameField.setEnabled(false);
                                    courseNameField.setText("");
                                    courseCodeField.setEnabled(false);
                                    courseCodeField.setText("");
                                    submitbtn.setEnabled(false);
                                }

                            }
                        });
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course c = new Course(courseNameField.getText().toString(),courseCodeField.getText().toString());
                fstore.collection("courses").document(courseUUID).set(c.getMap())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditCourseActivity.this,"Changes made.",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditCourseActivity.this,"Changes could not be made.",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}