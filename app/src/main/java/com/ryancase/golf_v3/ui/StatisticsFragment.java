package com.ryancase.golf_v3.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
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
    private TextView averageTv, minTv, maxTv;
    private List<Integer> scores, putts, greens, fairways, scoresToPar,
            scrambling, pars, birdies, eagles;

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

        picker.setDisplayedValues(new String[]{"Score", "Score To Par", "Putts", "Greens", "Fairways", "Scrambling", "Pars", "Birdies", "Eagles"});
        picker.setMinValue(0);
        picker.setMaxValue(8);
        picker.setValue(0);

    }

    private void bindViewModelElements() {
        lineChart = binding.chart;
        picker = binding.graphPicker;
        averageTv = binding.averageTv;
        minTv = binding.minTv;
        maxTv = binding.maxTv;
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

        createChart("Score");

        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 0) {
                    createChart("Score");
                }
                if (newVal == 1) {
                    createChart("Relative Score");
                }
                if (newVal == 2) {
                    createChart("Putts");
                }
                if (newVal == 3) {
                    createChart("Greens");
                }
                if (newVal == 4) {
                    createChart("Fairways");
                }
                if (newVal == 5) {
                    createChart("Scrambling");
                }
                if (newVal == 5) {
                    createChart("Pars");
                }
                if (newVal == 5) {
                    createChart("Birdies");
                }
                if (newVal == 5) {
                    createChart("Eagles");
                }
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
                    if (scores.get(i - 1) > max) {
                        max = scores.get(i - 1);
                    }
                    if (scores.get(i - 1) < min) {
                        min = scores.get(i - 1);
                    }
                    average += scores.get(i - 1);
                    entries.add(new Entry(i, scores.get(i - 1)));
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

                average = average / (float) scores.size();


                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Putts": {
                for (int i = 1; i <= putts.size(); i++) {
                    if (putts.get(i - 1) > max) {
                        max = putts.get(i - 1);
                    }
                    if (putts.get(i - 1) < min) {
                        min = putts.get(i - 1);
                    }
                    average += putts.get(i - 1);
                    entries.add(new Entry(i, putts.get(i - 1)));
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

                average = average / (float) putts.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Greens": {
                for (int i = 1; i <= greens.size(); i++) {
                    if (greens.get(i - 1) > max) {
                        max = greens.get(i - 1);
                    }
                    if (greens.get(i - 1) < min) {
                        min = greens.get(i - 1);
                    }
                    average += greens.get(i - 1);
                    entries.add(new Entry(i, greens.get(i - 1)));
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

                average = average / (float) greens.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Fairways": {
                for (int i = 1; i <= fairways.size(); i++) {
                    if (fairways.get(i - 1) > max) {
                        max = fairways.get(i - 1);
                    }
                    if (fairways.get(i - 1) < min) {
                        min = fairways.get(i - 1);
                    }
                    average += fairways.get(i - 1);
                    entries.add(new Entry(i, fairways.get(i - 1)));
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

                average = average / (float) fairways.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(String.format(getString(R.string.min) + min));
                maxTv.setText(String.format(getString(R.string.max) + max));

                break;
            }

            case "Relative Score": {
                for (int i = 1; i <= scoresToPar.size(); i++) {
                    if (scoresToPar.get(i - 1) > max) {
                        max = scoresToPar.get(i - 1);
                    }
                    if (scoresToPar.get(i - 1) < min) {
                        min = scoresToPar.get(i - 1);
                    }
                    average += scoresToPar.get(i - 1);
                    entries.add(new Entry(i, scoresToPar.get(i - 1)));
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

                average = average / (float) scoresToPar.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(getString(R.string.min) + min);
                maxTv.setText(getString(R.string.max) + max);

                break;
            }

            case "Scrambling": {
                for (int i = 1; i <= scrambling.size(); i++) {
                    if (scrambling.get(i - 1) > max) {
                        max = scrambling.get(i - 1);
                    }
                    if (scrambling.get(i - 1) < min) {
                        min = scrambling.get(i - 1);
                    }
                    average += scrambling.get(i - 1);
                    entries.add(new Entry(i, scrambling.get(i - 1)));
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

                average = average / (float) scrambling.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(getString(R.string.min) + min);
                maxTv.setText(getString(R.string.max) + max);

                break;
            }

            case "Pars": {
                for (int i = 1; i <= pars.size(); i++) {
                    if (pars.get(i - 1) > max) {
                        max = pars.get(i - 1);
                    }
                    if (pars.get(i - 1) < min) {
                        min = pars.get(i - 1);
                    }
                    average += pars.get(i - 1);
                    entries.add(new Entry(i, pars.get(i - 1)));
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

                average = average / (float) pars.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(getString(R.string.min) + min);
                maxTv.setText(getString(R.string.max) + max);

                break;
            }

            case "Birdies": {
                for (int i = 1; i <= birdies.size(); i++) {
                    if (birdies.get(i - 1) > max) {
                        max = birdies.get(i - 1);
                    }
                    if (birdies.get(i - 1) < min) {
                        min = birdies.get(i - 1);
                    }
                    average += birdies.get(i - 1);
                    entries.add(new Entry(i, birdies.get(i - 1)));
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

                average = average / (float) birdies.size();

                String averageStr = String.format(Locale.getDefault(), "%.2f", average);

                averageTv.setText(getString(R.string.average) + averageStr);
                minTv.setText(getString(R.string.min) + min);
                maxTv.setText(getString(R.string.max) + max);

                break;
            }

            case "Eagles": {
                for (int i = 1; i <= eagles.size(); i++) {
                    if (eagles.get(i - 1) > max) {
                        max = eagles.get(i - 1);
                    }
                    if (eagles.get(i - 1) < min) {
                        min = eagles.get(i - 1);
                    }
                    average += eagles.get(i - 1);
                    entries.add(new Entry(i, eagles.get(i - 1)));
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

                average = average / (float) eagles.size();

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
