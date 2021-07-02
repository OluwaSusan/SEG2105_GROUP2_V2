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
import java.util.HashMap;

public class CoursePage extends Activity {

    EditText description, capacity, mon_time, tues_time, wed_time, thurs_time, fri_time;
    TextView coursecode, coursename, instructor, err_mssg;
    String description_og, assignedInstructor_username, assignedInstructor_fullname;
    String capacity_og;
    DBHandlerCourses dbCourses;
    String specificCourse;
    String userCurrent_fullname, getUserCurrent_username;
    Button assign_unassign, homeBtn_coursepage, backBtn_coursepage, saveBtn, editBtn;
    private FirebaseAuth fAuth;
    private FirebaseDatabase realDatabase;
    private ProgressBar loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        saveBtn = findViewById(R.id.save_coursepage);
        editBtn = findViewById(R.id.edit_courepage);
        err_mssg = findViewById(R.id.error_coursepage);
        mon_time = findViewById(R.id.mon_time);
        tues_time = findViewById(R.id.tues_time);
        wed_time = findViewById(R.id.wed_time);
        thurs_time = findViewById(R.id.thurs_time);
        fri_time = findViewById(R.id.fri_time);
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

                if (task.equals("ASSIGN")) {
                    //create a new course of the info including instructor
                    Course course_assign = new Course(coursename.getText().toString(), coursecode.getText().toString());
                    course_assign.setInstructor(getUserCurrent_username);

                    //delete old course and readd with new instructor
                    dbCourses.deleteCourse(coursecode.getText().toString());
                    dbCourses.addCourse(course_assign);

                    //update fields and view
                    assignedInstructor_fullname = userCurrent_fullname;
                    assignedInstructor_username = getUserCurrent_username;
                    instructor.setText(assignedInstructor_fullname);

                    //reset view based on new instructor
                    setViewBasedOnInstructor();
                    err_mssg.setText("To Add information click edit and save the information");

                } else if (task.equals("UNASSIGN")) {
                    //create a new course with course info minus details
                    Course course_unassign = new Course(coursename.getText().toString(), coursecode.getText().toString());

                    //delete old course and add back original course without details
                    dbCourses.deleteCourse(coursecode.getText().toString());
                    dbCourses.addCourse(course_unassign);

                    //update assigned instructor fields and view to match
                    assignedInstructor_username = "";
                    assignedInstructor_fullname = "";
                    description.setText((CharSequence) null);
                    capacity.setText((CharSequence) null);
                    instructor.setText((CharSequence) null);

                    //update view
                    setViewBasedOnInstructor();
                }
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableFields(1);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableFields(2);

                Course updated = new Course(coursename.getText().toString(), coursecode.getText().toString());

                if(!validation()){
                    err_mssg.setText("Invalid fields will not be accepted, course details not saved");
                }
                else{
                    updated.setDescription(description.getText().toString());
                    updated.setCapacity(capacity.getText().toString());
                }


                HashMap<String, String> dates = new HashMap<>();

                if(!mon_time.getText().toString().isEmpty() && validateTimes("monday", mon_time.getText().toString().replaceAll(" ", ""))){

                    dates.put("Monday",mon_time.getText().toString().replaceAll(" ", ""));

                }
                if(!tues_time.getText().toString().isEmpty() && validateTimes("tuesday", tues_time.getText().toString().replaceAll(" ", ""))){
                    dates.put("Tuesday",tues_time.getText().toString().replaceAll(" ", ""));

                }
                if(!wed_time.getText().toString().isEmpty() && validateTimes("wednesday", wed_time.getText().toString().replaceAll(" ", ""))){

                    dates.put("Wednesday",wed_time.getText().toString().replaceAll(" ", ""));

                }
                if(!thurs_time.getText().toString().isEmpty() && validateTimes("thursday", thurs_time.getText().toString().replaceAll(" ", ""))){

                    dates.put("Thursday",thurs_time.getText().toString().replaceAll(" ", ""));

                }
                if(!fri_time.getText().toString().isEmpty() && validateTimes("friday", fri_time.getText().toString().replaceAll(" ", ""))){

                    dates.put("Friday",fri_time.getText().toString().replaceAll(" ", ""));

                }

