package com.ryancase.golf_v3.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.ViewModels.DrivingViewModel;
import com.ryancase.golf_v3.ViewModels.PuttViewModel;
import com.ryancase.golf_v3.databinding.DrivingStatTabBinding;
import com.ryancase.golf_v3.databinding.PuttStatTabBinding;

/**
 * Created by ryancase on 10/6/16.
 */

public class PuttTabFragment extends Fragment {

    private String puttRating;
    private float puttPercentage;
    private int putts;

    private PuttStatTabBinding binding;

    private PuttViewModel viewModel;

    public PuttTabFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            puttRating = getArguments().getString("puttRating");
            puttPercentage = getArguments().getFloat("puttPercentage");
            putts = getArguments().getInt("putts");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.putt_stat_tab, container, false);

        binding = DataBindingUtil.bind(retval);
        if (viewModel == null) {
            viewModel = new PuttViewModel();
        }
        binding.setViewModel(viewModel);

        viewModel.setPuttRating(puttRating);
        viewModel.setPuttPercentage(String.valueOf(puttPercentage));
        viewModel.setPutts(String.valueOf(putts));

        return retval;
    }
}
