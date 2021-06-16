package com.example.coursebookingapp;

import android.app.Activity;
import android.content.Intent;
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

public class Administrator extends Activity {

    Button viewusers_admin, homeBtn_admin, backBtn_admin;
    com.google.android.material.floatingactionbutton.FloatingActionButton coursebtn_add;
    FirebaseAuth fAuth;
    FirebaseDatabase realDatabase;
    RecyclerView recUsers, recCourses;
    ArrayList<Course> courselist;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        viewusers_admin = findViewById(R.id.viewusers_admin);
        homeBtn_admin = findViewById(R.id.homeBtn_admin);

        coursebtn_add = findViewById(R.id.coursebtn_add);

        viewCourses();

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

    private void viewCourses(){
        //recyclerview shows courses as course_item objects
    }
    private void listCourses(ArrayList<Course> cl){
        courselist = cl;
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

        // initializing variables
        ArrayList<Course> courseArrayList = new ArrayList<>();
        DBHandlerCourses dbHandler = new DBHandlerCourses(this);


        // getting the arraylist of products from MyDBHandler class
        dbHandler.listCourses(new FirebaseCallBackCourses() {
                                                     @Override
                                                     public void onCallBackCourseList(ArrayList<Course> courseList) {
                                                         //.listCourses(courseList);
                                                     }

                                                     @Override
                                                     public void onCallBackCourse(Course course) {

                                                     }
                                                 });

                // here we pass the ArrayList to our adapter class
                CourseAdapter courseAdapter = new CourseAdapter(courseArrayList, this);

//        // my recyclerview is idProductDisplay in the activity_display_product.xml file
        RecyclerView courseRV = findViewById(R.id);

        // layout manager positions items within our recyclerview
        // using a vertical recyclerview (other option is horizontal)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        courseRV.setLayoutManager(linearLayoutManager);

        // attaching the adapter to the recyclerview
        courseRV.setAdapter(courseAdapter);



    }
}
