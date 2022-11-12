package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Auth auth;
    Store store;
    String email, password;
    Button loginBtn;
    TextView registerBtn;
    EditText emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = new Auth();
        store = new Store();
        emailField = findViewById(R.id.registerName);
        passwordField = findViewById(R.id.registerEmail);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        if (auth.isSignedIn()) { updateScreenWelcome(); }

        setClickListeners();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.loginBtn) {
            email = emailField.getText().toString();
            password = passwordField.getText().toString();
            signInWithEmailPassword(email, password);
        } else if (id == R.id.registerBtn) {
            updateScreenRegister();
        }
    }

    public boolean fieldsFilled(){
        EditText[] editTexts = {emailField, passwordField};

        for (EditText editText : editTexts) {
            if (editText.getText().toString().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private void signInWithEmailPassword(String email, String password) {
        if (!fieldsFilled()) {
            toast("Empty fields");
            return;
        }

        Task<AuthResult> taskAuth = auth.signIn(email, password);
        OnCompleteListener<AuthResult> listener = this::onSignInComplete;
        taskAuth.addOnCompleteListener(LoginActivity.this, listener);
    }

    private void onSignInComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            updateScreenWelcome();
            toast("Successfully signed in");
        } else {
            toast("Invalid credentials");
        }
    }

    private void updateScreenRegister(){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private void updateScreenWelcome() {
        Intent intent = new Intent(getApplicationContext(), SpinnerActivity.class);
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