package com.example.coursebookingapp;

import static org.junit.Assert.*;


import androidx.annotation.NonNull;

import com.example.coursebookingapp.database.Auth;
import org.junit.Test;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import android.widget.Toast;


public class LoginActivityTest{
    public static String EMAIL_PATTERN = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
    LoginActivityTest loginActivityTest;
    Auth auth;

//    @Test
//    public void signInWithEmailPassword(String email, String password) {
//        Task<AuthResult> taskAuth = auth.signIn(email, password);
//        OnCompleteListener<AuthResult> listener = this::
//        taskAuth.addOnCompleteListener(loginActivityTest, listener);
//    }

    @Test
    public void validEmail() throws NullPointerException{
        String email = "admin123@gmail.com";
        assertTrue("Email is Valid",email.toLowerCase().matches(EMAIL_PATTERN));
    }

    @Test
    public void invalidEmail() throws NullPointerException{
        String email2 = "has.y.spring@gmail.com";
        assertTrue("Email is Valid",email2.toLowerCase().matches(EMAIL_PATTERN));
    }
}