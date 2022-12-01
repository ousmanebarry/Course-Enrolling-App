package com.example.coursebookingapp;

import com.example.coursebookingapp.classes.Student;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentTest extends TestCase {
    private Student s;
    private ArrayList<String> c;
    @Before
    protected void setUp(){
        c = new ArrayList<String>();
        c.add("GNG2101");
        c.add("SEG2105");

        s = new Student("student@mail.com","Stu Dent",c);
    }

    @Test
    public void testEnsureStudent() {
        assertNotNull(s);
        assertTrue(s.getAccountType().equals("Student"));
    }

    @Test
    public void testGetCourses() {
        List<String> courses = s.getCourses();
        courses.add("TEST101");
        assertTrue("Mutability",s.getCourses().get(2).equals("TEST101"));
    }

    @Test
    public void testGetMap() {
        Map<String,Object> m = s.getMap();
        assertTrue(m.get("name").equals("Stu Dent"));
        assertTrue(m.get("email").equals("student@mail.com"));
        assertTrue(m.get("course").equals(c));
    }
}