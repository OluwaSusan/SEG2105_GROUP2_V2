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

import java.util.ArrayList;


public class Instructor extends AppCompatActivity {

    Button viewassigned, homeBtn_instructor;
    FirebaseAuth fAuth;
    DBHandlerCourses dbCourses;
    RecyclerView recCourses;
    CourseAdapter adapter;
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

        setContentView(R.layout.activity_instructor);

        viewassigned = findViewById(R.id.view_assigned);
        homeBtn_instructor = findViewById(R.id.homeBtn_instructor);
        dbCourses = new DBHandlerCourses();


        viewassigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AssignedCourses.class));
            }
        });


        homeBtn_instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivityWelcome.class));
            }
        });

        BR.onReceive(this, null);
    }

    private void initRecylcerView(ArrayList<Course> courseList){
        adapter = new CourseAdapter(Instructor.this, courseList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_instructor);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Instructor.this));
    }



}


