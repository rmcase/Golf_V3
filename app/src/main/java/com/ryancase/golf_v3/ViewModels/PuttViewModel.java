package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;

import com.github.mikephil.charting.charts.PieChart;


public class PuttViewModel extends BaseObservable {
    private String putts, puttRating, puttPercentage;
    private PieChart pieChart;

    public PuttViewModel() {

    }

    public String getPutts() {
        return putts;
    }

    public void setPutts(String putts) {
        this.putts = putts;
    }

    public String getPuttRating() {
        return puttRating;
    }

    public void setPuttRating(String puttRating) {
        this.puttRating = puttRating;
    }

    public String getPuttPercentage() {
        return puttPercentage;
    }

    public void setPuttPercentage(String puttPercentage) {
        this.puttPercentage = puttPercentage;
    }
}





