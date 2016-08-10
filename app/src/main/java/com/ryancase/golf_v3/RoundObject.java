package com.ryancase.golf_v3;

import lombok.ToString;

/**
 * File description here...
 */

@ToString
public class RoundObject {

    private RoundModel roundModel;

    public RoundObject(RoundModel roundModel) {
        this.roundModel = roundModel;
    }

    public RoundModel getRoundModel() {
        return roundModel;
    }

    public void setRoundModel(RoundModel roundModel) {
        this.roundModel = roundModel;
    }
}
