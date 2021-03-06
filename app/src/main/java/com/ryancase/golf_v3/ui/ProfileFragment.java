package com.ryancase.golf_v3.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.RoundThing;
import com.ryancase.golf_v3.ViewModels.ProfileViewModel;
import com.ryancase.golf_v3.databinding.ProfileTabBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryancase on 10/6/16.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private ProfileTabBinding binding;

    private ProfileViewModel viewModel;

    private List<RoundThing> rounds;

    private FirebaseAuth auth;

    private float scoAvg, nineScoAvg;

    private int allTimeScoreToPar, allTimeScoreToParNine;

    private String allTimeScoreToParStr, allTimeScoreToParNineStr;

    private SharedPreferences preferences;

    private boolean eighteenShowing;

    public ProfileFragment() {

    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // User chose the "Logout" item, show the app settings UI...
                Log.d("LOGOUT", "");

                binding.profileTable.setVisibility(View.GONE);
                preferences.edit().clear();
                preferences.edit().apply();
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getActivity(), SplashActivity.class));
                return true;

            case R.id.action_help:

                startActivity(new Intent(getActivity(), HelpActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.options, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.profile_tab, container, false);

        binding = DataBindingUtil.bind(retval);
        if (viewModel == null) {
            viewModel = new ProfileViewModel();
        }
        binding.setViewModel(viewModel);

        rounds = new ArrayList<>();

        auth = FirebaseAuth.getInstance();

        preferences = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);

        eighteenShowing = true;

        binding.scoAvgTv.setOnClickListener(this);
        binding.scoreToParTv.setOnClickListener(this);
        binding.scoAvg.setOnClickListener(this);
        binding.scoreToPar.setOnClickListener(this);

        loadProfileData();

        return retval;
    }

    private void loadProfileData() {
        scoAvg = preferences.getFloat("scoAvg", 0f);
        nineScoAvg = preferences.getFloat("nineScoAvg", 0f);
        allTimeScoreToPar = preferences.getInt("allTimeScoreToPar", 0);
        allTimeScoreToParNine = preferences.getInt("allTimeScoreToParNine", 0);
        int totalStrokes = preferences.getInt("totalStrokes", 0);
        int fullRounds = preferences.getInt("fullRounds", 0);
        int halfRounds = preferences.getInt("halfRounds", 0);
        String currentGolfer = "Tiger Woods";

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            currentGolfer = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }

        if(allTimeScoreToPar > 0) {
            allTimeScoreToParStr = "+" + String.valueOf(allTimeScoreToPar);
        } else {
            allTimeScoreToParStr = String.valueOf(allTimeScoreToPar);
        }

        if(allTimeScoreToPar == 0) {
            allTimeScoreToParStr = "Even";
        }

        if(allTimeScoreToParNine > 0 ) {
            allTimeScoreToParNineStr = "+" + String.valueOf(allTimeScoreToParNine);
        } else {
            allTimeScoreToParNineStr = String.valueOf(allTimeScoreToParNine);
        }

        if(allTimeScoreToParNine == 0) {
            allTimeScoreToParNineStr = "Even";
        }

        if(Float.isNaN(scoAvg) || scoAvg < 10.0f) {
            binding.scoAvg.setText("No Rounds!");
        } else {
            binding.scoAvg.setText(String.valueOf(scoAvg));
        }

        binding.golfer.setText(currentGolfer);
        binding.roundsPl.setText(String.valueOf(fullRounds) + "  |  " + String.valueOf(halfRounds));
        binding.strokesTk.setText(String.valueOf(totalStrokes));
        binding.scoreToPar.setText(allTimeScoreToParStr);
    }

    public void onClick(View v) {
        String otherScoringAverage = "Scoring Average (9):";
        String otherScoreToPar = "Score To Par (9)";
        if(binding.scoAvgTv.getText() != otherScoringAverage) {
            eighteenShowing = false;

            binding.scoAvgTv.setText(otherScoringAverage);
            if(Float.isNaN(nineScoAvg) || scoAvg < 10.0f) {
                binding.scoAvg.setText("No Rounds!");
            } else {
                binding.scoAvg.setText(String.valueOf(nineScoAvg));
            }

            if(nineScoAvg == 0) {
                allTimeScoreToParNineStr = "Even";
            }
            binding.scoreToParTv.setText(otherScoreToPar);
            binding.scoreToPar.setText(allTimeScoreToParNineStr);
        } else {
            eighteenShowing = true;

            otherScoringAverage = "Scoring Average (18):";
            binding.scoAvgTv.setText(otherScoringAverage);
            if(Float.isNaN(scoAvg) || scoAvg < 10.0f) {
                binding.scoAvg.setText("No Rounds!");
            } else {
                binding.scoAvg.setText(String.valueOf(scoAvg));
            }

            otherScoreToPar = "Score To Par (18):";
            binding.scoreToParTv.setText(otherScoreToPar);
            binding.scoreToPar.setText(allTimeScoreToParStr);
        }
    }

    private int getScoreToParTextColor(boolean eighteenShowing) {
        int retval = Color.GRAY;

        if(eighteenShowing) {
            if (allTimeScoreToPar < 0) {
                retval = getResources().getColor(R.color.red);
            } else if (allTimeScoreToPar == 0) {
                retval = getResources().getColor(R.color.fTeal);
            } else if (allTimeScoreToPar > 0) {
                retval = Color.GRAY;
            }
        } else {
            if (allTimeScoreToParNine < 0) {
                retval = getResources().getColor(R.color.red);
            } else if (allTimeScoreToParNine == 0) {
                retval = getResources().getColor(R.color.fTeal);
            } else if (allTimeScoreToParNine > 0) {
                retval = Color.GRAY;
            }
        }
        return retval;
    }
}
