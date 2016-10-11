package com.ryancase.golf_v3.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.fitness.data.DataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.RoundThing;
import com.ryancase.golf_v3.ViewModels.HistoryViewModel;
import com.ryancase.golf_v3.ViewModels.StatisticsViewModel;
import com.ryancase.golf_v3.databinding.FragmentHistoryBinding;
import com.ryancase.golf_v3.databinding.FragmentStatisticsBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * File description here...
 */

public class StatisticsFragment extends android.support.v4.app.Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private StatisticsViewModel viewModel;

    private FragmentStatisticsBinding binding;

    private DatabaseReference database;
    private List<RoundThing> previousRounds;
    private LineChart lineChart;
    private NumberPicker picker;
    private List<Integer> scores, putts, greens;

    public StatisticsFragment() {
    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.fragment_statistics, container, false);

        getActivity().setTitle(R.string.statistics);

        binding = DataBindingUtil.bind(retval);


        if (viewModel == null) {
            viewModel = new StatisticsViewModel();
        }
        binding.setViewModel(viewModel);

        bindViewModelElements();

        populateViewModelElements();

        loadRounds();

        return retval;
    }

    private void populateViewModelElements() {
        previousRounds = new ArrayList<>();
        scores = new ArrayList<>();
        putts = new ArrayList<>();
        greens = new ArrayList<>();

        picker.setDisplayedValues(new String[]{"Score", "Putts", "Greens"});
        picker.setMinValue(0);
        picker.setMaxValue(2);
        picker.setValue(0);

    }

    private void bindViewModelElements() {
//       viewModel.setHistoryList(binding.historyList);
        lineChart = binding.chart;
        picker = binding.graphPicker;

    }

    private void loadRounds() {
        database = FirebaseDatabase.getInstance().getReference();
        Query roundQuery = database.child("Rounds").orderByChild("date");

        roundQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                RoundThing round = dataSnapshot.getValue(RoundThing.class);

                previousRounds.add(round);

                Log.d("THEMOFO:", "" + round.getDate());

                int score = round.getBackNine().getScore() + round.getFrontNine().getScore();
                int putt = round.getBackNine().getPutts() + round.getFrontNine().getPutts();
                int green = round.getBackNine().getGreens() + round.getFrontNine().getGreens();

                greens.add(green);
                putts.add(putt);
                scores.add(score);

                createChart("Score");

                picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if (newVal == 1) {
                            createChart("Putts");
                        }
                        if(newVal == 0) {
                            createChart("Score");
                        }
                        if(newVal == 2) {
                            createChart("Greens");
                        }
                    }
                });

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createChart(String value) {
        List<Entry> entries = new ArrayList<>();
        lineChart.getXAxis().setGranularity(1f);
        lineChart.setDescription("");
        lineChart.getAxisRight().setEnabled(false);

        switch (value) {
            case "Score": {
                for (int i = 1; i <= scores.size(); i++) {
                    entries.add(new Entry(i, scores.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                break;
            }

            case "Putts": {
                for (int i = 1; i <= putts.size(); i++) {
                    entries.add(new Entry(i, putts.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                break;
            }

            case "Greens": {
                for (int i = 1; i <= greens.size(); i++) {
                    entries.add(new Entry(i, greens.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                break;
            }
        }


    }


}
