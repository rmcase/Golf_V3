package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;
import android.widget.Button;
import android.widget.TableLayout;

import com.ryancase.golf_v3.RoundModel;
import com.ryancase.golf_v3.RoundThing;


public class HistoryRoundViewModel extends BaseObservable {
    private String title;

    public HistoryRoundViewModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}





