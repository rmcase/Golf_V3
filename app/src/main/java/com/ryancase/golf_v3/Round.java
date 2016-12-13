package com.ryancase.golf_v3;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class Round {

    private static final String DATE_FORMAT = "dd/MM/yy";
    private static Nine frontNine;
    private static Nine backnine;
    private static String roundId;
    private static String course;
    private static Date datePlayed;
    private static int score, putts, relativeScore = 0;

    public static void setPutts(int putts) {
        Round.putts = putts;
    }

    public static int getRelativeScore() {
        return relativeScore;
    }

    public static void setRelativeScore(int relativeScore) {
        Round.relativeScore = relativeScore;
    }

    public static void setScore(int score) {
        Round.score = score;
    }

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

    public static String getRating(String shotToRate) {
        switch (shotToRate) {
            case "Driver": {
                return getLetterGrade((frontNine.getAverageDriverRatingAsFloat() + backnine.getAverageDriverRatingAsFloat()) / 2f);
            }
            case "Iron": {
                return getLetterGrade((frontNine.getAverageIronRatingAsFloat() + backnine.getAverageIronRatingAsFloat()) / 2f);
            }
            case "Approach": {
                return getLetterGrade((frontNine.getAverageApproachRatingAsFloat() + backnine.getAverageApproachRatingAsFloat()) / 2f);
            }
            case "Putt": {
                return getLetterGrade((frontNine.getAveragePuttRatingAsFloat() + backnine.getAveragePuttRatingAsFloat()) / 2f);
            }
            default:
                return null;
        }
    }

    private static String getLetterGrade(float average) {
        String retval;

        if (average <= 1) {
            retval = "A";
        } else if (average > 1 && average <= 1.30) {
            retval = "A-";
        } else if (average > 1.30 && average <= 1.75) {
            retval = "B+";
        } else if (average > 1.75 && average <= 2) {
            retval = "B";
        } else if (average > 2 && average <= 2.30) {
            retval = "B-";
        } else if (average > 2.30 && average <= 2.75) {
            retval = "C+";
        } else if (average > 2.75 && average <= 3) {
            retval = "C";
        } else if (average > 3 && average <= 3.30) {
            retval = "C-";
        } else {
            retval = "D";
        }

        return retval;
    }
}
