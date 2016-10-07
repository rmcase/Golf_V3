package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;


public class DrivingViewModel extends BaseObservable {
    private String fairways, driverRating, fairwayPercentage;

    public DrivingViewModel() {

    }

    public String getFairways() {
        return fairways;
    }

    public void setFairways(String fairways) {
        this.fairways = fairways;
    }

    public String getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(String driverRating) {
        this.driverRating = driverRating;
    }

    public String getFairwayPercentage() {
        return fairwayPercentage;
    }

    public void setFairwayPercentage(String fairwayPercentage) {
        this.fairwayPercentage = fairwayPercentage;
    }
}





