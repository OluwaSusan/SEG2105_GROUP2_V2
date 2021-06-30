package com.example.coursebookingapp;

import java.util.Objects;

public class Date {

    private String days;
    private String hours;

    public Date(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date course = (Date) o;
        return Objects.equals(days, course.days) &&
                Objects.equals(hours, course.hours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(days, hours);
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
