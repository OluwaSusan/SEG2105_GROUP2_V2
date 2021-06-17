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

    private void saveCourse() {

    }

    private void checkValidation() {
    }

}
