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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText name,email,password;
    Button registerBtn,loginBtn;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    CheckBox isTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        name = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        registerBtn = findViewById(R.id.registerBtn);
        loginBtn = findViewById(R.id.loginBtn2);
        isTeacher = findViewById(R.id.isTeacher);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(name);
                checkField(email);
                checkField(password);

                if(valid){
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser user = fAuth.getCurrentUser();
                                    Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    DocumentReference df = fStore.collection("Users").document(user.getUid());
                                    Map<String, Object> userInfo =  new HashMap<>();
                                    userInfo.put("AccountType",isTeacher.isChecked() ? "Teacher" : "Student");
                                    userInfo.put("Name", name.getText().toString());
                                    userInfo.put("Email", email.getText().toString());

                                    userInfo.put("isTeacher", isTeacher.isChecked() ? true : false);



                                    df.set(userInfo);

                                    startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
}