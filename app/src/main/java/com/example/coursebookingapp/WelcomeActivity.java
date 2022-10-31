package com.example.coursebookingapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coursebookingapp.classes.*;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    User currentUser;
    Button createCourseBtn,editCourseBtn,deleteCourseBtn,deleteAccountBtn,signOut;
    FirebaseFirestore fstore;

    TextView userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //set welcome text
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uuid = user.getUid();

        userInfo = findViewById(R.id.welcomeText);
        fstore = FirebaseFirestore.getInstance();
        //update text
        fstore.collection("Users").document(uuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userInfo.setText(String.format("Welcome, %s.\nRole: %s",documentSnapshot.get("Name"),documentSnapshot.get("AccountType")));
            }
        });






        //set button visibility depending on role (only admin so far)
        if(currentUser instanceof Admin){
            findViewById(R.id.createCourseBtn).setEnabled(true);
            findViewById(R.id.deleteCourseBtn).setEnabled(true);
            findViewById(R.id.editCourseBtn).setEnabled(true);
            findViewById(R.id.deleteAccountBtn).setEnabled(true);
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
                startActivity(new Intent(WelcomeActivity.this,CreateCourseActivity.class));
            }
        });
        editCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this,EditCourseActivity.class));
            }
        });
        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this,DeleteCourseActivity.class));
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