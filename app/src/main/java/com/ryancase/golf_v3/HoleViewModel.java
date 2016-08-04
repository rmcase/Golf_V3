package com.ryancase.golf_v3;

import android.databinding.BaseObservable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;


public class HoleViewModel extends BaseObservable {
    private String title;
    private String puttTv;
    private NumberPicker scoreSelect;
    private NumberPicker parSelect;
    private Button nextHoleButton;
    private CheckBox onePutt;
    private CheckBox twoPutt;
    private CheckBox threePutt;
    private CheckBox fourPutt;
    private CheckBox greenCheck;
    private CheckBox fairwayCheck;
    private CheckBox upAndDownCheck;
    private HoleView holeView;

    public HoleViewModel(HoleView holeView) {
        this.holeView = holeView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NumberPicker getScoreSelect() {
        return scoreSelect;
    }

    public void setScoreSelect(NumberPicker scoreSelect) {
        this.scoreSelect = scoreSelect;
    }

    public Button getNextHoleButton() {
        return nextHoleButton;
    }

    public void setNextHoleButton(Button nextHoleButton) {
        this.nextHoleButton = nextHoleButton;
    }

    public NumberPicker getParSelect() {
        return parSelect;
    }

    public void setParSelect(NumberPicker parSelect) {
        this.parSelect = parSelect;
    }

    public CheckBox getOnePutt() {
        return onePutt;
    }

    public void setOnePutt(CheckBox onePutt) {
        this.onePutt = onePutt;
    }

    public CheckBox getTwoPutt() {
        return twoPutt;
    }

    public void setTwoPutt(CheckBox twoPutt) {
        this.twoPutt = twoPutt;
    }

    public CheckBox getThreePutt() {
        return threePutt;
    }

    public void setThreePutt(CheckBox threePutt) {
        this.threePutt = threePutt;
    }

    public CheckBox getFourPutt() {
        return fourPutt;
    }

    public void setFourPutt(CheckBox fourPutt) {
        this.fourPutt = fourPutt;
    }

    public String getPuttTv() {
        return puttTv;
    }

    public void setPuttTv(String puttTv) {
        this.puttTv = puttTv;
    }

    public CheckBox getUpAndDownCheck() {
        return upAndDownCheck;
    }

    public void setUpAndDownCheck(CheckBox upAndDownCheck) {
        this.upAndDownCheck = upAndDownCheck;
    }

    public CheckBox getFairwayCheck() {
        return fairwayCheck;
    }

    public void setFairwayCheck(CheckBox fairwayCheck) {
        this.fairwayCheck = fairwayCheck;
    }

    public CheckBox getGreenCheck() {
        return greenCheck;
    }

    public void setGreenCheck(CheckBox greenCheck) {
        this.greenCheck = greenCheck;
    }

    public int getParForHole() {
        return parSelect.getValue();
    }

    public int getScoreForHole() {
        return scoreSelect.getValue();
    }

    public int getNumberOfPutts() {
        int retval = 0;

        if (onePutt.isChecked()) {
            retval = 1;
        } else if (twoPutt.isChecked()) {
            retval = 2;
        } else if (threePutt.isChecked()) {
            retval = 3;
        } else if (fourPutt.isChecked()) {
            retval = 4;
        }

        return retval;
    }

    public int getGreenStat() {
        int retval = 0;

        if (greenCheck.isChecked()) {
            retval = 1;
        }

        return retval;
    }

    public int getFairwayStat() {
        int retval = 0;

        if(fairwayCheck.isChecked()) {
            retval = 1;
        }

        return retval;
    }

    public int getUpAndDownStat() {
        int retval = 0;

        if(upAndDownCheck.isChecked()) {
            retval = 1;
        }

        return retval;
    }

    public int getScoreRelativeToPar() {
        return scoreSelect.getValue() - parSelect.getValue();
    }
}





