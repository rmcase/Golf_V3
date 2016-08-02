package com.ryancase.golf_v3;

/**
 * File description here...
 */

public class Nine {
    private Hole[] holes;

    public Nine(boolean isFrontNine) {
        holes = new Hole[9];
    }

    public Hole[] getHoles() {
        return holes;
    }

    public void setHoles(Hole[] holes) {
        this.holes = holes;
    }

    public void addHole(Hole hole, int pos) {
        if (holes[pos] == null) {
            holes[pos] = hole;
        }
    }
}
