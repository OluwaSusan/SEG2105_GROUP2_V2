package com.example.coursebookingapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Administrator extends Activity {

    Button viewusers_admin, homeBtn_admin, backBtn_admin;
    com.google.android.material.floatingactionbutton.FloatingActionButton coursebtn_add;
    FirebaseAuth fAuth;
    DBHandlerCourses dbCourses;
    RecyclerView recUsers, recCourses;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        viewusers_admin = findViewById(R.id.viewusers_admin);
        homeBtn_admin = findViewById(R.id.homeBtn_admin);
        coursebtn_add = findViewById(R.id.coursebtn_add);
        dbCourses = new DBHandlerCourses();

//        dbCourses.addCourse(new Course("Computer Science", "ITI1121"));
//        dbCourses.addCourse(new Course("SEG2105", "Software Engineering"));
//        dbCourses.addCourse(new Course("Linear Algebra", "MAT1346"));

        viewusers_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserList.class));
            }
        });
        homeBtn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivityWelcome.class));
            }
        });
        coursebtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });
        
        BR.onReceive(this, null);

    }

    public void showPopup(){
        startActivity(new Intent(getApplicationContext(), CourseActivity.class));
    }

    private void initRecylcerView(ArrayList<Course> courseList){
        adapter = new CourseAdapter(this, courseList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_admin);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}