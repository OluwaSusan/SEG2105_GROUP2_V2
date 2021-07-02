package com.example.coursebookingapp;

import java.util.HashMap;
import java.util.Objects;

public class Course {

    private String courseName;
    private String courseCode;
    private String instructor;
    private String capacity;
    private String description;
    private HashMap<String, String> dates = new HashMap<>();


    public Course(){}

    public Course(String courseName, String courseCode) {
        this.courseName = courseName;
        this.courseCode = courseCode;

    }

    public Course(String courseName, String courseCode, String instructor, String capacity, String description, HashMap<String, String> dates) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.instructor = instructor;
        this.capacity = capacity;
        this.description = description;
        this.dates = dates;
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

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getCapacity(){ return capacity;}

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDescription(){return description;}

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, String> getDates() {
        return dates;
    }

    public void setDates(HashMap<String, String> dates) {
        this.dates = dates;
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
