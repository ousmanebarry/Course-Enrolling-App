package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import com.example.coursebookingapp.database.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Auth auth;
    String email, password;
    Button loginBtn, registerBtn;
    EditText emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = new Auth();
        emailField = findViewById(R.id.loginEmail);
        passwordField = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        if (auth.isSignedIn()) { updateScreenFinal(WelcomeActivity.class); }

        setClickListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                email = emailField.getText().toString();
                password = passwordField.getText().toString();
                signInWithEmailPassword(email, password);
                break;
            case R.id.registerBtn:
                updateScreen(RegisterActivity.class);
                break;
        }
    }

    private void signInWithEmailPassword(String email, String password) {

        Task<AuthResult> task = auth.signIn(email, password);

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) { onSignInComplete(task); }
        };

        task.addOnCompleteListener(LoginActivity.this, listener);

    }

    private void onSignInComplete(@NonNull Task<AuthResult> task) {

        if (task.isSuccessful()) {
            updateScreenFinal(WelcomeActivity.class);
        } else {
            toast("Invalid credentials");
        }
    }

    private void updateScreen(Class<RegisterActivity> next){
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