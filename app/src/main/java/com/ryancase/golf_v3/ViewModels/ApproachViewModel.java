package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;


public class ApproachViewModel extends BaseObservable {
    private String greens, ironRating, greenPercentage;
    private String scrambling, approachRating;

    public ApproachViewModel() {

    }

    public String getGreens() {
        return greens;
    }

    public void setGreens(String greens) {
        this.greens = greens;
    }

    public String getIronRating() {
        return ironRating;
    }

    public void setIronRating(String ironRating) {
        this.ironRating = ironRating;
    }

    public String getGreenPercentage() {
        return greenPercentage;
    }

    public void setGreenPercentage(String greenPercentage) {
        this.greenPercentage = greenPercentage;
    }

    public String getScrambling() {
        return scrambling;
    }

    public void setScrambling(String scrambling) {
        this.scrambling = scrambling;
    }

    public String getApproachRating() {
        return approachRating;
    }

    public void setApproachRating(String approachRating) {
        this.approachRating = approachRating;
    }
}





