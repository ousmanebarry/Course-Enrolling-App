package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.coursebookingapp.classes.Course;
import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class InstructorAllActivity extends AppCompatActivity implements InstructorAllRecyclerViewInterface {

    Button backBtn;
    ArrayList<Course> courseModels;
    Store store;
    Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_all);

        store = new Store();
        auth = new Auth();
        backBtn = findViewById(R.id.backBtn);

        Objects.requireNonNull(getSupportActionBar()).setTitle("All Courses");

        backBtn.setOnClickListener(view -> {
            finish();
        });

        loadCourses();
    }

    @Override
    public void onViewClick(int position) {}

    @Override
    public void onTeachClick(int position) {}

    private void loadCourses() {
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        courseModels = new ArrayList<>();

        store.getAllCourses().addOnSuccessListener(query -> {
            for (DocumentSnapshot snapshot : query) {
                String docID = Objects.requireNonNull(snapshot.getId());
                String name = Objects.requireNonNull(snapshot.get("name")).toString();
                String code = Objects.requireNonNull(snapshot.get("code")).toString();

                Course instructorCourseModel = new Course(name, code, docID);
                courseModels.add(instructorCourseModel);
            }

            InstructorAllCourseRecyclerViewAdapter adapter = new InstructorAllCourseRecyclerViewAdapter(this, courseModels, InstructorAllActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });
    }
}