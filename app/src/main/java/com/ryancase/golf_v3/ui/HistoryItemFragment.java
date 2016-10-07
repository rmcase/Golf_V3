package com.ryancase.golf_v3.ui;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.RoundThing;
import com.ryancase.golf_v3.ViewModels.HistoryRoundViewModel;
import com.ryancase.golf_v3.databinding.FragmentHistoryRoundClickBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * File description here...
 */

public class HistoryItemFragment extends android.support.v4.app.Fragment implements HoleView {

    private RoundThing round;

    private FragmentHistoryRoundClickBinding binding;

    private HistoryRoundViewModel viewModel;

    private FragmentTabHost tabHost;

    private float fairwayPercentage, greenPercentage;
    private String driverRating, ironRating, approachRating;

    public HistoryItemFragment(RoundThing round) {
        this.round = round;
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
                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    HistoryFragment stat = new HistoryFragment();
                    fragmentTransaction.add(R.id.content_view, stat, "STAT");
                    fragmentTransaction.commit();
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
        View retval = inflater.inflate(R.layout.fragment_history_round_click, container, false);

        getActivity().setTitle(round.getCourse().toUpperCase() + "\t" + round.getDate());

        binding = DataBindingUtil.bind(retval);

        if (viewModel == null) {
            viewModel = new HistoryRoundViewModel();
        }
        binding.setViewModel(viewModel);

        int score = round.getBackNine().getScore() + round.getFrontNine().getScore();
        int frontScore = round.getFrontNine().getScore();
        int backScore = round.getBackNine().getScore();
        int scoreToPar = round.getFrontNine().getScoreToPar() + round.getBackNine().getScoreToPar();
        int par = round.getFrontNine().getPar() + round.getBackNine().getPar();
        int fairways = round.getBackNine().getFairways() + round.getFrontNine().getFairways();
        int greens = round.getBackNine().getGreens() + round.getFrontNine().getGreens();

        fairwayPercentage = findFairwayPercentage();
        greenPercentage = findGreenPercentage();

        if(round.getBackNine().getScore() == 0) {
            driverRating = getLetterGrade(round.getFrontNine().getAverageDriverRatingAsFloat());
            ironRating = getLetterGrade(round.getFrontNine().getAverageIronRatingAsFloat());
            approachRating = getLetterGrade(round.getFrontNine().getAverageApproachRatingAsFloat());
        } else if(round.getFrontNine().getScore() == 0) {
            driverRating = getLetterGrade(round.getBackNine().getAverageDriverRatingAsFloat());
            ironRating = getLetterGrade(round.getBackNine().getAverageIronRatingAsFloat());
            approachRating = getLetterGrade(round.getBackNine().getAverageApproachRatingAsFloat());
        } else {
            driverRating = getLetterGrade((round.getFrontNine().getAverageDriverRatingAsFloat() + round.getBackNine().getAverageDriverRatingAsFloat()) / 2f);
            ironRating = getLetterGrade((round.getBackNine().getAverageIronRatingAsFloat() + round.getFrontNine().getAverageIronRatingAsFloat()) / 2f);
            approachRating = getLetterGrade((round.getBackNine().getAverageApproachRatingAsFloat() + round.getFrontNine().getAverageApproachRatingAsFloat()) / 2f);
        }
        tabHost = binding.tabHost;
        tabHost.setup(getActivity(), getChildFragmentManager(), R.layout.driving_stat_tab);

        //OVERVIEW TAB
        Bundle overviewStats = new Bundle();
        overviewStats.putInt("score", score);
        overviewStats.putInt("frontScore", frontScore);
        overviewStats.putInt("backScore", backScore);
        overviewStats.putInt("scoreToPar", scoreToPar);
        overviewStats.putInt("par", par);
        overviewStats.putString("course", round.getCourse());
        overviewStats.putString("date", round.getDate());
        tabHost.addTab(tabHost.newTabSpec("OverviewTab").setIndicator("Overview"), OverviewTabFragment.class, overviewStats);
        //---------------

        //DRIVING TAB
        Bundle drivingStats = new Bundle();
        drivingStats.putString("driverRating", driverRating);
        drivingStats.putFloat("fairwayPercentage", fairwayPercentage);
        drivingStats.putInt("fairways", fairways);
        drivingStats.putInt("", 1);
        tabHost.addTab(tabHost.newTabSpec("DrivingTab").setIndicator("Driving"), DrivingTabFragment.class, drivingStats);
        //---------------

        //APPROACH TAB
        Bundle approachStats = new Bundle();
        approachStats.putString("ironRating", ironRating);
        approachStats.putString("approachRating", approachRating);
        approachStats.putFloat("greenPercentage", greenPercentage);
        approachStats.putInt("greens", greens);
//        approachStats.putInt("upDowns", 1);
        tabHost.addTab(tabHost.newTabSpec("ApproachTab").setIndicator("Approach"), ApproachTabFragment.class, approachStats);
        //---------------


        Bundle arg3 = new Bundle();
        arg3.putInt("", 3);
        tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Putt"), DrivingTabFragment.class, arg3);

        for(int i=0; i<4; i++) {
            TextView x = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            x.setTextSize(10);
        }

        return retval;
    }

