package com.ryancase.golf_v3.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.RoundThing;
import com.ryancase.golf_v3.ViewModels.StatisticsViewModel;
import com.ryancase.golf_v3.databinding.FragmentStatisticsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * File description here...
 */

public class StatisticsFragment extends android.support.v4.app.Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";
    private final float circleRadius = 5.75f;
    private final float lineWidth = 1.5f;
    private final float circleHoleRadius = 4.25f;

    private int lineColor;
    private int circleColor, circleOutlineColor;
    private StatisticsViewModel viewModel;

    private FragmentStatisticsBinding binding;

    private SharedPreferences preferences;
    private DatabaseReference database;
    private List<RoundThing> previousRounds;
    private LineChart lineChart;
    private NumberPicker picker;
    private String[] pickerStrings = {"Score", "Score To Par", "Putts", "Greens", "Fairways", "Scrambling", "Pars", "Birdies", "Eagles"};
    private Switch theSwitch;
    private TextView averageTv, minTv, maxTv;
    private List<Integer> scores, putts, greens, fairways, scoresToPar,
            scrambling, pars, birdies, eagles;
    private List<Integer> scores9, putts9, greens9, fairways9, scoresToPar9,
            scrambling9, pars9, birdies9, eagles9;
    private boolean checked;

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

        binding = DataBindingUtil.bind(retval);

        preferences = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);

        if (viewModel == null) {
            viewModel = new StatisticsViewModel();
        }
        binding.setViewModel(viewModel);

        circleOutlineColor = getResources().getColor(R.color.white);
        lineColor = getResources().getColor(R.color.fDarkPurple);
        circleColor = getResources().getColor(R.color.fDarkTeal);

        bindViewModelElements();

        populateViewModelElements();

        loadStatisticsData();

        return retval;
    }

    private void populateViewModelElements() {
        previousRounds = new ArrayList<>();
        scores = new ArrayList<>();
        putts = new ArrayList<>();
        greens = new ArrayList<>();
        fairways = new ArrayList<>();
        scoresToPar = new ArrayList<>();
        scrambling = new ArrayList<>();
        eagles = new ArrayList<>();
        birdies = new ArrayList<>();
        pars = new ArrayList<>();

        scores9 = new ArrayList<>();
        putts9 = new ArrayList<>();
        greens9 = new ArrayList<>();
        fairways9 = new ArrayList<>();
        scoresToPar9 = new ArrayList<>();
        scrambling9 = new ArrayList<>();
        eagles9 = new ArrayList<>();
        birdies9 = new ArrayList<>();
        pars9 = new ArrayList<>();

        picker.setDisplayedValues(pickerStrings);
        picker.setMinValue(0);
        picker.setMaxValue(8);
        picker.setValue(0);

        checked = false;
    }

    private void bindViewModelElements() {
        lineChart = binding.chart;
        picker = binding.graphPicker;
        averageTv = binding.averageTv;
        minTv = binding.minTv;
        maxTv = binding.maxTv;
        theSwitch = binding.theSwitch;
    }

    private void loadStatisticsData() {
        int numberOfRoundsToLoad;
        numberOfRoundsToLoad = preferences.getInt("roundsPlayed", 0);

        Gson gson = new Gson();

        for (int i = 0; i < numberOfRoundsToLoad; i++) {
            String objectToLoad = preferences.getString("round" + i, "");
            previousRounds.add(gson.fromJson(objectToLoad, RoundThing.class));
        }

        for(RoundThing round : previousRounds) {
            if(round.getBackNine().getScore() == 0 || round.getFrontNine().getScore() == 0) {
                int sc = round.getBackNine().getScore() + round.getFrontNine().getScore();
                int pu = round.getBackNine().getPutts() + round.getFrontNine().getPutts();
                int gr = round.getBackNine().getGreens() + round.getFrontNine().getGreens();
                int fa = round.getBackNine().getFairways() + round.getFrontNine().getFairways();
                int rs = round.getBackNine().getScoreToPar() + round.getFrontNine().getScoreToPar();
                int scr = round.getBackNine().getScrambling() + round.getFrontNine().getScrambling();
                int bi = round.getBackNine().getBirdies() + round.getFrontNine().getBirdies();
                int pr = round.getBackNine().getPars() + round.getFrontNine().getPars();
                int eag = round.getBackNine().getEagles() + round.getFrontNine().getEagles();

                scoresToPar9.add(rs);
                fairways9.add(fa);
                greens9.add(gr);
                putts9.add(pu);
                scores9.add(sc);
                scrambling9.add(scr);
                eagles9.add(eag);
                birdies9.add(bi);
                pars9.add(pr);
            } else {
                int score = round.getBackNine().getScore() + round.getFrontNine().getScore();
                int putt = round.getBackNine().getPutts() + round.getFrontNine().getPutts();
                int green = round.getBackNine().getGreens() + round.getFrontNine().getGreens();
                int fairway = round.getBackNine().getFairways() + round.getFrontNine().getFairways();
                int relScore = round.getBackNine().getScoreToPar() + round.getFrontNine().getScoreToPar();
                int scrmblng = round.getBackNine().getScrambling() + round.getFrontNine().getScrambling();
                int birdie = round.getBackNine().getBirdies() + round.getFrontNine().getBirdies();
                int par = round.getBackNine().getPars() + round.getFrontNine().getPars();
                int eagle = round.getBackNine().getEagles() + round.getFrontNine().getEagles();

                scoresToPar.add(relScore);
                fairways.add(fairway);
                greens.add(green);
                putts.add(putt);
                scores.add(score);
                scrambling.add(scrmblng);
                eagles.add(eagle);
                birdies.add(birdie);
                pars.add(par);
            }
        }

        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    checked = true;
                    createChart(pickerStrings[picker.getValue()]);
                } else {
                    checked = false;
                    createChart(pickerStrings[picker.getValue()]);
                }
            }
        });

        createChart("Score");

        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                createChart(pickerStrings[picker.getValue()]);
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
                List<Integer> graphScores;
                if(checked) {
                    graphScores = scores9;
                } else {
                    graphScores = scores;
                }
                for (int i = 1; i <= graphScores.size(); i++) {
                    if (graphScores.get(i - 1) > max) {
                        max = graphScores.get(i - 1);
                    }
                    if (graphScores.get(i - 1) < min) {
                        min = graphScores.get(i - 1);
                    }
                    average += graphScores.get(i - 1);
                    entries.add(new Entry(i, graphScores.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(lineWidth);
                dataSet.setCircleRadius(circleRadius);
                dataSet.setCircleHoleRadius(circleHoleRadius);
                dataSet.setColor(lineColor);
                dataSet.setCircleColor(circleOutlineColor);
                dataSet.setCircleColorHole(circleColor);

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float) graphScores.size();


                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Score To Par": {
                List<Integer> graphRelScores;
                if(checked) {
                    graphRelScores = scoresToPar9;
                } else {
                    graphRelScores = scoresToPar;
                }
                for (int i = 1; i <= graphRelScores.size(); i++) {
                    if (graphRelScores.get(i - 1) > max) {
                        max = graphRelScores.get(i - 1);
                    }
                    if (graphRelScores.get(i - 1) < min) {
                        min = graphRelScores.get(i - 1);
                    }
                    average += graphRelScores.get(i - 1);
                    entries.add(new Entry(i, graphRelScores.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(lineWidth);
                dataSet.setCircleRadius(circleRadius);
                dataSet.setCircleHoleRadius(circleHoleRadius);
                dataSet.setColor(lineColor);
                dataSet.setCircleColor(circleOutlineColor);
                dataSet.setCircleColorHole(circleColor);

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float) graphRelScores.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(getString(R.string.min) + min);
                maxTv.setText(getString(R.string.max) + max);

                break;
            }

            case "Putts": {
                List<Integer> graphPutts;
                if(checked) {
                    graphPutts = putts9;
                } else {
                    graphPutts = putts;
                }
                for (int i = 1; i <= graphPutts.size(); i++) {
                    if (graphPutts.get(i - 1) > max) {
                        max = graphPutts.get(i - 1);
                    }
                    if (graphPutts.get(i - 1) < min) {
                        min = graphPutts.get(i - 1);
                    }
                    average += graphPutts.get(i - 1);
                    entries.add(new Entry(i, graphPutts.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(lineWidth);
                dataSet.setCircleRadius(circleRadius);
                dataSet.setCircleHoleRadius(circleHoleRadius);
                dataSet.setColor(lineColor);
                dataSet.setCircleColor(circleOutlineColor);
                dataSet.setCircleColorHole(circleColor);

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float) graphPutts.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Greens": {
                List<Integer> graphGreens;
                if(checked) {
                    graphGreens = greens9;
                } else {
                    graphGreens = greens;
                }
                for (int i = 1; i <= graphGreens.size(); i++) {
                    if (graphGreens.get(i - 1) > max) {
                        max = graphGreens.get(i - 1);
                    }
                    if (graphGreens.get(i - 1) < min) {
                        min = graphGreens.get(i - 1);
                    }
                    average += graphGreens.get(i - 1);
                    entries.add(new Entry(i, graphGreens.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(lineWidth);
                dataSet.setCircleRadius(circleRadius);
                dataSet.setCircleHoleRadius(circleHoleRadius);
                dataSet.setColor(lineColor);
                dataSet.setCircleColor(circleOutlineColor);
                dataSet.setCircleColorHole(circleColor);

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float) graphGreens.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Fairways": {
                List<Integer> graphFairways;
                if(checked) {
                    graphFairways = fairways9;
                } else {
                    graphFairways = fairways;
                }
                for (int i = 1; i <= graphFairways.size(); i++) {
                    if (graphFairways.get(i - 1) > max) {
                        max = graphFairways.get(i - 1);
                    }
                    if (graphFairways.get(i - 1) < min) {
                        min = graphFairways.get(i - 1);
                    }
                    average += graphFairways.get(i - 1);
                    entries.add(new Entry(i, graphFairways.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(lineWidth);
                dataSet.setCircleRadius(circleRadius);
                dataSet.setCircleHoleRadius(circleHoleRadius);
                dataSet.setColor(lineColor);
                dataSet.setCircleColor(circleOutlineColor);
                dataSet.setCircleColorHole(circleColor);

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float) graphFairways.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Scrambling": {
                List<Integer> graphScrambling;
                if(checked) {
                    graphScrambling = scrambling9;
                } else {
                    graphScrambling = scrambling;
                }
                for (int i = 1; i <= graphScrambling.size(); i++) {
                    if (graphScrambling.get(i - 1) > max) {
                        max = graphScrambling.get(i - 1);
                    }
                    if (graphScrambling.get(i - 1) < min) {
                        min = graphScrambling.get(i - 1);
                    }
                    average += graphScrambling.get(i - 1);
                    entries.add(new Entry(i, graphScrambling.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(lineWidth);
                dataSet.setCircleRadius(circleRadius);
                dataSet.setCircleHoleRadius(circleHoleRadius);
                dataSet.setColor(lineColor);
                dataSet.setCircleColor(circleOutlineColor);
                dataSet.setCircleColorHole(circleColor);

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float) graphScrambling.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(getString(R.string.min) + min);
                maxTv.setText(getString(R.string.max) + max);

                break;
            }

            case "Pars": {
                List<Integer> graphPars;
                if(checked) {
                    graphPars = pars9;
                } else {
                    graphPars = pars;
                }
                for (int i = 1; i <= graphPars.size(); i++) {
                    if (graphPars.get(i - 1) > max) {
                        max = graphPars.get(i - 1);
                    }
                    if (graphPars.get(i - 1) < min) {
                        min = graphPars.get(i - 1);
                    }
                    average += graphPars.get(i - 1);
                    entries.add(new Entry(i, graphPars.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(lineWidth);
                dataSet.setCircleRadius(circleRadius);
                dataSet.setCircleHoleRadius(circleHoleRadius);
                dataSet.setColor(lineColor);
                dataSet.setCircleColor(circleOutlineColor);
                dataSet.setCircleColorHole(circleColor);

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float) graphPars.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(getString(R.string.min) + min);
                maxTv.setText(getString(R.string.max) + max);

                break;
            }

            case "Birdies": {
                List<Integer> graphBirdies;
                if(checked) {
                    graphBirdies = birdies9;
                } else {
                    graphBirdies = birdies;
                }
                for (int i = 1; i <= graphBirdies.size(); i++) {
                    if (graphBirdies.get(i - 1) > max) {
                        max = graphBirdies.get(i - 1);
                    }
                    if (graphBirdies.get(i - 1) < min) {
                        min = graphBirdies.get(i - 1);
                    }
                    average += graphBirdies.get(i - 1);
                    entries.add(new Entry(i, graphBirdies.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(lineWidth);
                dataSet.setCircleRadius(circleRadius);
                dataSet.setCircleHoleRadius(circleHoleRadius);
                dataSet.setColor(lineColor);
                dataSet.setCircleColor(circleOutlineColor);
                dataSet.setCircleColorHole(circleColor);

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float) graphBirdies.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(getString(R.string.min) + min);
                maxTv.setText(getString(R.string.max) + max);

                break;
            }

            case "Eagles": {
                List<Integer> graphEagles;
                if(checked) {
                    graphEagles = eagles9;
                } else {
                    graphEagles = eagles;
                }
                for (int i = 1; i <= graphEagles.size(); i++) {
                    if (graphEagles.get(i - 1) > max) {
                        max = graphEagles.get(i - 1);
                    }
                    if (graphEagles.get(i - 1) < min) {
                        min = graphEagles.get(i - 1);
                    }
                    average += graphEagles.get(i - 1);
                    entries.add(new Entry(i, graphEagles.get(i - 1)));
                }
                LineDataSet dataSet = new LineDataSet(entries, value);
                dataSet.setDrawValues(false);
                dataSet.setLineWidth(lineWidth);
                dataSet.setCircleRadius(circleRadius);
                dataSet.setCircleHoleRadius(circleHoleRadius);
                dataSet.setColor(lineColor);
                dataSet.setCircleColor(circleOutlineColor);
                dataSet.setCircleColorHole(circleColor);

                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                average = average / (float) graphEagles.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(getString(R.string.min) + min);
                maxTv.setText(getString(R.string.max) + max);

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
        lineChart.setBackgroundColor(getResources().getColor(R.color.white));
    }


}
