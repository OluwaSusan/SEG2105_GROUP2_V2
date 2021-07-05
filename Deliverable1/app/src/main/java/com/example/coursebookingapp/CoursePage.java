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
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CoursePage extends Activity {

    EditText description, capacity, mon_time, tues_time, wed_time, thurs_time, fri_time;
    TextView coursecode, coursename, instructor;
    String description_og, assignedInstructor_username, assignedInstructor_fullname;
    String capacity_og;
    DBHandlerCourses dbCourses;
    String specificCourse;
    String userCurrent_fullname, getUserCurrent_username;
    Button un_assign_enroll, homeBtn_coursepage, backBtn_coursepage, saveBtn, editBtn;
    private FirebaseAuth fAuth;
    private FirebaseDatabase realDatabase;
    private ProgressBar loading;
    private UserType userType = UserType.INSTRUCTOR;
    private boolean userEnr = false;
    private boolean studEnr = false;
    protected Course sCourse;
    protected HashMap<String, String> students;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullcoursepage);

        description = findViewById(R.id.description_coursepage);
        capacity = findViewById(R.id.capacity_coursepage);
        coursecode = findViewById(R.id.coursecode_coursepage);
        coursename = findViewById(R.id.coursename_coursepage);
        instructor = findViewById(R.id.instructor_coursepage);
        un_assign_enroll = findViewById(R.id.un_assign_enroll_btn);
        homeBtn_coursepage = findViewById(R.id.homeBtn_coursepage);
        backBtn_coursepage = findViewById(R.id.bckbtn_coursepage);
        loading = findViewById(R.id.loading_coursepage);
        saveBtn = findViewById(R.id.save_coursepage);
        editBtn = findViewById(R.id.edit_courepage);
