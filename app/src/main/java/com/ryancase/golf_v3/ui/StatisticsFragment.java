package com.ryancase.golf_v3.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.RoundThing;
import com.ryancase.golf_v3.ViewModels.StatisticsViewModel;
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
    private TextView averageTv, minTv, maxTv;
    private List<Integer> scores, putts, greens, fairways, scoresToPar;

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
        fairways = new ArrayList<>();
        scoresToPar = new ArrayList<>();

        picker.setDisplayedValues(new String[]{"Score", "Putts", "Greens", "Fairways", "Score To Par"});
        picker.setMinValue(0);
        picker.setMaxValue(4);
        picker.setValue(0);

    }

    private void bindViewModelElements() {
        lineChart = binding.chart;
        picker = binding.graphPicker;
        averageTv = binding.averageTv;
        minTv = binding.minTv;
        maxTv = binding.maxTv;
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
                int fairway = round.getBackNine().getFairways() + round.getFrontNine().getFairways();
                int relScore = round.getBackNine().getScoreToPar() + round.getFrontNine().getScoreToPar();

                scoresToPar.add(relScore);
                fairways.add(fairway);
                greens.add(green);
                putts.add(putt);
                scores.add(score);

                createChart("Score");

                picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if (newVal == 0) {
                            createChart("Score");
                        }
                        if (newVal == 1) {
                            createChart("Putts");
                        }
                        if (newVal == 2) {
                            createChart("Greens");
                        }
                        if (newVal == 3) {
                            createChart("Fairways");
                        }
                        if(newVal == 4) {
                            createChart("Relative Score");
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
        setChartSettings();

        List<Entry> entries = new ArrayList<>();
        float average = 0f;
        float max = 0, min = 100;
        averageTv.setText(getString(R.string.average));
        minTv.setText(R.string.min);
        maxTv.setText(R.string.max);

        switch (value) {
            case "Score": {
                for (int i = 1; i <= scores.size(); i++) {
                    if(scores.get(i-1) > max) {
                        max = scores.get(i-1);
                    }
                    if(scores.get(i-1) < min) {
                        min = scores.get(i-1);
                    }
                    average += scores.get(i-1);
                    entries.add(new Entry(i, scores.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(1.5f);
                dataSet.setCircleRadius(5f);
                dataSet.setCircleHoleRadius(3.25f);
                dataSet.setColor(getResources().getColor(R.color.darkgreen));
                dataSet.setCircleColor(Color.BLACK);
                dataSet.setCircleColorHole(getResources().getColor(R.color.darkgreen));

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float)scores.size();

                averageTv.setText(String.format(getString(R.string.average) + average));
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Putts": {
                for (int i = 1; i <= putts.size(); i++) {
                    if(putts.get(i-1) > max) {
                        max = putts.get(i-1);
                    }
                    if(putts.get(i-1) < min) {
                        min = putts.get(i-1);
                    }
                    average += putts.get(i-1);
                    entries.add(new Entry(i, putts.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(1.5f);
                dataSet.setCircleRadius(6f);
                dataSet.setCircleHoleRadius(3f);
                dataSet.setColor(getResources().getColor(R.color.darkgreen));
                dataSet.setCircleColor(Color.BLACK);
                dataSet.setCircleColorHole(getResources().getColor(R.color.darkgreen));

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float)putts.size();

                averageTv.setText(String.format(getString(R.string.average) + average));
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Greens": {
                for (int i = 1; i <= greens.size(); i++) {
                    if(greens.get(i-1) > max) {
                        max = greens.get(i-1);
                    }
                    if(greens.get(i-1) < min) {
                        min = greens.get(i-1);
                    }
                    average += greens.get(i-1);
                    entries.add(new Entry(i, greens.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(1.5f);
                dataSet.setCircleRadius(6f);
                dataSet.setCircleHoleRadius(3f);
                dataSet.setColor(getResources().getColor(R.color.darkgreen));
                dataSet.setCircleColor(Color.BLACK);
                dataSet.setCircleColorHole(getResources().getColor(R.color.darkgreen));

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float)putts.size();

                averageTv.setText(String.format(getString(R.string.average) + average));
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Fairways": {
                for (int i = 1; i <= fairways.size(); i++) {
                    if(fairways.get(i-1) > max) {
                        max = fairways.get(i-1);
                    }
                    if(fairways.get(i-1) < min) {
                        min = fairways.get(i-1);
                    }
                    average += fairways.get(i-1);
                    entries.add(new Entry(i, fairways.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(1.5f);
                dataSet.setCircleRadius(6f);
                dataSet.setCircleHoleRadius(3f);
                dataSet.setColor(getResources().getColor(R.color.darkgreen));
                dataSet.setCircleColor(Color.BLACK);
                dataSet.setCircleColorHole(getResources().getColor(R.color.darkgreen));

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float)putts.size();

                averageTv.setText(String.format(getString(R.string.average) + average));
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Relative Score": {
                for (int i = 1; i <= scoresToPar.size(); i++) {
                    if(scoresToPar.get(i-1) > max) {
                        max = scoresToPar.get(i-1);
                    }
                    if(scoresToPar.get(i-1) < min) {
                        min = scoresToPar.get(i-1);
                    }
                    average += scoresToPar.get(i-1);
                    entries.add(new Entry(i, scoresToPar.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(1.5f);
                dataSet.setCircleRadius(6f);
                dataSet.setCircleHoleRadius(3f);
                dataSet.setColor(getResources().getColor(R.color.darkgreen));
                dataSet.setCircleColor(Color.BLACK);
                dataSet.setCircleColorHole(getResources().getColor(R.color.darkgreen));

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float)putts.size();

                averageTv.setText(String.format(getString(R.string.average) + average));
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }
        }


    }

    private void setChartSettings() {
        lineChart.getXAxis().setGranularity(1f);
        lineChart.getAxisLeft().setGranularity(1f);
        lineChart.setDescription("");
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setTextSize(12);
        lineChart.getXAxis().setAxisMinValue(0);
        lineChart.getXAxis().setAxisMaxValue(10);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setDescriptionColor(Color.BLACK);
        lineChart.setBackgroundColor(getResources().getColor(R.color.grey));
    }


}
