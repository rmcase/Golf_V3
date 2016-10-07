package com.ryancase.golf_v3.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.ViewModels.ApproachViewModel;
import com.ryancase.golf_v3.ViewModels.DrivingViewModel;
import com.ryancase.golf_v3.databinding.ApproachStatTabBinding;
import com.ryancase.golf_v3.databinding.DrivingStatTabBinding;

/**
 * Created by ryancase on 10/6/16.
 */

public class ApproachTabFragment extends Fragment {

    private String ironRating, approachRating;
    private float greenPercentage;
    private int greens;

    private ApproachStatTabBinding binding;

    private ApproachViewModel viewModel;

    public ApproachTabFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ironRating = getArguments().getString("ironRating");
            approachRating = getArguments().getString("approachRating");
            greenPercentage = getArguments().getFloat("greenPercentage");
            greens = getArguments().getInt("greens");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.approach_stat_tab, container, false);

        binding = DataBindingUtil.bind(retval);
        if (viewModel == null) {
            viewModel = new ApproachViewModel();
        }
        binding.setViewModel(viewModel);

        viewModel.setApproachRating(approachRating);
        viewModel.setIronRating(ironRating);
        viewModel.setGreenPercentage(String.valueOf(greenPercentage));
        viewModel.setGreens(String.valueOf(greens));

        return retval;
    }
}
