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


public class Student extends AppCompatActivity {


    Button viewCourses_student, homeBtn_student;
    com.google.android.material.floatingactionbutton.FloatingActionButton searchBtn_student;
    FirebaseAuth fAuth;
    DBHandlerCourses dbCourses;
    DBHandlerUsers dbUsers;
    RecyclerView recCourses;
    CourseAdapter adapter;
    boolean sw = false;

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

        setContentView(R.layout.activity_student);

        viewCourses_student = findViewById(R.id.viewCourses_Student);
        homeBtn_student = findViewById(R.id.homeBtn_student);
        dbCourses = new DBHandlerCourses();
        dbUsers = new DBHandlerUsers();
        fAuth = FirebaseAuth.getInstance();
        searchBtn_student = findViewById(R.id.searchBtn_student);




        viewCourses_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), AssignedCourses.class));
                sw = !sw;
                refresh();
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

    private boolean getSw(){
        return sw;
    }
    private void initRecylcerView(ArrayList<Course> courseList){
        adapter = new CourseAdapter(Student.this, courseList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_student);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Student.this));
    }

    private void refresh(){
        dbCourses.listCourses(new FirebaseCallBackCourses() {
        @Override
        public void onCallBackCourseList(ArrayList<Course> courseList) {
            if (getSw() == false) {
                initRecylcerView(courseList);
            }
            else{
                ArrayList<Course> assigCourses = new ArrayList<>();

                String username =  fAuth.getCurrentUser().getEmail().split("@")[0];
                dbUsers.findUser(username, new FirebaseCallBackUsers() {
                    @Override
                    public void onCallBackUsersList(ArrayList<User> userList) {

                    }

                    @Override
                    public void onCallBackUser(User user) {
                        for (int i = 0; i <courseList.size(); i++){
                            for (Map.Entry mapElement : user.getMyCourses().entrySet()) {
                                String key = (String)mapElement.getKey();

                                if (courseList.get(i).getCourseCode().equals(key)){
                                    assigCourses.add(courseList.get(i));
                                    Log.i("test", "courseCode " + courseList.get(i));
                                }
                            }

                        }
                        if (courseList.size() == 0){
                            Log.i("test", "courseList is empty.");
                        }
                        if (assigCourses.size() == 0){
                            Log.i("test", "assig courses is empty.");
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

