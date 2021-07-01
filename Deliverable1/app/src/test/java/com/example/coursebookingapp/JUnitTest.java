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
    public void validation_password(){
        boolean result = main.validPasswordTest("Password123");
        assertTrue(result);
    }
    

    @Test
    public void validation_coursename(){
        boolean res = course.checkCourseNameValidTest("Linear Algebra");
        assertTrue(res);
    }

    @Test
    public void validation_courseID(){
        boolean res = course.checkCourseIDValidTest("MAT1348");
        assertTrue(res);
    }


//    @Test
//  public void validation_userNameExists(){
//        boolean res = main.userNameExists("stevey");
//       assertTrue(res);
//   }
    

}
