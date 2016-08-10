package com.ryancase.golf_v3;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class Nine {
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
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getPar();
        }

        return retval;
    }

    public int getScore() {
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getScore();
        }

        return retval;
    }

    public int getScoreToPar() {
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getScoreToPar();
        }

        return retval;
    }

    public int getPutts() {
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getPutts();
        }

        return retval;
    }

    public int getGreens() {
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getGreen();
        }

        return retval;
    }

    public int getFairways() {
        int retval = 0;

        for (Hole hole : holes) {
            retval += hole.getFairway();
        }

        return retval;
    }

    public String getFairwayPercentage() {
        String retval;
        float percent;
        float numOfPossibleFairways = 0;

        for (Hole hole : holes) {
            if (hole.getPar() != 3) {
                numOfPossibleFairways += 1;
            }
        }
        float fairwaysHit = getFairways();
        percent = fairwaysHit / numOfPossibleFairways;
        percent *= 100;
        retval = String.format("%.2f", percent);
        retval = retval + "%";
        return retval;
    }

    public String getGreenPercentage() {
        String retval;
        float percent;
        float numOfPossibleGreens = 18;

        float greensHit = getGreens();
        percent = greensHit / numOfPossibleGreens;
        percent *= 100;
        retval = String.format("%.2f", percent);
        retval = retval + "%";
        return retval;
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
        percent *= 100;
        retval = String.format("%.2f", percent);
        retval = retval + "%";
        return retval;
    }
}
