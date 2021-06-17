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

public class CourseActivity extends Activity {

    Button save_btn;
    TextInputLayout course_name, course_id;
    TextView title, errorCourseEnter;
    DBHandlerCourses dbCourses;
    String specificCourse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        //putExtra()
        checkExtra(savedInstanceState);
        save_btn = findViewById(R.id.save_btn);
        dbCourses = new DBHandlerCourses();
        course_name = findViewById(R.id.course_name);
        course_id = findViewById(R.id.course_id);
        title = findViewById(R.id.course_activity_title);



        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean check = checkValidation(course_name.getEditText().getText().toString(), course_id.getEditText().getText().toString());
                if (check) {
                    if (specificCourse == null) {
                        saveCourse(course_name.getEditText().getText().toString(), course_id.getEditText().getText().toString());
                    } else {
                        deleteCourse(specificCourse);
                        saveCourse(course_name.getEditText().getText().toString(), course_id.getEditText().getText().toString());

                    }
                    closeWindow();
                }
                else{
                    //give error
                }
                Log.i("test", "Course test: " + course_name.getEditText().getText().toString() + " " + course_id.getEditText().getText().toString() + " " + check);
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
    }

    private void saveCourse(String courseName, String courseID) {


        dbCourses.addCourse(new Course(courseName, courseID));


    }



    private boolean checkValidation(String courseName, String courseID) {

        boolean returnBoolean = false;

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
                    Log.i("test", "course exists: " + courseList.get(i).getCourseCode());

                    if (courseList.get(i).equals(course)) {
//                        returnBoolean = false;
//                        returnFalse = true;
                        break;
                    }
                }
                Log.i("test", "checkfalse: " + returnFalse);

//                if (!returnFalse) {
//                    returnBoolean = true;
//                    Log.i("test", "IM INSIDE THE BASE");
//
//                }
            }

            @Override
            public void onCallBackCourse(Course course) {

            }


        });
        Log.i("test", "returned returnBoolean[0]: " + returnBoolean);

        return returnBoolean;
    }


}

