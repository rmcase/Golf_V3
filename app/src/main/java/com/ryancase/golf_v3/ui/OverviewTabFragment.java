package com.ryancase.golf_v3.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryancase.golf_v3.R;

/**
 * Created by ryancase on 10/6/16.
 */

public class OverviewTabFragment extends Fragment {

    private int score, scoreToPar, frontScore, backScore, par;
    public OverviewTabFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getInt("score");
            scoreToPar = getArguments().getInt("scoreToPar");
            backScore = getArguments().getInt("backScore");
            frontScore = getArguments().getInt("frontScore");
            par = getArguments().getInt("par");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.overview_stat_tab, container, false);

//        Log.d("SAVEDINT:", "" + getArguments().getInt("Arg for Frag1"));



        return retval;
    }
}
