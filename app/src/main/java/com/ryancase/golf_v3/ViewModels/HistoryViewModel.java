package com.ryancase.golf_v3.ViewModels;

import android.databinding.BaseObservable;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;


public class HistoryViewModel extends BaseObservable {
    private String title;

    private ListView historyList;

    public HistoryViewModel() {

    }

    public ListView getHistoryList() {
        return historyList;
    }

    public void setHistoryList(ListView historyList) {
        this.historyList = historyList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}





