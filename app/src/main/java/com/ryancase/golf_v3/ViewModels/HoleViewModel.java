package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ryancase.golf_v3.Helpers.Checkbox;
import com.ryancase.golf_v3.HoleView;


public class HoleViewModel extends BaseObservable {
    private String title;
    private String puttTv;
    private NumberPicker scoreSelect;
    private NumberPicker parSelect;
    private NumberPicker ratingSelector;
    private RadioGroup driverRg, puttRg, approachRg, ironRg;
    private Button nextHoleButton;
    private CheckBox onePutt;
    private CheckBox twoPutt;
    private CheckBox threePutt;
    private CheckBox greenCheck;
    private CheckBox fairwayCheck;
    private CheckBox upAndDownCheck;
    private TextView clubToRate;
    private CheckBox ratingConfirm;
    private int driverRating, ironRating, approachRating, puttRating;

    public RadioGroup getDriverRg() {
        return driverRg;
    }

    public void setDriverRg(RadioGroup driverRg) {
        this.driverRg = driverRg;
    }

    public RadioGroup getPuttRg() {
        return puttRg;
    }

    public void setPuttRg(RadioGroup puttRg) {
        this.puttRg = puttRg;
    }

    public RadioGroup getApproachRg() {
        return approachRg;
    }

    public void setApproachRg(RadioGroup approachRg) {
        this.approachRg = approachRg;
    }

    public RadioGroup getIronRg() {
        return ironRg;
    }

    public void setIronRg(RadioGroup ironRg) {
        this.ironRg = ironRg;
    }

    public CheckBox getRatingConfirm() {
        return ratingConfirm;
    }

    public void setRatingConfirm(CheckBox ratingConfirm) {
        this.ratingConfirm = ratingConfirm;
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

    public TextView getClubToRate() {
        return clubToRate;
    }

    public void setClubToRate(TextView clubToRate) {
        this.clubToRate = clubToRate;
    }

    public NumberPicker getRatingSelector() {
        return ratingSelector;
    }

    public void setRatingSelector(NumberPicker ratingSelector) {
        this.ratingSelector = ratingSelector;
    }

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





