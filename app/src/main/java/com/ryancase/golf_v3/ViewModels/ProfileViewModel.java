package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;
import android.widget.ProgressBar;


public class ProfileViewModel extends BaseObservable {
    private String totalStrokes, roundsPlayed, scoringAverage;

    private int textColor;

    public ProfileViewModel() {

    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public String getTotalStrokes() {
        return totalStrokes;
    }

    public void setTotalStrokes(String totalStrokes) {
        this.totalStrokes = totalStrokes;
    }

    public String getRoundsPlayed() {
        return roundsPlayed;
    }

    public void setRoundsPlayed(String roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public String getScoringAverage() {
        return scoringAverage;
    }

    public void setScoringAverage(String scoringAverage) {
        this.scoringAverage = scoringAverage;
    }
}