//      err_mssg = findViewById(R.id.error_coursepage);
        mon_time = findViewById(R.id.mon_time);
        tues_time = findViewById(R.id.tues_time);
        wed_time = findViewById(R.id.wed_time);
        thurs_time = findViewById(R.id.thurs_time);
        fri_time = findViewById(R.id.fri_time);
        dbCourses = new DBHandlerCourses();
        sCourse = new Course();
        students = new HashMap<>();

        //Show page as loading
        loading.setVisibility(View.VISIBLE);

        currentUser();
        checkExtra(savedInstanceState);
        //currentUser();


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

        un_assign_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String task = un_assign_enroll.getText().toString();

                if (task.equals("ASSIGN")) {
                    //create a new course of the info including instructor
                    Course course_assign = new Course(coursename.getText().toString(), coursecode.getText().toString());
                    course_assign.setInstructor(getUserCurrent_username);
                    course_assign.setStudents(students);

                    //delete old course and readd with new instructor
                    dbCourses.deleteCourse(coursecode.getText().toString());
                    dbCourses.addCourse(course_assign);

                    //update fields and view
                    assignedInstructor_fullname = userCurrent_fullname;
                    assignedInstructor_username = getUserCurrent_username;
                    instructor.setText(assignedInstructor_fullname);

                    //reset view based on new instructor
                    setViewBasedOnUser();
                    Toast toast = Toast.makeText(getApplicationContext(), "To Add information click edit and save the information", Toast.LENGTH_SHORT);
                    toast.show();

                } else if (task.equals("UNASSIGN")) {
                    //create a new course with course info minus details
                    Course course_unassign = new Course(coursename.getText().toString(), coursecode.getText().toString());
                    course_unassign.setStudents(students);

                    //delete old course and add back original course without details
                    dbCourses.deleteCourse(coursecode.getText().toString());
                    dbCourses.addCourse(course_unassign);

                    //if an instructor unassign themselves does student list delete?

                    //update assigned instructor fields and view to match
                    assignedInstructor_username = "";
                    assignedInstructor_fullname = "";
                    description.setText((CharSequence) null);
                    capacity.setText((CharSequence) null);
                    instructor.setText((CharSequence) null);
                    mon_time.setText((CharSequence) null);
                    tues_time.setText((CharSequence) null);
                    wed_time.setText((CharSequence) null);
                    thurs_time.setText((CharSequence) null);
                    fri_time.setText((CharSequence) null);

                    //update view
                    setViewBasedOnUser();
                }

                else if(task.equals("ENROLL")){
                    students.put(getUserCurrent_username, userCurrent_fullname);
                    dbCourses.updateStudentEnrolled(coursecode.getText().toString(), students);
                    userEnr = true;
                    setViewBasedOnUser();

                    //Create a new course with course info minus details

                }
                else if(task.equals("UNENROLL")){
                    students.remove(getUserCurrent_username);
                    dbCourses.updateStudentEnrolled(coursecode.getText().toString(), students);
                    userEnr = false;
                    setViewBasedOnUser();
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

                if (!validation()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid fields will not be accepted, course details not saved", Toast.LENGTH_SHORT);
                    toast.show();
                    description.setText((CharSequence) null);
                    capacity.setText((CharSequence) null);
                } else {
                    updated.setDescription(description.getText().toString());
                    updated.setCapacity(capacity.getText().toString());
                }


                HashMap<String, String> dates = new HashMap<>();

                if (!mon_time.getText().toString().isEmpty() && validateTimes("monday", mon_time.getText().toString().replaceAll(" ", ""))) {

                    dates.put("Monday", mon_time.getText().toString().replaceAll(" ", ""));
                }

                if (!tues_time.getText().toString().isEmpty() && validateTimes("tuesday", tues_time.getText().toString().replaceAll(" ", ""))) {
                    dates.put("Tuesday", tues_time.getText().toString().replaceAll(" ", ""));
                }

                if (!wed_time.getText().toString().isEmpty() && validateTimes("wednesday", wed_time.getText().toString().replaceAll(" ", ""))) {

                    dates.put("Wednesday", wed_time.getText().toString().replaceAll(" ", ""));
                }
                if (!thurs_time.getText().toString().isEmpty() && validateTimes("thursday", thurs_time.getText().toString().replaceAll(" ", ""))) {

                    dates.put("Thursday", thurs_time.getText().toString().replaceAll(" ", ""));
                }
                if (!fri_time.getText().toString().isEmpty() && validateTimes("friday", fri_time.getText().toString().replaceAll(" ", ""))) {
                    dates.put("Friday", fri_time.getText().toString().replaceAll(" ", ""));
                }

                updated.setStudents(students);
                updated.setDates(dates);
                updated.setInstructor(assignedInstructor_username);
                dbCourses.updateCourse(updated.getCourseCode(), updated);
            }
        });

    }

    private void setViewBasedOnUser() {

        Log.i("Current User", "SetView: " + getUserCurrent_username );
        Log.i("Assigned", "SetView: " + assignedInstructor_username);
        Log.i("User type", "Set View: " + userType);

        //We have class variables that store currentUser username and assignedInstructor username for comparison, since usernames are unique
        if (userType == UserType.INSTRUCTOR){

            if (Strings.isEmptyOrWhitespace(assignedInstructor_username)) {
                un_assign_enroll.setText("ASSIGN");
                un_assign_enroll.setVisibility(View.VISIBLE);
                un_assign_enroll.setClickable(true);

                editBtn.setVisibility(View.INVISIBLE);
                editBtn.setClickable(false);
                saveBtn.setVisibility(View.INVISIBLE);
                saveBtn.setClickable(false);

            } else if (getUserCurrent_username.equals(assignedInstructor_username)) {
                un_assign_enroll.setText("UNASSIGN");
                un_assign_enroll.setVisibility(View.VISIBLE);
                un_assign_enroll.setClickable(true);

                editBtn.setVisibility(View.VISIBLE);
                editBtn.setClickable(true);
                saveBtn.setVisibility(View.VISIBLE);
                saveBtn.setClickable(true);
            } else {
                un_assign_enroll.setVisibility(View.INVISIBLE);
                un_assign_enroll.setClickable(false);
                editBtn.setVisibility(View.INVISIBLE);
                editBtn.setClickable(false);
                saveBtn.setVisibility(View.INVISIBLE);
                saveBtn.setClickable(false);
            }
        }
        else if (userType == UserType.STUDENT){

            editBtn.setVisibility(View.INVISIBLE);
            editBtn.setClickable(false);
            saveBtn.setVisibility(View.INVISIBLE);
            saveBtn.setClickable(false);

            //no students enrolled
            if(!studEnr){
                un_assign_enroll.setText("ENROLL");
                un_assign_enroll.setVisibility(View.VISIBLE);
                un_assign_enroll.setClickable(true);
            }
            //User not enrolled
            if(!userEnr){
                un_assign_enroll.setText("ENROLL");
                un_assign_enroll.setVisibility(View.VISIBLE);
                un_assign_enroll.setClickable(true);
            }
            else if(userEnr){
                un_assign_enroll.setText("UNENROLL");
                un_assign_enroll.setVisibility(View.VISIBLE);
                un_assign_enroll.setClickable(true);
            }
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

                        if (Strings.isEmptyOrWhitespace(course.getCapacity())) {
                            capacity.setText((CharSequence) null);
                        } else {
                            capacity.setText(course.getCapacity());
                            capacity_og = course.getCapacity();
                        }


                        if (Strings.isEmptyOrWhitespace(course.getInstructor())) {
                            instructor.setText((CharSequence) null);
                        } else {

                            DBHandlerUsers db = new DBHandlerUsers();
                            db.findUser(course.getInstructor(), new FirebaseCallBackUsers() {
                                @Override
                                public void onCallBackUsersList(ArrayList<User> userList) {

                                }

                                @Override
                                public void onCallBackUser(User user) {
                                    instructor.setText(user.getFullName());
                                    //assignedInstructor_username = user.getUserName();
                                }
                            });
                            assignedInstructor_username = course.getInstructor();
                            Log.i("Assigned", "After onCallBackCourse: " + assignedInstructor_username);
                            setViewBasedOnUser();
                        }

                        if (course.getDates().size() < 1) {

                        } else {

                            for (String day : course.getDates().keySet()) {

                                if (day.equals("Monday")) {
                                    mon_time.setText(course.getDates().get(day));
                                }
                                if (day.equals("Tuesday")) {
                                    tues_time.setText(course.getDates().get(day));
                                }
                                if (day.equals("Wednesday")) {
                                    wed_time.setText(course.getDates().get(day));
                                }
                                if (day.equals("Thursday")) {
                                    thurs_time.setText(course.getDates().get(day));
                                }
                                if (day.equals("Friday")) {
                                    fri_time.setText(course.getDates().get(day));
                                }

                            }
                        }

                        if(!course.getStudents().isEmpty()){
                            studEnr = true;
                            students = course.getStudents();

                            if(course.getStudents().containsKey(getUserCurrent_username)){
                                userEnr = true;
                            }else{userEnr = false;}

                        }else {
                            studEnr = false;
                            userEnr = false;
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
                userType = user.getUserType();

                Log.i("Current User", "onCallBackUser Username: " + getUserCurrent_username );
                Log.i("Current User", "User Type" + userType);

                //setViewBasedOnUser();
            }
        });

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

    public boolean validation() {

        int cap = Integer.parseInt(capacity.getText().toString());

        if (description.getText().toString().length() > 195) {
            Toast toast = Toast.makeText(getApplicationContext(), "Description exceeds maximum characterrs of 195, please re-edit", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (cap < 1) {
            Toast toast = Toast.makeText(getApplicationContext(), "Capacity is invalid, please re-edit a valid capacity", Toast.LENGTH_SHORT);
            toast.show();
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

        if (Integer.parseInt(times[0].split(":")[0]) > 11 || Integer.parseInt(times[1].split(":")[0]) > 12) {
            showTimeValidationToast(day);
            return false;
        }

        int[] hours = new int[2];
        int[] minutes = new int[2];
        int[] halfTime = new int[2]; // 0 is am 1 is pm

        for (int i = 0; i < 2; i++) {

            if (!(times[i].contains("pm") || times[i].contains("am") || times[i].contains(":"))) {

                showTimeValidationToast(day);
                return false;
            }

            if (times[i].contains("pm")) {
                halfTime[i] = 1;
            }

            if (Integer.parseInt(times[i].split(":")[1].replaceAll("am", "").replaceAll("pm", "")) > 59) {

                showTimeValidationToast(day);
                return false;
            }

            hours[i] = Integer.parseInt(times[i].split(":")[0]);
            minutes[i] = Integer.parseInt(times[i].split(":")[1].replaceAll("am", "").replaceAll("pm", ""));
        }

        // first time is after in the same half of the day
        if (hours[0] > hours[1] && halfTime[0] == halfTime[1]) {
            showTimeValidationToast(day);

            return false;

        }

        if (hours[0] == hours[1] && minutes[0] > minutes[1] && halfTime[0] == halfTime[1]) {
            showTimeValidationToast(day);

            return false;

        }
        // pm to am classes are not valid
        if (halfTime[0] > halfTime[1]) {
            showTimeValidationToast(day);
            return false;
        }

        return true;

    }

    private void showTimeValidationToast(String day) {
        Toast toast = Toast.makeText(getApplicationContext(), "The time entered for " + day + " is invalid", Toast.LENGTH_SHORT);
        toast.show();
    }

    public boolean description_validation_test(String description) {


        if (description.length() > 195) {
            return false;
        }
        return true;
    }



}
