package com.example.coursebookingapp;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
import com.example.coursebookingapp.MainActivity;
import com.example.coursebookingapp.CourseActivity;
import com.example.coursebookingapp.CoursePage;
import com.example.coursebookingapp.SearchCourse;


public class JUnitTest {
    private MainActivity main;
    private CourseActivity course;
    private CoursePage coursep;
    private SearchCourse searchc;


    @Before
    public void setUp() {
        main = new MainActivity();
        course = new CourseActivity();
        coursep = new CoursePage();
        searchc = new SearchCourse();
    }

    @Test
    public void validation_full_name() {
        boolean result = main.validFullName("Steve McQueen");
        assertTrue(result);
    }

    @Test
    public void validation_password() {
        boolean result = main.validPasswordTest("Password123");
        assertTrue(result);
    }


    @Test
    public void validation_coursename() {
        boolean res = course.checkCourseNameValidTest("Linear Algebra");
        assertTrue(res);
    }

    @Test
    public void validation_courseID() {
        boolean res = course.checkCourseIDValidTest("MAT1348");
        assertTrue(res);
    }

    @Test
    public void validation_coursedescription() {
        boolean res = coursep.description_validation_test("This course is to study the fundamentals of engineering design.");
        assertTrue(res);
    }

    @Test
    public void validateTime_test() {
        boolean res = coursep.validateTimeTest("10:00am-11:00pm");
        assertTrue(res);
    }

    @Test
    public void search_validation_coursename() {
        boolean res = searchc.search_checkCourseNameValidTest("Linear Algebra");
        assertTrue(res);
    }

    @Test
    public void search_validation_courseID() {
        boolean res = searchc.search_checkCourseIDValidTest("MAT1348");
        assertTrue(res);
    }
}


