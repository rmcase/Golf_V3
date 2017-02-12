package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;
import android.widget.ProgressBar;


public class ProfileViewModel extends BaseObservable {
    private String totalStrokes, roundsPlayed, scoringAverage;

    public ProfileViewModel() {

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





