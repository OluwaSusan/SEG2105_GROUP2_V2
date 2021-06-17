package com.example.coursebookingapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class DBHandlerCourses {

    private final DatabaseReference courseRefrence;


    public DBHandlerCourses() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        courseRefrence = rootNode.getReference("courses");
    }

    public void addCourse(Course course) {
        courseRefrence.child(course.getCourseCode()).setValue(course);
    }

    public void findCourse(String courseCode, FirebaseCallBackCourses callBack) {


        Query query = courseRefrence.orderByChild("courseCode").equalTo(courseCode);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                if (snapshot.exists()) {


                    Course dbCourse = snapshot.child(courseCode).getValue(Course.class);
                    callBack.onCallBackCourse(dbCourse);

                    //Log.i("test", "reaches inside the find function ");

                } else {
                    callBack.onCallBackCourse(new Course());
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                //Log.i("test", "reaches here");

            }


        });


    }

    public void listCourses(FirebaseCallBackCourses callBack) {
        ArrayList<Course> courseList = new ArrayList<Course>();

        courseRefrence
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Course course = snapshot.getValue(Course.class);

                            //Log.i("test", "user inside loop " + course);
                            courseList.add(course);

                        }

                        callBack.onCallBackCourseList(courseList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }

    public void deleteCourse(String courseCode) {

        courseRefrence.child(courseCode).removeValue();


    }

    public void updateCourseCode(String currentCourseCode, String updatedCourseCode) {

        findCourse(currentCourseCode, new FirebaseCallBackCourses() {
            @Override
            public void onCallBackCourseList(ArrayList<Course> courseList) {

            }

            @Override
            public void onCallBackCourse(Course course) {

                Log.i("test", "reaches the reset "+ course.getCourseCode());


                if (course.getCourseCode() != null) {


                    deleteCourse(currentCourseCode);
                    course.setCourseCode(updatedCourseCode);
                    Log.i("test", "reaches the reset");
                    addCourse(course);


                }else{
                    Log.i("test", "does not reach the reset");
                }

            }
        });


    }

    public void updateCourseName(String courseCode, String updatedCourseName) {

        courseRefrence.child(courseCode).child("courseName").setValue(updatedCourseName);


    }


}
