package com.ryancase.golf_v3;

/**
 * File description here...
 */

public class Nine {
    private static Hole[] holeList;

    public Nine() {
        holeList = new Hole[9];
    }

    public static Hole[] getHoles() {
        return holeList;
    }

    public static void setHoles(Hole[] holes) {
        holeList = holes;
    }

    public static void addHole(Hole hole, int pos) {
        if (holeList[pos] == null) {
            holeList[pos] = hole;
        }
    }

    public int getPar() {
        int retval = 0;

        for(Hole hole: holeList) {
            retval += hole.getPar();
        }

        return retval;
    }
}
