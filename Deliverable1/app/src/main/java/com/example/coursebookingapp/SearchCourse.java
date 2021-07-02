package com.example.coursebookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.util.Strings;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchCourse extends Activity {

    Button search_btn;
    TextInputLayout query;
    TextView title, error_register;
    DBHandlerCourses dbCourses;
    String specificCourse;
    List<Course> courseList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_btn = findViewById(R.id.search_btn);
        dbCourses = new DBHandlerCourses();
        query = findViewById(R.id.query);
        title = findViewById(R.id.course_activity_title);
        error_register = findViewById(R.id.error_register);


        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trim() coursename/id
                String q = query.getEditText().getText().toString().trim();

                searchCourse(q);
            }
        });

    }

    private void searchCourse(String query) {

        dbCourses.searchCourse(query, new FirebaseCallBackCourses() {
            @Override
            public void onCallBackCourseList(ArrayList<Course> courseList) {

            }

            @Override
            public void onCallBackCourse(Course course) {
                if(course == null || Strings.isEmptyOrWhitespace(course.getCourseCode())) {
                    error_register.setVisibility(View.VISIBLE);
                    error_register.setText("No results");
                }
                else {
                    Intent i = new Intent(getApplicationContext(), CourseActivity.class);
                    i.putExtra("Course_ID", course.getCourseCode());
                    startActivity(i);
                    finish();
                }
            }
        });

    }



    public String checkValidation(String courseName, String courseID) {

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
    public boolean checkCourseNameValidTest(String courseName) {


        if (courseName.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean checkCourseIDValidTest(String courseID) {

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
        return true;
    }

}


