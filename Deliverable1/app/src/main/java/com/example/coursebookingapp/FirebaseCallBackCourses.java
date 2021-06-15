package com.example.coursebookingapp;

import java.util.ArrayList;

public interface FirebaseCallBackCourses {
    void onCallBackCourseList(ArrayList<Course> courseList);
    void onCallBackCourse(Course course);
}

