package com.example.coursebookingapp;

import org.junit.Test;

import static org.junit.Assert.*;
import com.example.coursebookingapp.MainActivity;
import com.example.coursebookingapp.CourseActivity;


public class JUnitTest {

//    CourseActivity course = new CourseActivity();


    String str;




//    @Test
//    public void validation_courses(){
//
//        str = course.checkValidation("Introduction to Discrete Math", "MAT1348");
//        assertThat(str,null);
//    }

    @Test
    public void validation_full_name(){
        MainActivity main = new MainActivity();
        boolean result = main.validFullName("Stevieboy123");
        assertTrue(result);
    }

//    @Test
//    public void validation_userNameExists(){
//        assertThat(main.userNameExists.isTrue());
//    }


}
