package com.example.coursebookingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;


public class Instructor extends AppCompatActivity {

    Button viewassigned, homeBtn_instructor;
    com.google.android.material.floatingactionbutton.FloatingActionButton searchbtn;
    FirebaseAuth fAuth;
    DBHandlerCourses dbCourses;
    DBHandlerUsers dbUsers;
    boolean sw = false;
    RecyclerView recCourses;
    CourseAdapter adapter;
    BroadcastReceiver BR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
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
        dbUsers = new DBHandlerUsers();
        fAuth = FirebaseAuth.getInstance();
        searchbtn = findViewById(R.id.searchbtn);


        viewassigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw = !sw;
                refresh();
            }
        });


        homeBtn_instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivityWelcome.class));
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchCourse.class));
            }
        });
        BR.onReceive(this, null);
    }


    private void initRecylcerView(ArrayList<Course> courseList) {
        adapter = new CourseAdapter(Instructor.this, courseList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_instructor);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Instructor.this));
    }

    private boolean getSw() {
        return sw;
    }

    private void refresh() {
        dbCourses.listCourses(new FirebaseCallBackCourses() {
            @Override
            public void onCallBackCourseList(ArrayList<Course> courseList) {
                if (getSw() == false) {
                    initRecylcerView(courseList);
                } else {
                    ArrayList<Course> assigCourses = new ArrayList<>();

                    String username = fAuth.getCurrentUser().getEmail().split("@")[0];
                    dbUsers.findUser(username, new FirebaseCallBackUsers() {
                        @Override
                        public void onCallBackUsersList(ArrayList<User> userList) {

                        }

                        @Override
                        public void onCallBackUser(User user) {
                            for (int i = 0; i < courseList.size(); i++) {
                                for (Map.Entry mapElement : user.getMyCourses().entrySet()) {
                                    String key = (String) mapElement.getKey();

                                    if (courseList.get(i).getCourseCode().equals(key)) {
                                        assigCourses.add(courseList.get(i));
                                    }
                                }

                            }

                            initRecylcerView(assigCourses);
                        }
                    });

                }
            }

            @Override
            public void onCallBackCourse(Course course) {
            }
        });


    }
}