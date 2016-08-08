package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;
import android.widget.Button;
import android.widget.TableLayout;


public class StatViewModel extends BaseObservable {
    private String title;
    private String score;
    private String par;
    private String putts;
    private String greens;
    private String fairways;
    private String scoreToPar;
    private Button nextHoleButton;
    private Button finishHoleButton;
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

    public String getPar() {
        return par;
    }

    public void setPar(String par) {
        this.par = par;
    }

    public String getScoreToPar() {
        return scoreToPar;
    }

    public void setScoreToPar(String scoreToPar) {
        this.scoreToPar = scoreToPar;
    }

    public TableLayout getTableLayout() {
        return tableLayout;
    }

    public void setTableLayout(TableLayout tableLayout) {
        this.tableLayout = tableLayout;
    }

    public String getPutts() {
        return putts;
    }

    public void setPutts(String putts) {
        this.putts = putts;
    }

    public String getGreens() {
        return greens;
    }

    public void setGreens(String greens) {
        this.greens = greens;
    }

    public String getFairways() {
        return fairways;
    }

    public void setFairways(String fairways) {
        this.fairways = fairways;
    }

    public Button getFinishHoleButton() {
        return finishHoleButton;
    }

    public void setFinishHoleButton(Button finishHoleButton) {
        this.finishHoleButton = finishHoleButton;
    }
}





