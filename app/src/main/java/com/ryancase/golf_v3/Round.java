package com.ryancase.golf_v3;

/**
 * File description here...
 */

public class Round {

    private static Nine _frontNine;
    private static Nine _backNine;

    public static Nine getFrontNine() {
        return _frontNine;
    }

    public static void setFrontNine(Nine frontNine) { _frontNine = frontNine;
    }

    public static Nine getBackNine() {
        return _backNine;
    }

    public static void setBackNine(Nine backNine) {
        _backNine = backNine;
    }
}
