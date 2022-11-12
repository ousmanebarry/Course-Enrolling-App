package com.example.coursebookingapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {
    TextView welcomeTxt;
    Auth auth;
    Store store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        auth = new Auth();
        store = new Store();
        String uuid = auth.getCurrentUser().getUid();
        welcomeTxt = findViewById(R.id.welcomeTxt);

        store.getUserDocument(uuid).addOnSuccessListener(documentSnapshot -> {
            welcomeTxt.setText(String.format("Welcome, %s! (%s)", documentSnapshot.get("name"), documentSnapshot.get("accountType")));
        });
    }
}