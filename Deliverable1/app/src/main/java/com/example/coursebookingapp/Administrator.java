package com.example.coursebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Administrator extends AppCompatActivity {

    Button viewusers_admin, homeBtn_admin, backBtn_admin;
    com.google.android.material.floatingactionbutton.FloatingActionButton coursebtn_add;
    RecyclerView recUsers, recCourses;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        viewusers_admin = findViewById(R.id.viewusers_admin);
        homeBtn_admin = findViewById(R.id.homeBtn_admin);
        backBtn_admin = findViewById(R.id.backBtn_admin);
        coursebtn_add = findViewById(R.id.coursebtn_add);


        viewusers_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_userlist);
            }
        });
        homeBtn_admin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_welcome);
            }
        });
        coursebtn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                courseWindow(null);
            }


        });
        backBtn_admin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_administrator);
            }
        });
    }
    private void courseWindow(Course c) {
        //code for a 'popup' (doesn't have to be) edit existing course
        if (c.equals(null)){

        }
    }
    //missing code for Recyclerview and listing out users/courses depending on the contentview
}
