package com.example.coursebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {
    Auth auth;
    Store store;
    TextView userInfo;
    String accountType;
    Button createCourseBtn, editCourseBtn, deleteCourseBtn, deleteAccountBtn, signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        auth = new Auth();
        store = new Store();
        String uuid = auth.getCurrentUser().getUid();
        userInfo = findViewById(R.id.welcomeText);


        store.getUserDocument(uuid).addOnSuccessListener(documentSnapshot -> {
            userInfo.setText(String.format("Welcome, %s.\nRole: %s",documentSnapshot.get("name"),documentSnapshot.get("accountType")));
            accountType = documentSnapshot.get("accountType").toString();
        });


        //set button visibility depending on role (only admin so far)
        if(!Objects.equals(accountType, "Admin")) {
            findViewById(R.id.createCourseBtn).setEnabled(false);

            findViewById(R.id.deleteCourseBtn).setEnabled(false);
            findViewById(R.id.editCourseBtn).setEnabled(false);
            findViewById(R.id.deleteAccountBtn).setEnabled(false);
        }

        createCourseBtn = findViewById(R.id.createCourseBtn);
        editCourseBtn = findViewById(R.id.editCourseBtn);
        deleteCourseBtn = findViewById(R.id.deleteCourseBtn);
        deleteAccountBtn = findViewById(R.id.deleteAccountBtn);
        signOut = findViewById(R.id.logOutBtn);

        //button listeners
        createCourseBtn.setOnClickListener(view -> updateScreen(CreateCourseActivity.class));
        editCourseBtn.setOnClickListener(view -> updateScreen(EditCourseActivity.class));
        deleteCourseBtn.setOnClickListener(view -> updateScreen(DeleteCourseActivity.class));
        deleteAccountBtn.setOnClickListener(view -> updateScreen(DeleteUserActivity.class));
        signOut.setOnClickListener(view -> {
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