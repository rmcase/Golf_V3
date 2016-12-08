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
import com.ryancase.golf_v3.ViewModels.ApproachViewModel;
import com.ryancase.golf_v3.ViewModels.DrivingViewModel;
import com.ryancase.golf_v3.databinding.ApproachStatTabBinding;
import com.ryancase.golf_v3.databinding.DrivingStatTabBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryancase on 10/6/16.
 */

public class ApproachTabFragment extends Fragment {

    private String ironRating, approachRating;
    private float greenPercentage;
    private int greens, scrambling;
    private PieChart pieChart;

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

        pieChart = binding.chart;
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(greenPercentage));
        entries.add(new PieEntry(100-greenPercentage));
        PieDataSet set = new PieDataSet(entries, "GREENS");
        set.setColors(new int[] {Color.GREEN, Color.DKGRAY});
        PieData data = new PieData(set);
        data.setDrawValues(false);
        pieChart.setData(data);
        pieChart.setCenterTextSize(12);
        pieChart.setCenterText("Greens Hit:\n" + greenPercentage + "%");
        pieChart.setDescription("");
        pieChart.getLegend().setEnabled(true);
        pieChart.getLegend().setCustom(new int[] {Color.GREEN, Color.DKGRAY}, new String[] {"% Hit", "% Missed"});
        pieChart.getLegend().setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);
        pieChart.invalidate();

        viewModel.setApproachRating(approachRating);
        viewModel.setIronRating(ironRating);
        viewModel.setGreenPercentage(String.valueOf(greenPercentage));
        viewModel.setGreens(String.valueOf(greens));

        return retval;
    }
}
