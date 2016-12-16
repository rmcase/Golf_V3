package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;
import android.widget.Button;
import android.widget.CheckBox;

import com.ryancase.golf_v3.HoleView;

import info.hoang8f.android.segmented.SegmentedGroup;


public class HoleViewModel extends BaseObservable {
    private String title;
    private String puttTv;
    private com.shawnlin.numberpicker.NumberPicker scoreSelect;
    private SegmentedGroup parSelect;
    private SegmentedGroup driverRg, puttRg, approachRg, ironRg;
    private Button nextHoleButton;
    private CheckBox greenCheck;
    private CheckBox fairwayCheck;
    private CheckBox upAndDownCheck;
    private int parForHole, scoreForHole, scoreRelativeToPar;
    private int driverRating, ironRating, approachRating, puttRating, numberOfPutts;
    private HoleView holeView;

    public HoleViewModel(HoleView holeView) {
        this.holeView = holeView;
    }

    public SegmentedGroup getDriverRg() {
        return driverRg;
    }

    public void setDriverRg(SegmentedGroup driverRg) {
        this.driverRg = driverRg;
    }

    public SegmentedGroup getPuttRg() {
        return puttRg;
    }

    public void setPuttRg(SegmentedGroup puttRg) {
        this.puttRg = puttRg;
    }

    public SegmentedGroup getApproachRg() {
        return approachRg;
    }

    public void setApproachRg(SegmentedGroup approachRg) {
        this.approachRg = approachRg;
    }

    public SegmentedGroup getIronRg() {
        return ironRg;
    }

    public void setIronRg(SegmentedGroup ironRg) {
        this.ironRg = ironRg;
    }

    public int getPuttRating() {
        return puttRating;
    }

    public void setPuttRating(int puttRating) {
        this.puttRating = puttRating;
    }

    public int getApproachRating() {
        return approachRating;
    }

    public void setApproachRating(int approachRating) {
        this.approachRating = approachRating;
    }

    public int getIronRating() {
        return ironRating;
    }

    public void setIronRating(int ironRating) {
        this.ironRating = ironRating;
    }

    public int getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(int driverRating) {
        this.driverRating = driverRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public com.shawnlin.numberpicker.NumberPicker getScoreSelect() {
        return scoreSelect;
    }

    public void setScoreSelect(com.shawnlin.numberpicker.NumberPicker scoreSelect) {
        this.scoreSelect = scoreSelect;
    }

    public Button getNextHoleButton() {
        return nextHoleButton;
    }

    public void setNextHoleButton(Button nextHoleButton) {
        this.nextHoleButton = nextHoleButton;
    }

    public SegmentedGroup getParSelect() {
        return parSelect;
    }

    public void setParSelect(SegmentedGroup parSelect) {
        this.parSelect = parSelect;
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
        return parForHole;
    }

    public void setParForHole(int parForHole) {
        this.parForHole = parForHole;
    }

    public int getScoreForHole() {
        return scoreSelect.getValue();
    }

    public int getNumberOfPutts() {
        return numberOfPutts;
    }

    public void setNumberOfPutts(int numberOfPutts) {
        this.numberOfPutts = numberOfPutts;
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
        return scoreRelativeToPar;
    }

    public void setScoreRelativeToPar(int scoreRelativeToPar) {
        this.scoreRelativeToPar = scoreRelativeToPar;
    }
}





