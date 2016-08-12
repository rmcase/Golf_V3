package com.ryancase.golf_v3;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class Hole {
    private int score;
    private int par;
    private int fairway;
    private int green;
    private int putts;
    private int upAndDown;
    private int scoreToPar;

    public Hole(int score, int par, int fairway, int green, int putts, int upAndDown, int scoreToPar) {
        this.score = score;
        this.par = par;
        this.fairway = fairway;
        this.green = green;
        this.putts = putts;
        this.upAndDown = upAndDown;
        this.scoreToPar = scoreToPar;
    }

    public Hole() {

    }

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

    public int getScoreToPar() {
        return scoreToPar;
    }

    public void setScoreToPar(int scoreToPar) {
        this.scoreToPar = scoreToPar;
    }

    public static class Builder {
        private int score;
        private int par;
        private int fairway;
        private int green;
        private int putts;
        private int upAndDown;
        private int scoreToPar;

        public Builder score(int score) {
            this.score = score;
            return this;
        }

        public Builder par(int par) {
            this.par = par;
            return this;
        }

        public Builder fairway(int fairway) {
            this.fairway = fairway;
            return this;
        }

        public Builder green(int green) {
            this.green = green;
            return this;
        }

        public Builder putts(int putts) {
            this.putts = putts;
            return this;
        }

        public Builder upAndDown(int upAndDown) {
            this.upAndDown = upAndDown;
            return this;
        }

        public Builder scoreToPar(int scoreToPar) {
            this.scoreToPar = scoreToPar;
            return this;
        }

        public Hole build() {
            return new Hole(score, par, fairway, green, putts, upAndDown, scoreToPar);
        }
    }
}