                updated.setDates(dates);
                dbCourses.updateCourse(updated.getCourseCode(), updated);
            }
        });

    }

    private void setViewBasedOnInstructor() {

        //We have class variables that store currentUser username and assignedInstructor username for comparison, since usernames are unique
        if (Strings.isEmptyOrWhitespace(assignedInstructor_username)) {
            assign_unassign.setText("ASSIGN");
            assign_unassign.setVisibility(View.VISIBLE);
            assign_unassign.setClickable(true);

            editBtn.setVisibility(View.INVISIBLE);
            editBtn.setClickable(false);
            saveBtn.setVisibility(View.INVISIBLE);
            saveBtn.setClickable(false);
        } else if (getUserCurrent_username.equals(assignedInstructor_username)) {
            assign_unassign.setText("UNASSIGN");
            assign_unassign.setVisibility(View.VISIBLE);
            assign_unassign.setClickable(true);

            editBtn.setVisibility(View.VISIBLE);
            editBtn.setClickable(true);
            saveBtn.setVisibility(View.VISIBLE);
            saveBtn.setClickable(true);
        } else {
            assign_unassign.setVisibility(View.INVISIBLE);
            assign_unassign.setClickable(false);
            editBtn.setVisibility(View.INVISIBLE);
            editBtn.setClickable(false);
            saveBtn.setVisibility(View.INVISIBLE);
            saveBtn.setClickable(false);
        }
        //Page finished loading
        loading.setVisibility(View.INVISIBLE);
    }


    private void checkExtra(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                specificCourse = null;
            } else {
                specificCourse = extras.getString("Course_ID");

                dbCourses.findCourse(specificCourse, new FirebaseCallBackCourses() {
                    @Override
                    public void onCallBackCourseList(ArrayList<Course> courseList) {

                    }

                    @Override
                    public void onCallBackCourse(Course course) {
                        coursename.setText(course.getCourseName());
                        coursecode.setText(course.getCourseCode());

                        //if coded correctly if description is empty then all other fields are empty too
                        if (Strings.isEmptyOrWhitespace(course.getDescription())) {
                            description.setText((CharSequence) null);

                        } else {
                            description.setText(course.getDescription());
                            description_og = course.getDescription();

                        }

                        if(Strings.isEmptyOrWhitespace(course.getCapacity())){
                            capacity.setText((CharSequence) null);
                        }else{
                            capacity.setText(course.getCapacity());
                            capacity_og = course.getCapacity();
                        }

                        if (Strings.isEmptyOrWhitespace(course.getInstructor())) {
                            instructor.setText((CharSequence) null);
                        } else {
                            instructor.setText(assignedInstructor(course.getInstructor()));
                            instructor.setText(course.getInstructor());
                            assignedInstructor_username = course.getInstructor();

                        }

                        if(course.getDates().size() < 1){

                        }
                        else{
                            course.getDates().get("Monday").isEmpty()
                        }
                    }
                });

            }
        } else {
            specificCourse = (String) savedInstanceState.getSerializable("Course_ID");
        }
    }

    public void currentUser() {

        fAuth = FirebaseAuth.getInstance();
        realDatabase = FirebaseDatabase.getInstance();

        String email = fAuth.getCurrentUser().getEmail();
        String[] parts = email.split("@");
        String username = parts[0];
        Log.i("test", "username " + username);


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

    public String assignedInstructor(String username) {
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

    public void enableFields(int state) {
        if (state == 1) {
            description.setEnabled(true);
            capacity.setEnabled(true);
            mon_time.setEnabled(true);
            tues_time.setEnabled(true);
            wed_time.setEnabled(true);
            thurs_time.setEnabled(true);
            fri_time.setEnabled(true);
        } else {
            description.setEnabled(false);
            capacity.setEnabled(false);
            mon_time.setEnabled(false);
            tues_time.setEnabled(false);
            wed_time.setEnabled(false);
            thurs_time.setEnabled(false);
            fri_time.setEnabled(false);

        }
    }

    public boolean validation(){

        int cap = Integer.parseInt(capacity.getText().toString());

        if(description.getText().toString().length() > 195){
            err_mssg.setText("Description exceeds maximum characterrs of 195, please re-edit");
            return false;
        }
        if (cap < 1){
            err_mssg.setText("Capacity is invalid, please re-edit a valid capacity");
            return false;
        }

        return true;
    }

    private boolean validateTimes(String day, String input) {

        String[] times = input.split("-");

        if (times.length != 2) {
            System.out.println("-2");

            return false;
        }

        int[] hours = new int[2];
        int[] minutes = new int[2];
        int[] halfTime = new int[2]; // 0 is am 1 is pm

        for (int i = 0; i < 2; i++) {

            if (!(times[i].contains("pm") || times[i].contains("am") || times[i].contains(":"))) {

                err_mssg.setText("The time entered for " + day + " is invalid");
                return false;
            }

            if (times[i].contains("pm")) {
                halfTime[i] = 1;
            }

            if (Integer.parseInt(times[i].split(":")[0]) > 11
                    || Integer.parseInt(times[i].split(":")[1].replaceAll("am", "").replaceAll("pm", "")) > 59) {

                err_mssg.setText("The time entered for " + day + " is invalid");
                return false;
            }

            hours[i] = Integer.parseInt(times[i].split(":")[0]);
            minutes[i] = Integer.parseInt(times[i].split(":")[1].replaceAll("am", "").replaceAll("pm", ""));
        }

        // first time is after in the same half of the day
        if (hours[0] > hours[1] && halfTime[0] == halfTime[1]) {
            err_mssg.setText("The time entered for " + day + " is invalid");

            return false;

        }

        if (hours[0] == hours[1] && minutes[0] > minutes[1] && halfTime[0] == halfTime[1]) {
            err_mssg.setText("The time entered for " + day + " is invalid");

            return false;

        }
        // pm to am classes are not valid
        if (halfTime[0] > halfTime[1]) {
            err_mssg.setText("The time entered for " + day + " is invalid");

            return false;
        }

        return true;

    }





}
