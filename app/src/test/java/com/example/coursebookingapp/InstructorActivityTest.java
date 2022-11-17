package com.example.coursebookingapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class InstructorActivityTest {
    public static String HOUR_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]";
    public static String DAY_REGEX = "((Mon|Tue|Wed|Thu|Fri|Sat|Sun)(,|-)?)+";

    @Test
    public void invalidDay() throws NullPointerException{
        String email = "Monday";
        assertTrue("Days are invalid",email.toLowerCase().matches(DAY_REGEX));
    }

    @Test
    public void validHours() throws NullPointerException{
        String email = "1:30-2:30";
        assertTrue("Hours are valid",email.toLowerCase().matches(HOUR_REGEX));
    }

    @Test
    public void invalidHours() throws NullPointerException{
        String email = "5-7:30";
        assertTrue("Hours are invalid",email.toLowerCase().matches(HOUR_REGEX));
    }
}