package com.ryancase.golf_v3.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryancase.golf_v3.Nine;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.ViewModels.ScorecardViewModel;
import com.ryancase.golf_v3.databinding.ScorecardDialogBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryancase on 10/12/16.
 */
public class ScorecardFragment extends android.support.v4.app.DialogFragment {

    private int score, scoreToPar, putts;

    private Nine front, back;

    private ScorecardViewModel viewModel;

    private ScorecardDialogBinding binding;

    public ScorecardFragment(int score, int scoreToPar, int putts, Nine front, Nine back) {
        this.score = score;
        this.scoreToPar = scoreToPar;
        this.putts = putts;
        this.front = front;
        this.back = back;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scorecard_dialog, container);

        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        binding = DataBindingUtil.bind(view);

        if (viewModel == null) {
            viewModel = new ScorecardViewModel();
        }
        binding.setViewModel(viewModel);

        getDialog().show();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        Log.d("FROSIZE", ""+ front.getHoles().size());

        List<String> scores = new ArrayList<>();
        for(int i=0; i<front.getHoles().size(); i++) {
            scores.add(i, String.valueOf(front.getHoles().get(i).getScore()));
        }

        Log.d("SCOSIZE", ""+scores.size());

        for(int i=0; i<(18-front.getHoles().size()); i++) {
            scores.add(i+front.getHoles().size(), String.valueOf(0));
        }

        if(back.getHoles().size() != 0) {
            for(int i=0; i<back.getHoles().size(); i++) {
                scores.add(i+9, String.valueOf(back.getHoles().get(i).getScore()));
            }
        }


        viewModel.setScores(scores);
        viewModel.setScore(String.valueOf(score));
        viewModel.setPutts(String.valueOf(putts));
        viewModel.setScoreToPar(String.valueOf(scoreToPar));

        return view;
    }

}
