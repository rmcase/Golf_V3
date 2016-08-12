package com.ryancase.golf_v3.ViewModels;

/**
 * File description here...
 */

public class CourseSelectViewModel {
    private String title;
    private static String courseName;

    public CourseSelectViewModel() {

    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
