package com.ryancase.golf_v3.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.ViewModels.DrivingViewModel;
import com.ryancase.golf_v3.ViewModels.OverviewViewModel;
import com.ryancase.golf_v3.databinding.DrivingStatTabBinding;
import com.ryancase.golf_v3.databinding.OverviewStatTabBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryancase on 10/6/16.
 */

public class DrivingTabFragment extends Fragment {

    private String driverRating;
    private float fairwayPercentage;
    private int fairways;
    private PieChart pieChart;

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

        pieChart = binding.chart;

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(fairwayPercentage));
        entries.add(new PieEntry(100-fairwayPercentage));
        PieDataSet set = new PieDataSet(entries, "DRIVING");
        set.setColors(new int[] {Color.GREEN, Color.DKGRAY});
        PieData data = new PieData(set);
        data.setDrawValues(false);
        pieChart.setData(data);
        pieChart.setCenterTextSize(12);
        pieChart.setCenterText("Fairways Hit:\n" + fairwayPercentage + "%");
        pieChart.setDescription("");
        pieChart.getLegend().setEnabled(true);
        pieChart.getLegend().setCustom(new int[] {Color.GREEN, Color.DKGRAY}, new String[] {"% Hit", "% Missed"});
        pieChart.getLegend().setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);
        pieChart.invalidate();

        viewModel.setDriverRating(driverRating);
        viewModel.setFairwayPercentage(String.valueOf(fairwayPercentage));
        viewModel.setFairways(String.valueOf(fairways));

        return retval;
    }
}
