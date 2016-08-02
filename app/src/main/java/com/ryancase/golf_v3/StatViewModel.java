package com.ryancase.golf_v3;

import android.databinding.BaseObservable;


public class StatViewModel extends BaseObservable {
    private int score;
    private int par;
    private String title;

    public StatViewModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}





