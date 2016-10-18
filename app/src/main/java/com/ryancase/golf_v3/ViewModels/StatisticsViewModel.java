package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;


public class StatisticsViewModel extends BaseObservable {
    private String average, max, min;

    public StatisticsViewModel() {

    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}





