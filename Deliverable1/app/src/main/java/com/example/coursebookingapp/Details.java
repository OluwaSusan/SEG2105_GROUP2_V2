package com.example.coursebookingapp;

import java.util.Objects;

public class Details {


    private int cap;
    private String desc;
    private Date date;

    public Details(){}

    public Details(String courseName, String courseCode) {


    }


    public int getCap(){ return cap;}

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getDesc(){return desc;}

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Details course = (Details) o;
        return course.date.equals(date) && course.cap == cap && course.desc.equals(desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cap, desc, date);
    }

}
