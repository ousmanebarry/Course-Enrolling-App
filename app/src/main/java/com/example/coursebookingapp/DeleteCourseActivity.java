package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DeleteCourseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();

        Button deleteBtn = findViewById(R.id.deleteCourseSubmitBtn);
        EditText courseCode = findViewById(R.id.deleteCourseCodeField);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fstore.collection("courses").
                        whereEqualTo("courseCode", courseCode.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(DeleteCourseActivity.this,"Found Course",Toast.LENGTH_SHORT).show();
                                    String courseId = task.getResult().getDocuments().get(0).getId();
                                    fstore.collection("courses").document(courseId)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(DeleteCourseActivity.this,"Course succesfully deleted",Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(DeleteCourseActivity.this,"Could not delete course",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(DeleteCourseActivity.this,"Could not find course",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

    }
}