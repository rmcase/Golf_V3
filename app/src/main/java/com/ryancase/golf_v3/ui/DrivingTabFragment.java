package com.ryancase.golf_v3.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.ViewModels.DrivingViewModel;
import com.ryancase.golf_v3.ViewModels.OverviewViewModel;
import com.ryancase.golf_v3.databinding.DrivingStatTabBinding;
import com.ryancase.golf_v3.databinding.OverviewStatTabBinding;

/**
 * Created by ryancase on 10/6/16.
 */

public class DrivingTabFragment extends Fragment {

    private String driverRating;
    private float fairwayPercentage;
    private int fairways;

    private DrivingStatTabBinding binding;

    private DrivingViewModel viewModel;

    public DrivingTabFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            driverRating = getArguments().getString("driverRating");
            fairwayPercentage = getArguments().getFloat("fairwayPercentage");
            fairways = getArguments().getInt("fairways");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.driving_stat_tab, container, false);

        binding = DataBindingUtil.bind(retval);
        if (viewModel == null) {
            viewModel = new DrivingViewModel();
        }
        binding.setViewModel(viewModel);

        viewModel.setDriverRating(driverRating);
        viewModel.setFairwayPercentage(String.valueOf(fairwayPercentage));
        viewModel.setFairways(String.valueOf(fairways));

        return retval;
    }
}
