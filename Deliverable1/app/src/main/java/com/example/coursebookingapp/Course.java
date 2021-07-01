package com.example.coursebookingapp;

import java.util.Objects;

public class Course {

    private String courseName;
    private String courseCode;
    private Instructor instructor;
    private Details details;

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

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Instructor getInstructor() {
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

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}
