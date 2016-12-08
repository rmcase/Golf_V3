package com.ryancase.golf_v3;

import java.util.List;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class RoundObject {

    private static List<RoundThing> historyListRounds;

    public static List<RoundThing> getHistoryListRounds() {
        return historyListRounds;
    }

    public void setHistoryListRounds(List<RoundThing> historyListRnds) {
        historyListRounds = historyListRnds;
    }
}
