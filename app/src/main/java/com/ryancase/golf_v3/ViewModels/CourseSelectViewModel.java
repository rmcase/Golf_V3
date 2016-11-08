package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;
import android.widget.Button;
import android.widget.ListView;


public class CourseSelectViewModel extends BaseObservable {
    private ListView previousCourseList;
    private Button newCourseButton;

    private int visibility;

    public CourseSelectViewModel() {

    }

    public Button getNewCourseButton() {
        return newCourseButton;
    }

    public void setNewCourseButton(Button newCourseButton) {
        this.newCourseButton = newCourseButton;
    }

    public ListView getPreviousCourseList() {
        return previousCourseList;
    }

    public void setPreviousCourseList(ListView previousCourseList) {
        this.previousCourseList = previousCourseList;
    }
}





