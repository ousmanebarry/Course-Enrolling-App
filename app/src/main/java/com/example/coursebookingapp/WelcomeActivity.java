package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;
import com.example.coursebookingapp.classes.*;
import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {
    Auth auth;
    Store store;
    String accountType;
    Button createCourseBtn, editCourseBtn, deleteCourseBtn, deleteAccountBtn, signOut;


    TextView userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        auth = new Auth();
        store = new Store();
        FirebaseUser user = auth.getCurrentUser();
        String uuid = user.getUid();
        userInfo = findViewById(R.id.welcomeText);


        store.getUserDocument(uuid).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userInfo.setText(String.format("Welcome, %s.\nRole: %s",documentSnapshot.get("name"),documentSnapshot.get("accountType")));
                accountType = documentSnapshot.get("accountType").toString();
            }
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
        createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, CreateCourseActivity.class));
            }
        });
        editCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, EditCourseActivity.class));
            }
        });
        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, DeleteCourseActivity.class));
            }
        });

        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Example use deletion
                // fauth.deleteUser()
                startActivity(new Intent(WelcomeActivity.this, DeleteUserActivity.class));
            }
        });


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}