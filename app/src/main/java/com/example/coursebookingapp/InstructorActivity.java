package com.example.coursebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;

public class InstructorActivity extends AppCompatActivity {
    Auth auth;
    Store store;
    TextView welcomeTxt;
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        auth = new Auth();
        store = new Store();
        String uuid = auth.getCurrentUser().getUid();

        welcomeTxt = findViewById(R.id.welcomeTxt);
        logoutBtn = findViewById(R.id.logOutBtn);

        store.getUserDocument(uuid).addOnSuccessListener(documentSnapshot -> {
            welcomeTxt.setText(String.format("Welcome, %s! (%s)", documentSnapshot.get("name"), documentSnapshot.get("accountType")));
        });

        logoutBtn.setOnClickListener(view -> {
            auth.signOut();
            updateScreen();
            finish();
        });

        loadCourses();
    }

    private void loadCourses() {
    }

    private void updateScreen() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
