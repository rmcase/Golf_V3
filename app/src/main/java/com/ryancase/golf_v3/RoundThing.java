package com.ryancase.golf_v3;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class RoundThing {

    private Nine backNine;
    private Nine frontNine;
    private String course;
    private String date;
    private String roundId;


    public RoundThing() {
    }

    public Nine getBackNine() {
        return backNine;
    }

    public void setBackNine(Nine backNine) {
        this.backNine = backNine;
    }

    public Nine getFrontNine() {
        return frontNine;
    }

    public void setFrontNine(Nine frontNine) {
        this.frontNine = frontNine;
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

    public String getRoundId() {
        return roundId;
    }

    public void setRoundId(String roundId) {
        this.roundId = roundId;
    }
}
