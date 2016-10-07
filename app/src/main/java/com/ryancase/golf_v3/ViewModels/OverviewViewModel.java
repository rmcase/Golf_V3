package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;


public class OverviewViewModel extends BaseObservable {
    private String score, scoreToPar, par;
    private String course, date;
    private String frontScore, backScore;

    public OverviewViewModel() {

    }

    public String getScoreToPar() {
        return scoreToPar;
    }

    public void setScoreToPar(String scoreToPar) {
        this.scoreToPar = scoreToPar;
    }

    public String getPar() {
        return par;
    }

    public void setPar(String par) {
        this.par = par;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBackScore() {
        return backScore;
    }

    public void setBackScore(String backScore) {
        this.backScore = backScore;
    }

    public String getFrontScore() {
        return frontScore;
    }

    public void setFrontScore(String frontScore) {
        this.frontScore = frontScore;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}





