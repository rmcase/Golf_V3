package com.ryancase.golf_v3;

import java.util.List;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class RoundObject {

//    private RoundModel roundModel;
    private static List<RoundThing> historyListRounds;

    public static List<RoundThing> getHistoryListRounds() {
        return historyListRounds;
    }

    public static void setHistoryListRounds(List<RoundThing> historyListRds) {
        historyListRounds = historyListRds;
    }

//    public RoundModel getRoundModel() {
//        return roundModel;
//    }
//
//    public void setRoundModel(RoundModel roundModel) {
//        this.roundModel = roundModel;
//    }
}
