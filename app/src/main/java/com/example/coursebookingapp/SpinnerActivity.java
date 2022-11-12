package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.coursebookingapp.classes.Admin;
import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;

import java.util.Objects;

public class SpinnerActivity extends AppCompatActivity {
    Auth auth;
    Store store;
    String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        auth = new Auth();
        store = new Store();
        String uuid = auth.getCurrentUser().getUid();

        store.getUserDocument(uuid).addOnSuccessListener(s -> {
            accountType = Objects.requireNonNull(s.get("accountType")).toString();

            switch (accountType) {
                case "Admin":
                    updateMainScreen(AdminActivity.class);
                    break;
                case "Instructor":
                    updateMainScreen(WelcomeActivity.class);
                    break;
            }
        });
    }

    private void updateMainScreen(Class<?> next) {
        Intent intent = new Intent(getApplicationContext(), next);
        startActivity(intent);
        finish();
    }
}