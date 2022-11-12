package com.example.coursebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;

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
        welcomeTxt = findViewById(R.id.welcomeTxt);

        logoutBtn = findViewById(R.id.logOutBtn);

        store.getUserDocument(uuid).addOnSuccessListener(documentSnapshot -> {
            welcomeTxt.setText(String.format("Welcome, %s! (%s)", documentSnapshot.get("name"), documentSnapshot.get("accountType")));
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