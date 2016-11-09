package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;
import android.widget.ListView;

import info.hoang8f.widget.FButton;


public class CourseSelectViewModel extends BaseObservable {
    private ListView previousCourseList;
//    private Button newCourseButton;
    private FButton beginRoundButton;
    private FButton newCourseButton;

    private int visibility;

    public CourseSelectViewModel() {

    }

    public FButton getBeginRoundButton() {
        return beginRoundButton;
    }

    public void setBeginRoundButton(FButton beginRoundButton) {
        this.beginRoundButton = beginRoundButton;
    }

    public FButton getNewCourseButton() {
        return newCourseButton;
    }

    public void setNewCourseButton(FButton newCourseButton) {
        this.newCourseButton = newCourseButton;
    }

    public ListView getPreviousCourseList() {
        return previousCourseList;
    }

    public void setPreviousCourseList(ListView previousCourseList) {
        this.previousCourseList = previousCourseList;
    }
}





