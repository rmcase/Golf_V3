package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;


public class DialogViewModel extends BaseObservable {
    private String score, scoreToPar, putts;

    public DialogViewModel() {

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





