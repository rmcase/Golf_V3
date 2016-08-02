package com.ryancase.golf_v3;

import android.databinding.BaseObservable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;


public class HoleViewModel extends BaseObservable {
    private int score;
    private int par;
    private String title;
    private NumberPicker scoreSelect;
    private NumberPicker parSelect;
    private Button nextHoleButton;
    private CheckBox onePutt;
    private CheckBox twoPutt;
    private CheckBox threePutt;
    private CheckBox fourPutt;
    private TextView scoreLabel;
    private HoleView holeView;

    public HoleViewModel(HoleView holeView) {
        this.holeView = holeView;
    }

    public int getScore() {
        return score;
    }

    public int getPar() {
        return par;
    }

    public String getTitle() {
        return title;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPar(int par) {
        this.par = par;
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

    public TextView getScoreLabel() {
        return scoreLabel;
    }

    public void setScoreLabel(TextView scoreLabel) {
        this.scoreLabel = scoreLabel;
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
}





