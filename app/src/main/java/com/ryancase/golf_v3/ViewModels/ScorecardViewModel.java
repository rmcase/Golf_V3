package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;

import java.util.List;


public class ScorecardViewModel extends BaseObservable {
    private String score, scoreToPar, putts;
    private String greens, pars, birdies;
    private List<String> scores;

    public ScorecardViewModel() {

    }

    public String getGreens() {
        return greens;
    }

    public void setGreens(String greens) {
        this.greens = greens;
    }

    public String getPars() {
        return pars;
    }

    public void setPars(String pars) {
        this.pars = pars;
    }

    public String getBirdies() {
        return birdies;
    }

    public void setBirdies(String birdies) {
        this.birdies = birdies;
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





