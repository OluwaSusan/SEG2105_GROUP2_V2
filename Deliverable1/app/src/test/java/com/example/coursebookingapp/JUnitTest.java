package com.example.coursebookingapp;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
import com.example.coursebookingapp.MainActivity;
import com.example.coursebookingapp.CourseActivity;
import com.example.coursebookingapp.CoursePage;


public class JUnitTest {
    private MainActivity main;
    private CourseActivity course;
    private CoursePage coursep ;

    @Before
    public void setUp() {
        main = new MainActivity();
        course = new CourseActivity();
        coursep = new CoursePage();
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

    @Test
    public void validation_coursedescription(){
        boolean res = coursep.description_validation_test("This course is to study the fundamentals of engineering design.");
        assertTrue(res);
    }


//    @Test
//  public void validation_userNameExists(){
//        boolean res = main.userNameExists("stevey");
//       assertTrue(res);
//   }
    

}
