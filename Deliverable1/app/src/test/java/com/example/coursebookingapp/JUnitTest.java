package com.example.coursebookingapp;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
import com.example.coursebookingapp.MainActivity;
import com.example.coursebookingapp.CourseActivity;


public class JUnitTest {
    private MainActivity main;
    private CourseActivity course;

    @Before
    public void setUp() {
        main = new MainActivity();
        course = new CourseActivity();
    }

    @Test
    public void validation_full_name(){
        boolean result = main.validFullName("Steve McQueen");
        assertTrue(result);
    }
    

    @Test
    public void validation_courses(){
        boolean res = course.checkNameValidTest("Linear Algebra", "MAT1348");
        assertTrue(res);
    }
    
    @Test 
    public void validation_userNameExists(){
        boolean res = main.userNameExists("stevey");
        assertTrue(res);
    }
    

}
