package com.ryancase.golf_v3.ViewModels;

import java.util.List;

/**
 * File description here...
 */

public class CourseSelectViewModel {
    private List<String> courseList;

    public CourseSelectViewModel() {

    }

    public List<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<String> courseList) {
        this.courseList = courseList;
    }
}
