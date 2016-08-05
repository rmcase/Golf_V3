package com.ryancase.golf_v3;

import android.databinding.BaseObservable;
import android.widget.Button;
import android.widget.TableLayout;


public class StatViewModel extends BaseObservable {
    private String title;
    private String score, scoreTv;
    private String par, parTv;
    private String scoreToPar, scoreToParTv;
    private Button nextHoleButton;
    private TableLayout tableLayout;

    public StatViewModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Button getNextHoleButton() {
        return nextHoleButton;
    }

    public void setNextHoleButton(Button nextHoleButton) {
        this.nextHoleButton = nextHoleButton;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoreTv() {
        return scoreTv;
    }

    public void setScoreTv(String scoreTv) {
        this.scoreTv = scoreTv;
    }

    public String getPar() {
        return par;
    }

    public void setPar(String par) {
        this.par = par;
    }

    public String getParTv() {
        return parTv;
    }

    public void setParTv(String parTv) {
        this.parTv = parTv;
    }

    public String getScoreToPar() {
        return scoreToPar;
    }

    public void setScoreToPar(String scoreToPar) {
        this.scoreToPar = scoreToPar;
    }

    public String getScoreToParTv() {
        return scoreToParTv;
    }

    public void setScoreToParTv(String scoreToParTv) {
        this.scoreToParTv = scoreToParTv;
    }

    public TableLayout getTableLayout() {
        return tableLayout;
    }

    public void setTableLayout(TableLayout tableLayout) {
        this.tableLayout = tableLayout;
    }
}





