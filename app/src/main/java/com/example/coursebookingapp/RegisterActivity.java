package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import com.example.coursebookingapp.classes.Instructor;
import com.example.coursebookingapp.classes.Student;
import com.example.coursebookingapp.classes.User;
import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Auth auth;
    Store store;
    EditText nameField, emailField, passwordField;
    String name, email, password;
    Button loginBtn, registerBtn;
    CheckBox isTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = new Auth();
        store = new Store();
        nameField = findViewById(R.id.registerName);
        emailField = findViewById(R.id.registerEmail);
        passwordField = findViewById(R.id.registerPassword);
        registerBtn = findViewById(R.id.registerBtn);
        loginBtn = findViewById(R.id.loginBtnRegister);
        isTeacher = findViewById(R.id.isTeacher);

        setClickListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerBtn:
                email = emailField.getText().toString();
                password = passwordField.getText().toString();
                signUpWithEmailPassword(email, password);
                break;
            case R.id.loginBtnRegister:
                updateScreen(LoginActivity.class);
                break;
        }
    }

    public boolean fieldsFilled(){
        EditText[] editTexts = {nameField, emailField, passwordField};

        for (EditText editText : editTexts) {
            if (editText.getText().toString().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private void signUpWithEmailPassword(String email, String password) {
        if (!fieldsFilled()) {
            toast("Empty fields");
            return;
        }

        Task<AuthResult> task = auth.signUp(email, password);

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) { onSignUpComplete(task); }
        };

        task.addOnCompleteListener(RegisterActivity.this, listener);
    }

    private void onSignUpComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            User newUserInfo;
            FirebaseUser user = auth.getCurrentUser();

            if (isTeacher.isChecked()) {
                newUserInfo = new Instructor(email, name);
            } else {
                newUserInfo = new Student(email, name);
            }

            store.addUser(newUserInfo, user.getUid());

            updateScreenFinal(WelcomeActivity.class);
        } else {
            toast("An error occurred");
        }
    }

    private void updateScreen(Class<LoginActivity> next){
        Intent intent = new Intent(getApplicationContext(), next);
        startActivity(intent);
    }

    private void updateScreenFinal(Class<WelcomeActivity> next) {
        Intent intent = new Intent(getApplicationContext(), next);
        startActivity(intent);
        finish();
    }

    private void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void setClickListeners() {
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }
}