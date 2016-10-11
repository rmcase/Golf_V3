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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.ViewModels.DrivingViewModel;
import com.ryancase.golf_v3.ViewModels.PuttViewModel;
import com.ryancase.golf_v3.databinding.DrivingStatTabBinding;
import com.ryancase.golf_v3.databinding.PuttStatTabBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryancase on 10/6/16.
 */

public class PuttTabFragment extends Fragment {

    private String puttRating;
    private float puttPercentage;
    private int putts;
    private PieChart pieChart;

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

        pieChart = binding.chart;

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(puttPercentage));
        entries.add(new PieEntry(100-puttPercentage));
        PieDataSet set = new PieDataSet(entries, "PUTTING");
//        set.setValueTextSize(12); //Set slice text size
        set.setColors(new int[] {Color.GREEN, Color.DKGRAY});
        PieData data = new PieData(set);
        data.setDrawValues(false);
        pieChart.setData(data);
        pieChart.setCenterTextSize(12);
        pieChart.setCenterText("Made Putts:\n" + puttPercentage + "%");
        pieChart.setDescription("");
        pieChart.getLegend().setEnabled(true);
        pieChart.getLegend().setCustom(new int[] {Color.GREEN, Color.DKGRAY}, new String[] {"% Made", "% Missed"});
        pieChart.getLegend().setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);
        pieChart.invalidate();

        viewModel.setPuttRating(puttRating);
        viewModel.setPuttPercentage(String.valueOf(puttPercentage));
        viewModel.setPutts(String.valueOf(putts));

        return retval;
    }
}
