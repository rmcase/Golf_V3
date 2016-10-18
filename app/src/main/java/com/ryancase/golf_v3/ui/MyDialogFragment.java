package com.ryancase.golf_v3.ui;

import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.ViewModels.DialogViewModel;
import com.ryancase.golf_v3.ViewModels.HoleViewModel;
import com.ryancase.golf_v3.databinding.CustomDialogBinding;

/**
 * Created by ryancase on 10/12/16.
 */
public class MyDialogFragment extends DialogFragment {

    private int score, scoreToPar, putts;

    private DialogViewModel viewModel;

    private CustomDialogBinding binding;

    public MyDialogFragment(int score, int scoreToPar, int putts) {
        this.score = score;
        this.scoreToPar = scoreToPar;
        this.putts = putts;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog, container);

        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
        p.x = 200;
        getDialog().getWindow().setAttributes(p);

        binding = DataBindingUtil.bind(view);

        if (viewModel == null) {
            viewModel = new DialogViewModel();
        }
        binding.setViewModel(viewModel);

        getDialog().show();

        String scoreSt = "Score:\t" + score;
        String puttSt = "Putts:\t" + putts;
        String relScoreSt = "Score to Par:\t" + scoreToPar;

        viewModel.setScore(scoreSt);
        viewModel.setPutts(String.valueOf(puttSt));
        viewModel.setScoreToPar(String.valueOf(relScoreSt));

        return view;
    }

}
