package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteCourseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();

        Button createBtn = findViewById(R.id.deleteCourseSubmitBtn);
        EditText courseCodeDelete = findViewById(R.id.deleteCourseCodeField);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fstore.collection("courses").document(courseUUID).delete(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Log.d(TAG, "Course successfully deleted!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error deleting Course", e);
                    }
                });
            }
        }

    }
}