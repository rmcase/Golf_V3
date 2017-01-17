package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;
import android.graphics.Color;


public class OverviewViewModel extends BaseObservable {
    private String score, scoreToPar, par;
    private String frontScore, backScore;
    private String eagles, pars, birdies;
    private int textColor;

    public OverviewViewModel() {

    }

    public String getEagles() {
        return eagles;
    }

    public void setEagles(String eagles) {
        this.eagles = eagles;
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

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
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





