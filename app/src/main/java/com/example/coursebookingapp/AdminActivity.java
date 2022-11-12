package com.example.coursebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;
import com.example.coursebookingapp.models.AdminCourseModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity {
    TextView welcomeTxt;
    Auth auth;
    Store store;
    Button logoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        auth = new Auth();
        store = new Store();
        String uuid = auth.getCurrentUser().getUid();

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        welcomeTxt = findViewById(R.id.welcomeTxt);
        logoutBtn = findViewById(R.id.logOutBtn);

        store.getUserDocument(uuid).addOnSuccessListener(documentSnapshot -> {
            welcomeTxt.setText(String.format("Welcome, %s! (%s)", documentSnapshot.get("name"), documentSnapshot.get("accountType")));
        });

        store.getAllCourses().addOnSuccessListener(query -> {
            ArrayList<AdminCourseModel> courseModels = new ArrayList<>();

            for (DocumentSnapshot snapshot : query) {
                String name = Objects.requireNonNull(snapshot.get("name")).toString();
                String code = Objects.requireNonNull(snapshot.get("code")).toString();
                AdminCourseModel adminCourseModel = new AdminCourseModel(name, code);
                courseModels.add(adminCourseModel);
            }

            CourseRecyclerViewAdapter adapter = new CourseRecyclerViewAdapter(this, courseModels);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        });

        logoutBtn.setOnClickListener(view -> {
            auth.signOut();
            updateScreen(LoginActivity.class);
            finish();
        });
    }

    private void updateScreen(Class<?> next) {
        Intent intent = new Intent(getApplicationContext(), next);
        startActivity(intent);
    }
}