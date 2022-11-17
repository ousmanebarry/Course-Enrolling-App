package com.example.coursebookingapp;

import static org.junit.Assert.assertTrue;

import junit.framework.TestCase;

import org.junit.Test;

public class RegisterActivityTest {
    public static String NAME_PATTERN = "[a-z ,.'-]+$";


    @Test
    public void validNaming() throws NullPointerException{
        String name = "Hasbiya Yusuf";
        assertTrue("Name is Valid",name.toLowerCase().matches(NAME_PATTERN));
    }

    @Test
    public void validNaming2() throws NullPointerException{
        String name = "Jo$eph W3mbly";
        assertTrue("Name is invalid",name.toLowerCase().matches(NAME_PATTERN));
    }

}