package com.example.coursebookingapp;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Course {

    private String courseName;
    private String courseCode;
    private String capacity;
    private String description;
    private String instructor;


    public Course(){}

    public Course(String courseName, String courseCode) {
        this.courseName = courseName;
        this.courseCode = courseCode;

    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCapacity(){ return capacity;}

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDescription(){return description;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getInstructor() {
        return instructor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseName, course.courseName) &&
                Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, courseCode);
    }
}
