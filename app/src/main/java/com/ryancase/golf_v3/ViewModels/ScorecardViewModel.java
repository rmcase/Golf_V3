package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;

import java.util.List;


public class ScorecardViewModel extends BaseObservable {
    private String score, scoreToPar, putts;
    private List<String> scores;

    public ScorecardViewModel() {

    }

    public List<String> getScores() {
        return scores;
    }

    public void setScores(List<String> scores) {
        this.scores = scores;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoreToPar() {
        return scoreToPar;
    }

    public void setScoreToPar(String scoreToPar) {
        this.scoreToPar = scoreToPar;
    }

    public String getPutts() {
        return putts;
    }

    public void setPutts(String putts) {
        this.putts = putts;
    }
}





