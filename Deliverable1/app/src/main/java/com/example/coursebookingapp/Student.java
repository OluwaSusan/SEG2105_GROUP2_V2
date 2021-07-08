package com.example.coursebookingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Student extends AppCompatActivity {


    Button viewCourses_student, homeBtn_student;
    com.google.android.material.floatingactionbutton.FloatingActionButton searchBtn_student;
    FirebaseAuth fAuth;
    DBHandlerCourses dbCourses;
    RecyclerView recCourses;
    CourseAdapter adapter;
    boolean sw = false;

    BroadcastReceiver BR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dbCourses.listCourses(new FirebaseCallBackCourses() {
                @Override
                public void onCallBackCourseList(ArrayList<Course> courseList) {

                    initRecylcerView(courseList);

                }

                @Override
                public void onCallBackCourse(Course course) {

                }
            });
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //refresh the recyclerview to see the new courses
        registerReceiver(BR, new IntentFilter("Refresh Classes"));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(BR);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student);

        viewCourses_student = findViewById(R.id.viewCourses_Student);
        homeBtn_student = findViewById(R.id.homeBtn_student);
        dbCourses = new DBHandlerCourses();
        searchBtn_student = findViewById(R.id.searchBtn_student);



        viewCourses_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), AssignedCourses.class));
                sw = !sw;
                switch (sw){
                    case true:

                        break;

                    case false:

                        break;
                }

            }
        });


        homeBtn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivityWelcome.class));
            }
        });

        searchBtn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchCourse.class));
            }
        });
        BR.onReceive(this, null);
    }


    private void initRecylcerView(ArrayList<Course> courseList){
        adapter = new CourseAdapter(Student.this, courseList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_student);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Student.this));
    }


}

