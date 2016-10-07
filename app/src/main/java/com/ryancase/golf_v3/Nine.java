package com.ryancase.golf_v3;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class Nine {
    private int par;
    private int score;
    private int scoreToPar;
    private int putts;
    private int greens;
    private int fairways;
    private String fairwayPercentage;
    private String greenPercentage;

    private List<Hole> holes;

    public Nine() {
        holes = new ArrayList<>();
    }

    public List<Hole> getHoles() {
        return holes;
    }

    public void setHoles(List<Hole> holes) {
        this.holes = holes;
    }

    public void addHole(Hole hole) {
        if (holes != null) {
            holes.add(hole);
        }
    }

    public int getPar() {
        par = 0;

        for (Hole hole : holes) {
            par += hole.getPar();
        }

        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int getScore() {
        score = 0;

        for (Hole hole : holes) {
            score += hole.getScore();
        }

        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScoreToPar() {
        scoreToPar = 0;

        for (Hole hole : holes) {
            scoreToPar += hole.getScoreToPar();
        }

        return scoreToPar;
    }

    public void setScoreToPar(int scoreToPar) {
        this.scoreToPar = scoreToPar;
    }

    public int getPutts() {
        putts = 0;

        for (Hole hole : holes) {
            putts += hole.getPutts();
        }

        return putts;
    }

    public void setPutts(int putts) {
        this.putts = putts;
    }

    public int getGreens() {
        greens = 0;

        for (Hole hole : holes) {
            greens += hole.getGreen();
        }

        return greens;
    }

    public void setGreens(int greens) {
        this.greens = greens;
    }

    public int getFairways() {
        fairways = 0;

        for (Hole hole : holes) {
            fairways += hole.getFairway();
        }

        return fairways;
    }

    public void setFairways(int fairways) {
        this.fairways = fairways;
    }

    public String getFairwayPercentage() {
        float percent;
        float numOfPossibleFairways = 0;

        for (Hole hole : holes) {
            if (hole.getPar() != 3) {
                numOfPossibleFairways += 1;
            }
        }
        float fairwaysHit = getFairways();
        percent = fairwaysHit / numOfPossibleFairways;
        if(Float.isNaN(percent)) {
            fairwayPercentage = "0.00%";
        } else {
            percent *= 100;
            fairwayPercentage = String.format("%.2f", percent);
            fairwayPercentage = fairwayPercentage + "%";
        }
        return fairwayPercentage;
    }

    public void setFairwayPercentage(String fairwayPercentage) {
        this.fairwayPercentage = fairwayPercentage;
    }

    public String getGreenPercentage() {
        float percent;
        float numOfPossibleGreens = 18;

        float greensHit = getGreens();
        percent = greensHit / numOfPossibleGreens;
        if(Float.isNaN(percent)) {
            greenPercentage = "0.00%";
        } else {
            percent *= 100;
            greenPercentage = String.format("%.2f", percent);
            greenPercentage = greenPercentage + "%";
        }
        return greenPercentage;
    }

    public void setGreenPercentage(String greenPercentage) {
        this.greenPercentage = greenPercentage;
    }

    public String madePuttsPercentage() {
        String retval;
        float percent;
        float puttsMade = 0;

        for (Hole hole : holes) {
            if (hole.getPutts() > 0) {
                puttsMade += 1;
            }
        }

        float numOfPutts = getPutts();
        percent = puttsMade / numOfPutts;
        if (Float.isNaN(percent)) {
            return 0 + "%";
        }
        percent *= 100;
        retval = String.format("%.2f", percent);
        retval = retval + "%";

        return retval;
    }

    public String getAverageDriverRating() {
        String retval;
        float total = 0;
        float drivingHoles = 0;

        for (Hole hole : holes) {
            if (hole.getDriverRating() != 0) {
                total += hole.getDriverRating();
                drivingHoles++;
            }
        }

        float average = total / drivingHoles;


        retval = getLetterGrade(average);

        //        if(average <= 1) {
//            retval = "A";
//        } else if(average > 1 && average <= 1.30) {
//            retval = "A-";
//        } else if(average > 1.30 && average <= 1.75) {
//            retval = "B+";
//        } else if(average > 1.75 && average <= 2) {
//            retval = "B";
//        } else if(average > 2  && average <= 2.30) {
//            retval = "B-";
//        } else if(average > 2.30 && average <= 2.75) {
//            retval = "C+";
//        } else if(average > 2.75 && average <= 3) {
//            retval = "C";
//        } else if(average > 3 && average <= 3.30) {
//            retval = "C-";
//        } else {
//            retval = "D";
//        }

        return retval;
    }

    public float getAverageDriverRatingAsFloat() {
        float total = 0;
        float drivingHoles = 0;

        for (Hole hole : holes) {
            if (hole.getDriverRating() != 0) {
                total += hole.getDriverRating();
                drivingHoles++;
            }
        }

        float average = total / drivingHoles;
        if(Float.isNaN(average)) {
            average = 0.0f;
        }
        return average;
    }

    public String getAverageIronRating() {
        String retval;
        float total = 0;
        float validHoles = 0;

        for (Hole hole : holes) {
            if (hole.getIronRating() != 0) {
                total += hole.getIronRating();
                validHoles++;
            }
        }

        float average = total / validHoles;

        retval = getLetterGrade(average);

        return retval;
    }

    public float getAverageIronRatingAsFloat() {
        float total = 0;
        float validHoles = 0;

        for (Hole hole : holes) {
            if (hole.getIronRating() != 0) {
                total += hole.getIronRating();
                validHoles++;
            }
        }

        float average = total / validHoles;
        if(Float.isNaN(average)) {
            average = 0.0f;
        }
        return average;
    }

    public String getAverageApproachRating() {
        String retval;
        float total = 0;
        float validHoles = 0;

        for (Hole hole : holes) {
            if (hole.getPuttRating() != 0) {
                total += hole.getPuttRating();
                validHoles++;
            }
        }

        float average = total / validHoles;

        retval = getLetterGrade(average);

        return retval;
    }

    public float getAverageApproachRatingAsFloat() {
        float total = 0;
        float validHoles = 0;

        for (Hole hole : holes) {
            if (hole.getApproachRating() != 0) {
                total += hole.getApproachRating();
                validHoles++;
            }
        }

        float average = total / validHoles;
        if(Float.isNaN(average)) {
            average = 0.0f;
        }
        return average;
    }

    public String getAveragePuttRating() {
        String retval;
        float total = 0;
        float validHoles = 0;

        for (Hole hole : holes) {
            if (hole.getPuttRating() != 0) {
                total += hole.getPuttRating();
                validHoles++;
            }
        }

        float average = total / validHoles;

        retval = getLetterGrade(average);

        return retval;
    }

    public float getAveragePuttRatingAsFloat() {
        float total = 0;
        float validHoles = 0;

        for (Hole hole : holes) {
            if (hole.getPuttRating() != 0) {
                total += hole.getPuttRating();
                validHoles++;
            }
        }

        float average = total / validHoles;
        if(Float.isNaN(average)) {
            average = 0.0f;
        }
        return average;
    }

    private String getLetterGrade(float average) {
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
