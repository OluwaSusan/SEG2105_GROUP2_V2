package com.example.coursebookingapp;

import java.util.Objects;

public class Instructor {

    private String fullName;
    private String email;

    public Instructor(){}


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instructor course = (Instructor) o;
        return Objects.equals(fullName, course.fullName) &&
                Objects.equals(email, course.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email);
    }
}
