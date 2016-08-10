package com.ryancase.golf_v3;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class RoundModel {

    private Nine _frontNine;
    private Nine _backNine;
    private String roundId;
    private String course;

    public RoundModel(Nine _frontNine, Nine _backNine, String roundId, String course) {
        this._frontNine = _frontNine;
        this._backNine = _backNine;
        this.roundId = roundId;
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getRoundId() {
        return roundId;
    }

    public void setRoundId(String roundId) {
        this.roundId = roundId;
    }

    public Nine getFrontNine() {
        return _frontNine;
    }

    public void setFrontNine(Nine frontNine) {
        _frontNine = frontNine;
    }

    public Nine getBackNine() {
        return _backNine;
    }

    public void setBackNine(Nine backNine) {
        _backNine = backNine;
    }
}
