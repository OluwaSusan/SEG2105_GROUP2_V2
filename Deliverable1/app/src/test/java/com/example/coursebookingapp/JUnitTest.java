package com.example.coursebookingapp;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.manipulation.Ordering;


import static org.junit.Assert.*;
import com.example.coursebookingapp.MainActivity;
import com.example.coursebookingapp.CourseActivity;
import com.example.coursebookingapp.CoursePage;
import com.example.coursebookingapp.SearchCourse;
import com.example.coursebookingapp.UserAdapter;
import com.example.coursebookingapp.CourseAdapter;

import java.util.ArrayList;


public class JUnitTest {
    private MainActivity main;
    private CourseActivity course;
    private CoursePage coursep;
    private SearchCourse searchc;
    private UserAdapter userAdap;
    private CourseAdapter courseAdap;


    @Before
    public void setUp() {
        main = new MainActivity();
        course = new CourseActivity();
        coursep = new CoursePage();
        searchc = new SearchCourse();
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("Computer Science", "CSI1101"));
        courses.add(new Course("Computer Engineering", "CEG1101"));
        courses.add(new Course("Computer Software", "SEG1101"));
        courses.add(new Course("Computer Initialization", "SIG1101"));
        ArrayList<User> students = new ArrayList<>();
        students.add(new User("carmudgeon", "Car Mudgeon", "123456", UserType.STUDENT, "carmudgeon@gmail.com"));
        students.add(new User("carr", "Carr", "123456", UserType.STUDENT, "carr@gmail.com"));
        userAdap = new UserAdapter(students);
        courseAdap = new CourseAdapter(courses);
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

    //D3 Tests

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

    @Test
    public void user_count() {
        boolean res = userAdap.usertotal_validation(2);
        assertTrue(res);
    }

    @Test
    public void refresh_user_test() {
        boolean res = userAdap.test_refresh("carmudgeon");
        assertTrue(res);
    }




}


