package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;
import android.widget.ProgressBar;


public class ProfileViewModel extends BaseObservable {
    private String totalStrokes, roundsPlayed, scoringAverage;

    private ProgressBar a, b, c;

    public ProfileViewModel() {

    }

    public ProgressBar getA() {
        return a;
    }

    public void setA(ProgressBar a) {
        this.a = a;
    }

    public ProgressBar getB() {
        return b;
    }

    public void setB(ProgressBar b) {
        this.b = b;
    }

    public ProgressBar getC() {
        return c;
    }

    public void setC(ProgressBar c) {
        this.c = c;
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





