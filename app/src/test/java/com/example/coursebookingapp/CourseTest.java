package com.example.coursebookingapp;

import com.example.coursebookingapp.classes.Course;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CourseTest {
    private Course a,b,c;

    @Before
    public void setUp(){
        a = new Course("Test 1","TEST101");
        b = new Course("Test 2","TEST102");
        c = new Course("Test 3","TEST103");

        a.setHours("11:30-13:00");
        b.setHours("12:30-15:30");
        c.setHours("13:00-14:30");
    }

    @Test
    public void testGetHoursAsDoubles() {

        Assert.assertEquals("Starting time is correct for course a", 11.5, a.getHoursAsDoubles()[0],0);
        Assert.assertEquals("Ending time is correct for course a", 13.0, a.getHoursAsDoubles()[1],0);

        Assert.assertEquals("Starting time is correct for course b", 12.5, b.getHoursAsDoubles()[0],0);
        Assert.assertEquals("Ending time is correct for course b", 15.5, b.getHoursAsDoubles()[1],0);

        Assert.assertEquals("Starting time is correct for course c", 13.0, c.getHoursAsDoubles()[0],0);
        Assert.assertEquals("Ending time is correct for course c", 14.5, c.getHoursAsDoubles()[1],0);
    }

    @Test
    public void testCheckIfIntersects() {
        Assert.assertTrue("Course Intersect in a and b found", a.checkIfIntersects(b));
        Assert.assertTrue("Course Intersect in b and a found", b.checkIfIntersects(a));

        Assert.assertFalse("No intersect found in a and c", a.checkIfIntersects(c));
        Assert.assertFalse("No intersect found in c and a", c.checkIfIntersects(a));

        Assert.assertTrue("Course Intersect in c and b found", c.checkIfIntersects(b));
        Assert.assertTrue("Course Intersect in b and c found", b.checkIfIntersects(c));
    }
}