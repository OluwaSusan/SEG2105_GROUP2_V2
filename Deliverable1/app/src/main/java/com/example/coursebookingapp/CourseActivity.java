package com.example.coursebookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CourseActivity extends Activity {

    Button save_btn;
    TextInputLayout course_name, course_id, course_description;
    TextView errorCourseEnter;
    DBHandlerCourses dbCourses;
    Course foundCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        //putExtra()
        save_btn = findViewById(R.id.save_btn);
        dbCourses = new DBHandlerCourses();


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
                saveCourse();
                closeWindow();
            }
        });

    }

    private void closeWindow() {
    }

    private void saveCourse(String courseName, String courseID) {


        dbCourses.addCourse(new Course(courseName, courseID));


    }



    private boolean checkValidation(String courseName, String courseID) {

        final boolean[] returnBoolean = new boolean[1];

        if (courseID.length() != 7) {
            return false;
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(courseID.charAt(i))) {
                return false;
            }
        }

        for (int i = 3; i < 7; i++) {
            if (!Character.isDigit(courseID.charAt(i))) {
                return false;
            }
        }


        dbCourses.listCourses(new FirebaseCallBackCourses() {
            @Override
            public void onCallBackCourseList(ArrayList<Course> courseList) {

                Course course = new Course(courseName, courseID);
                boolean returnFalse = false;

                for (int i = 0; i < courseList.size(); i++) {

                    if (courseList.get(i).equals(course)) {
                        returnBoolean[0] = false;
                        returnFalse = true;
                        break;
                    }
                }

                if (!returnFalse) {
                    returnBoolean[0] = true;
                }
            }

            @Override
            public void onCallBackCourse(Course course) {

            }


        });

        return returnBoolean[0];
    }
}