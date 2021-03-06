package com.ryancase.golf_v3.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.ViewModels.HistoryViewModel;
import com.ryancase.golf_v3.ViewModels.OverviewViewModel;
import com.ryancase.golf_v3.databinding.OverviewStatTabBinding;

/**
 * Created by ryancase on 10/6/16.
 */

public class OverviewTabFragment extends Fragment {

    private int score, scoreToPar, frontScore, backScore, par
            ,birdies, eagles, pars;
    private String scoreToParString;

    private OverviewStatTabBinding binding;

    private OverviewViewModel viewModel;

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
            birdies = getArguments().getInt("birdies");
            pars = getArguments().getInt("pars");
            eagles = getArguments().getInt("eagles");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.overview_stat_tab, container, false);

        binding = DataBindingUtil.bind(retval);
        if (viewModel == null) {
            viewModel = new OverviewViewModel();
        }
        binding.setViewModel(viewModel);

        viewModel.setScore(String.valueOf(score));
        viewModel.setFrontScore(String.valueOf(frontScore));
        viewModel.setBackScore(String.valueOf(backScore));
        viewModel.setPar(String.valueOf(par));
        if(scoreToPar > 0) {
            scoreToParString = "+" + scoreToPar;
        } else {
            scoreToParString = String.valueOf(scoreToPar);
        }
        viewModel.setScoreToPar(scoreToParString);
        viewModel.setBirdies(String.valueOf(birdies));
        viewModel.setEagles(String.valueOf(eagles));
        viewModel.setPars(String.valueOf(pars));
        if(scoreToPar == 0) {
            viewModel.setTextColor(getResources().getColor(R.color.fTeal));
        } else if(scoreToPar < 0) {
            viewModel.setTextColor(getResources().getColor(R.color.red));
        }

        return retval;
    }
}
