package com.example.coursebookingapp;


public class User {

    private String userName;
    private String fullName;
    private String password;
    private UserType userType;
    private String email;

    public User() {

    }

    public User(String userName, String fullName, String password, UserType userType, String email) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.userType = userType;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}