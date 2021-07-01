package com.example.coursebookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class  CoursePage extends Activity {

    EditText description, capacity;
    TextView coursecode, coursename, instructor;
    String description_og, assignedInstructor_username, assignedInstructor_fullname;
    String capacity_og;
    DBHandlerCourses dbCourses;
    String specificCourse;
    String userCurrent_fullname, getUserCurrent_username;
    Button assign_unassign, homeBtn_coursepage, backBtn_coursepage;
    private FirebaseAuth fAuth;
    private FirebaseDatabase realDatabase;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullcoursepage);

        description = findViewById(R.id.description_coursepage);
        capacity = findViewById(R.id.capacity_coursepage);
        coursecode = findViewById(R.id.coursecode_coursepage);
        coursename = findViewById(R.id.coursename_coursepage);
        instructor = findViewById(R.id.instructor_coursepage);
        assign_unassign = findViewById(R.id.assign_unassign_btn);
        homeBtn_coursepage = findViewById(R.id.homeBtn_coursepage);
        backBtn_coursepage = findViewById(R.id.bckbtn_coursepage);
        loading = findViewById(R.id.loading_coursepage);
        dbCourses = new DBHandlerCourses();

        //Show page as loading
        loading.setVisibility(View.VISIBLE);

        checkExtra(savedInstanceState);
        currentUser();


        homeBtn_coursepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivityWelcome.class));
            }
        });

        backBtn_coursepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Instructor.class));
            }
        });

        assign_unassign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String task = assign_unassign.getText().toString();

                if (task.equals("ASSIGN")){
                    //call dbHandlerCourses assign method
                    //or delete original and add new
                }
                else if (task.equals("UNASSIGN")){
                    //call dbHandlerCourses unassign method
                    //or delete original and add new
                }
            }
        });

    }

    private void setViewBasedOnInstructor() {

        //We have class variables that store currentUser username and assignedInstructor username for comparison, since usernames are unique
        if (assignedInstructor_username.isEmpty()){
            assign_unassign.setText("ASSIGN");
            assign_unassign.setVisibility(View.VISIBLE);
            assign_unassign.setClickable(true);
        }
        else if (getUserCurrent_username.equals(assignedInstructor_username)){
            assign_unassign.setText("UNASSIGN");
            assign_unassign.setVisibility(View.VISIBLE);
            assign_unassign.setClickable(true);
        }
        else{
            assign_unassign.setVisibility(View.INVISIBLE);
            assign_unassign.setClickable(false);
        }
        //Page finished loading
        loading.setVisibility(View.INVISIBLE);
    }


    private void checkExtra(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                specificCourse= null;
            } else {
                specificCourse= extras.getString("Course_ID");

                dbCourses.findCourse(specificCourse, new FirebaseCallBackCourses() {
                    @Override
                    public void onCallBackCourseList(ArrayList<Course> courseList) {

                    }

                    @Override
                    public void onCallBackCourse(Course course) {
                        coursename.setText(course.getCourseName());
                        coursecode.setText(course.getCourseCode());

                        //if coded correctly if description is empty then all other fields are empty too
                        if (Strings.isEmptyOrWhitespace(course.getDescription())){
                            description.setText((CharSequence)null);
                            capacity.setText((CharSequence)null);
                        }
                        else {
                            description.setText(course.getDescription());
                            description_og = course.getDescription();
                            capacity.setText(course.getCapacity());
                            capacity_og = course.getCapacity();
                        }

                        if (Strings.isEmptyOrWhitespace(course.getInstructor())){
                            instructor.setText((CharSequence)null);
                        }
                        else {
                            instructor.setText(assignedInstructor(course.getInstructor()));
                            assignedInstructor_username = course.getInstructor();

                        }
                    }
                });

            }
        } else {
            specificCourse= (String) savedInstanceState.getSerializable("Course_ID");
        }
    }

    public void currentUser(){

        fAuth = FirebaseAuth.getInstance();
        realDatabase = FirebaseDatabase.getInstance();

        String email = fAuth.getCurrentUser().getEmail();
        String[] parts = email.split("@");
        String username = parts[0];
        Log.i("test" , "username " + username);


        DBHandlerUsers db = new DBHandlerUsers();

        db.findUser(username, new FirebaseCallBackUsers() {
            @Override
            public void onCallBackUsersList(ArrayList<User> userList) {

            }

            @Override
            public void onCallBackUser(User user) {
                userCurrent_fullname = user.getFullName();
                getUserCurrent_username = user.getUserName();
                setViewBasedOnInstructor();
            }
        });

    }

    public String assignedInstructor(String username){
        DBHandlerUsers db = new DBHandlerUsers();

        db.findUser(username, new FirebaseCallBackUsers() {
            @Override
            public void onCallBackUsersList(ArrayList<User> userList) {

            }

            @Override
            public void onCallBackUser(User user) {
                assignedInstructor_fullname = user.getFullName();
            }
        });

        return assignedInstructor_fullname;
    }

}
