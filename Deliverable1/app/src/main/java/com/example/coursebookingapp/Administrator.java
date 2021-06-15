package com.example.coursebookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Administrator extends Activity {

    Button viewusers_admin, homeBtn_admin, backBtn_admin;
    com.google.android.material.floatingactionbutton.FloatingActionButton coursebtn_add;
    FirebaseAuth fAuth;
    FirebaseDatabase realDatabase;
    RecyclerView recUsers, recCourses;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        viewusers_admin = findViewById(R.id.viewusers_admin);
        homeBtn_admin = findViewById(R.id.homeBtn_admin);
        backBtn_admin = findViewById(R.id.backBtn_admin);
        coursebtn_add = findViewById(R.id.coursebtn_add);

        viewCourses();

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
                createNewCourse();
            }


        });
        backBtn_admin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_administrator);
            }
        });
    }

    private void viewCourses() {
        //recyclerview shows courses as course_item objects
    }
    private void viewStudents() {
        //recyclerview shows students as user_item (only students from the database) objects
    }

    private void createNewCourse() {
        //adds element in the recyclerview, adds the element in the database once the name and course code is updated
    }

    public void currentAdmin(){

        fAuth = FirebaseAuth.getInstance();
        realDatabase = FirebaseDatabase.getInstance();

        String email = fAuth.getCurrentUser().getEmail();
        String[] parts = email.split("@");
        String username = parts[0];
        Log.i("test" , "username " + username);


//        DBHandlerUsers db = new DBHandlerUsers();
//
//        db.findUser(username, new FirebaseCallBackUsers() {
//            @Override
//            public void onCallBackUsersList(ArrayList<User> userList) {
//
//            }
//
//            @Override
//            public void onCallBackUser(User user) {
//                name_loggedin_textview.setText(user.getFullName());
//                role_loggedin_textview.setText(user.getUserType().toString());                }
//        });



    }
}
