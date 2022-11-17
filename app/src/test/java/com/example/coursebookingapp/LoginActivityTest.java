package com.example.coursebookingapp;

import static org.junit.Assert.*;

import android.app.Instrumentation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Locale;

public class LoginActivityTest {
    public static String EMAIL_PATTERN = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";

    public LoginActivity loginActivity;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Before
    public void setUp() throws Exception{
        emailEditText = loginActivity.findViewById(R.id.registerName);
        passwordEditText = loginActivity.findViewById(R.id.registerEmail);
        loginButton = loginActivity.findViewById(R.id.loginBtn);
    }

    @Test
    public void loginTestSuccess() throws Exception{
        emailEditText.setText("hastest@gmail.com");
        passwordEditText.setText("hastest1");

        loginButton.performClick();
    }

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