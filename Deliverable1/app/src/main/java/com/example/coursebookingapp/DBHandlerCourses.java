package com.example.coursebookingapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DBHandlerCourses {

    private final DatabaseReference courseReference;


    public DBHandlerCourses() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        courseReference = rootNode.getReference("courses");
    }

    public void addCourse(Course course) {
        courseReference.child(course.getCourseCode()).setValue(course);
    }

    public void findCourse(String courseCode, FirebaseCallBackCourses callBack) {


        Query query = courseReference.orderByChild("courseCode").equalTo(courseCode);
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

    public void findByName(String courseName, FirebaseCallBackCourses callBack) {


        Query query = courseReference.orderByChild("courseName").equalTo(courseName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                if (snapshot.exists()) {


                    Course dbCourse = snapshot.getChildren().iterator().next().getValue(Course.class);
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
    public void findByDay(String day, FirebaseCallBackCourses callBack) {


        Query query = courseReference.orderByChild("courseName");

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    Iterator<DataSnapshot> it = snapshot.getChildren().iterator();
                    while (it.hasNext()){

                        Course c = it.next().getValue(Course.class);;
                        if (c.getDates().containsKey(day)){
                            callBack.onCallBackCourse(c);
                            return;
                        }
                    }
                }
                callBack.onCallBackCourse(new Course());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }


        });


    }

    public void searchCourse(String query, FirebaseCallBackCourses callBack) {

        findCourse(query, new FirebaseCallBackCourses() {
                    @Override
                    public void onCallBackCourseList(ArrayList<Course> courseList) {
                        callBack.onCallBackCourseList(courseList);
                    }

                    @Override
                    public void onCallBackCourse(Course course) {
                        if(Strings.isEmptyOrWhitespace(course.getCourseCode())) {
                            //No result by ID, try by name
                            findByName(query, new FirebaseCallBackCourses() {
                                @Override
                                public void onCallBackCourseList(ArrayList<Course> courseList) {
                                    callBack.onCallBackCourseList(courseList);
                                }

                                @Override
                                public void onCallBackCourse(Course course) {
                                    if(Strings.isEmptyOrWhitespace(course.getCourseCode())){
                                        findByDay(query, new FirebaseCallBackCourses() {
                                            @Override
                                            public void onCallBackCourseList(ArrayList<Course> courseList) {
                                                callBack.onCallBackCourseList(courseList);
                                            }

                                            @Override
                                            public void onCallBackCourse(Course course) {
                                                callBack.onCallBackCourse(course);
                                            }
                                        });
                                    }
                                    else{
                                        callBack.onCallBackCourse(course);
                                    }

                                }
                            });
                            return;
                        }
                        callBack.onCallBackCourse(course);
                    }
                });
    }

    public void listCourses(FirebaseCallBackCourses callBack) {
        ArrayList<Course> courseList = new ArrayList<Course>();

        courseReference
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

        courseReference.child(courseCode).removeValue();


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

        courseReference.child(courseCode).child("courseName").setValue(updatedCourseName);

    }

    public void updateStudentEnrolled(String courseCode, HashMap<String, String> students) {

        courseReference.child(courseCode).child("students").setValue(students);

    }

    public void updateCourse(String courseCode, Course updatedCourse){

        deleteCourse(courseCode);
        addCourse(updatedCourse);

    }

//    public void assignCourse(String courseCode, String instructor){
//        courseRefrence.child(courseCode).child("instructor").setValue(instructor);
//    }
//
//    public void unassignCourse(String courseCode){
//        courseRefrence.child(courseCode).child("instructor").removeValue();
//
//    }


}
