package com.example.coursebookingapp;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
import com.example.coursebookingapp.MainActivity;
import com.example.coursebookingapp.CourseActivity;


public class JUnitTest {
    private MainActivity main;

    @Before
    public void setUp() {
        main = new MainActivity();
    }

    @Test
    public void validation_full_name(){
        boolean result = main.validFullName("Steve McQueen");
        assertTrue(result);
    }

//    @Test
//    public void validation_userNameExists(){
//        assertThat(main.userNameExists.isTrue());
//    }

//    @Test
//    public void validation_courses(){
//
//        str = course.checkValidation("Introduction to Discrete Math", "MAT1348");
//        assertThat(str,null);
//    }

}
