package com.ryancase.golf_v3;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class Round {

    private static Nine _frontNine;
    private static Nine _backNine;
    private static String roundId;
    private static String course;

    public static String getCourse() {
        return course;
    }

    public static void setCourse(String course) {
        Round.course = course;
    }

    public static String getRoundId() {
        return roundId;
    }

    public static void setRoundId(String roundId) {
        Round.roundId = roundId;
    }

    public static Nine getFrontNine() {
        return _frontNine;
    }

    public static void setFrontNine(Nine frontNine) {
        _frontNine = frontNine;
    }

    public static Nine getBackNine() {
        return _backNine;
    }

    public static void setBackNine(Nine backNine) {
        _backNine = backNine;
    }

    public static int getScore() {
        return _frontNine.getScore() + _backNine.getScore();
    }

    public static int getScoreToPar() {
        return _frontNine.getScoreToPar() + _backNine.getScoreToPar();
    }

    public static int getPar() {
        return _frontNine.getPar() + _backNine.getPar();
    }

    public static int getPutts() {
        return _frontNine.getPutts() + _backNine.getPutts();
    }

    public static int getGreens() {
        return _frontNine.getGreens() + _backNine.getGreens();
    }

    public static int getFairways() {
        return _frontNine.getFairways() + _backNine.getFairways();
    }
}
