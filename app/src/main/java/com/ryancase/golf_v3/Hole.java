package com.ryancase.golf_v3;

/**
 * File description here...
 */

public class Hole {
    private int score;
    private int par;
    private int fairway;
    private int green;
    private int putts;
    private int upAndDown;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int getFairway() {
        return fairway;
    }

    public void setFairway(int fairway) {
        this.fairway = fairway;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getPutts() {
        return putts;
    }

    public void setPutts(int putts) {
        this.putts = putts;
    }

    public int getUpAndDown() {
        return upAndDown;
    }

    public void setUpAndDown(int upAndDown) {
        this.upAndDown = upAndDown;
    }
}
