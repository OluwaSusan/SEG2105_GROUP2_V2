package com.example.coursebookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends Activity {

    Button save_btn;
    TextInputLayout course_name, course_id;
    TextView title, error_register;
    DBHandlerCourses dbCourses;
    String specificCourse;
    List<Course> courseList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        save_btn = findViewById(R.id.save_btn);
        dbCourses = new DBHandlerCourses();
        course_name = findViewById(R.id.course_name);
        course_id = findViewById(R.id.course_id);
        title = findViewById(R.id.course_activity_title);
        error_register = findViewById(R.id.error_register);
        checkExtra(savedInstanceState);
        loadCourselist();


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trim() coursename/id
                String courseName = course_name.getEditText().getText().toString().trim();
                String courseID = course_id.getEditText().getText().toString().trim();


                String errorMessage = checkValidation(courseName, courseID);
                if (errorMessage == null) {
                    if (specificCourse == null) {
                        saveCourse(courseName, courseID);
                    } else {
                        deleteCourse(specificCourse);
                        saveCourse(courseName, courseID);

                    }
                    closeWindow();
                }
                else{
                    error_register.setVisibility(View.VISIBLE);
                    error_register.setText(errorMessage);
                }
            }
        });

    }

    private void loadCourselist() {

        dbCourses.listCourses(new FirebaseCallBackCourses() {
            @Override
            public void onCallBackCourseList(ArrayList<Course> courseList) {
                CourseActivity.this.courseList = courseList;

            }
            @Override
            public void onCallBackCourse(Course course) {

            }
        });
    }

    private void deleteCourse(String specificCourse) {
        dbCourses.deleteCourse(specificCourse);
    }

    private void checkExtra(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                specificCourse= null;
            } else {
                specificCourse= extras.getString("Course_ID");
                title.setText("Edit Course");
                dbCourses.findCourse(specificCourse, new FirebaseCallBackCourses() {
                    @Override
                    public void onCallBackCourseList(ArrayList<Course> courseList) {

                    }

                    @Override
                    public void onCallBackCourse(Course course) {
                        course_name.getEditText().setText(course.getCourseName());
                        course_id.getEditText().setText(course.getCourseCode());
                    }
                });

            }
        } else {
            specificCourse= (String) savedInstanceState.getSerializable("Course_ID");
        }
    }

    private void closeWindow() {
        finish();
        Intent i = new Intent("Refresh Classes");
        sendBroadcast(i);
    }

    private void saveCourse(String courseName, String courseID) {


        dbCourses.addCourse(new Course(courseName, courseID));


    }



    private String checkValidation(String courseName, String courseID) {


        if (courseID.length() != 7) {
            return "course_ID must be 7 chars";
        }

        if(courseName.isEmpty()){
            return "course name cannot be blank";
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(courseID.charAt(i))) {
                return "course_ID must start with 3 letters";
            }
        }

        for (int i = 3; i < 7; i++) {
            if (!Character.isDigit(courseID.charAt(i))) {

                return "course_ID must end with 4 digits";
            }
        }
        return courseList.contains(new Course(courseName, courseID))? "This course already exists":null;
    }


}

