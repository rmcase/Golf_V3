package com.ryancase.golf_v3;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class Round {

    private static Nine _frontNine;
    private static Nine _backNine;

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

    public static int getPar() {
        return _frontNine.getPar() + _backNine.getPar();
    }
}