    private float findGreenPercentage() {
        float retval;

        String frontNineGreenPercent = round.getFrontNine().getGreenPercentage().substring(0, round.getFrontNine().getGreenPercentage().length()-1);
        String backNineGreenPercent = round.getBackNine().getGreenPercentage().substring(0, round.getBackNine().getGreenPercentage().length()-1);

        if(round.getBackNine().getScore() == 0) {
            return Float.valueOf(frontNineGreenPercent);
        } else if(round.getFrontNine().getScore() == 0) {
            return Float.valueOf(backNineGreenPercent);
        }

        if(round.getBackNine().getGreenPercentage().equals("NaN%")) {
            if(!round.getFrontNine().getGreenPercentage().equals("NaN%")) {
                retval = Float.valueOf(frontNineGreenPercent);
            } else {
                retval = 0f;
            }
        } else {
            if(round.getFrontNine().equals("NaN%")) {
                retval = Float.valueOf(backNineGreenPercent);
            } else {
                retval = (Float.valueOf(frontNineGreenPercent) + Float.valueOf(backNineGreenPercent)) / 2f;
            }
        }

        return retval;
    }

    private float findFairwayPercentage() {
        float retval;

        String frontNineFairwayPercent = round.getFrontNine().getFairwayPercentage().substring(0, round.getFrontNine().getFairwayPercentage().length()-1);
        String backNineFairwayPercent = round.getBackNine().getFairwayPercentage().substring(0, round.getBackNine().getFairwayPercentage().length()-1);

        if(round.getBackNine().getScore() == 0) {
            return Float.valueOf(frontNineFairwayPercent);
        } else if(round.getFrontNine().getScore() == 0) {
            return Float.valueOf(backNineFairwayPercent);
        }

        if(round.getBackNine().getFairwayPercentage().equals("NaN%")) {
            if(!round.getFrontNine().getFairwayPercentage().equals("NaN%")) {
                retval = Float.valueOf(frontNineFairwayPercent);
            } else {
                retval = 0f;
            }
        } else {
            if(round.getFrontNine().equals("NaN%")) {
                retval = Float.valueOf(backNineFairwayPercent);
            } else {
                retval = (Float.valueOf(frontNineFairwayPercent) + Float.valueOf(backNineFairwayPercent)) / 2f;
            }
        }

        return retval;
    }

    private String formatDate(String fuckedUpDate) {
        String correctDate = "";

        try {
            SimpleDateFormat format = new SimpleDateFormat("M/D/yy");
            correctDate = format.format(format.parse(fuckedUpDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return correctDate;
    }

    private String getLetterGrade(float average) {
        String retval;

        if(average <= 1) {
            retval = "A";
        } else if(average > 1 && average <= 1.30) {
            retval = "A-";
        } else if(average > 1.30 && average <= 1.75) {
            retval = "B+";
        } else if(average > 1.75 && average <= 2) {
            retval = "B";
        } else if(average > 2  && average <= 2.30) {
            retval = "B-";
        } else if(average > 2.30 && average <= 2.75) {
            retval = "C+";
        } else if(average > 2.75 && average <= 3) {
            retval = "C";
        } else if(average > 3 && average <= 3.30) {
            retval = "C-";
        } else {
            retval = "D";
        }

        return retval;
    }

}