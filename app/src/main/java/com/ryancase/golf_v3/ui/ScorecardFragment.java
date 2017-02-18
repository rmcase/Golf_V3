package com.ryancase.golf_v3.ui;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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

@SuppressLint("ValidFragment")
public class ScorecardFragment extends android.support.v4.app.DialogFragment {

    private int score, putts, scoreToPar;

    private int birdies, pars, greens;

    private Nine front, back;

    private ScorecardViewModel viewModel;

    private ScorecardDialogBinding binding;

    public ScorecardFragment() {

    }

    public ScorecardFragment(int score, int putts, Nine front, Nine back) {
        this.score = score;
        this.putts = putts;
        this.front = front;
        this.back = back;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scorecard_dialog, container);

        binding = DataBindingUtil.bind(view);

        if (viewModel == null) {
            viewModel = new ScorecardViewModel();
        }
        binding.setViewModel(viewModel);

        getDialog().show();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        greens += front.getGreens();
        birdies += front.getBirdies();
        pars += front.getPars();

        List<String> scores = new ArrayList<>();
        for(int i=0; i<front.getHoles().size(); i++) {
            scores.add(i, String.valueOf(front.getHoles().get(i).getScore()));
        }

        for(int i=0; i<(18-front.getHoles().size()); i++) {
            scores.add(i+front.getHoles().size(), String.valueOf(0));
        }

        if(back.getHoles().size() != 0) {
            for(int i=0; i<back.getHoles().size(); i++) {
                scores.add(i+9, String.valueOf(back.getHoles().get(i).getScore()));
            }
            greens += back.getGreens();
            birdies += back.getBirdies();
            pars += back.getPars();
        }


        scoreToPar = back.getScoreToPar() + front.getScoreToPar();

        viewModel.setScores(scores);
        viewModel.setScore(String.valueOf(score));
        viewModel.setPutts(String.valueOf(putts));
        viewModel.setGreens(String.valueOf(greens));
        viewModel.setPars(String.valueOf(pars));
        viewModel.setBirdies(String.valueOf(birdies));

        if(scoreToPar == 0) {
            viewModel.setScoreToPar("E");
            binding.relativeScoreTv.setTextColor(getResources().getColor(R.color.fTeal));
        } else if(scoreToPar > 0) {
            viewModel.setScoreToPar("+" + String.valueOf(scoreToPar));
        } else {
            viewModel.setScoreToPar(String.valueOf(scoreToPar));
        }
        if(scoreToPar < 0) {
            binding.relativeScoreTv.setTextColor(getResources().getColor(R.color.red));
        }

        return view;
    }

}
