package com.example.coursebookingapp;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class User {

    private String userName;
    private String fullName;
    private String password;
    private UserType userType;
    private String email;
    private ArrayList<Course> courses;
    private HashMap<String, String> myCourses = new HashMap<>();


    public User(){

    }

    public User(String userName, String fullName, String password, UserType userType, String email) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.userType = userType;
        this.email = email;
        this.courses = new ArrayList<Course>();
    }

    public User(String userName, String fullName, String password, UserType userType, String email, ArrayList<Course> courses) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.userType = userType;
        this.email = email;
        this.courses = courses;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public HashMap<String, String> getMyCourses() { return myCourses; }

    public void setMyCourses(HashMap<String, String> myCourses) { this.myCourses = myCourses; }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", courses=" + Arrays.toString(courses.toArray()) +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
