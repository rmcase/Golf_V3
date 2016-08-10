package com.ryancase.golf_v3;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class Round {

    private static final String DATE_FORMAT = "dd/MM/YYYY";
    private static Nine frontNine;
    private static Nine backnine;
    private static String roundId;
    private static String course;
    private static Date datePlayed;

    public static String getDatePlayed() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        return dateFormat.format(datePlayed);
    }

    public static void setDatePlayed(Date datePlayed) {
        Round.datePlayed = datePlayed;
    }

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
        return frontNine;
    }

    public static void setFrontNine(Nine frontNine) {
        Round.frontNine = frontNine;
    }

    public static Nine getBackNine() {
        return backnine;
    }

    public static void setBackNine(Nine backNine) {
        backnine = backNine;
    }

    public static int getScore() {
        return frontNine.getScore() + backnine.getScore();
    }

    public static int getScoreToPar() {
        return frontNine.getScoreToPar() + backnine.getScoreToPar();
    }

    public static int getPar() {
        return frontNine.getPar() + backnine.getPar();
    }

    public static int getPutts() {
        return frontNine.getPutts() + backnine.getPutts();
    }

    public static int getGreens() {
        return frontNine.getGreens() + backnine.getGreens();
    }

    public static int getFairways() {
        return frontNine.getFairways() + backnine.getFairways();
    }
}
