package com.ryancase.golf_v3.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

public class ProfileFragment extends Fragment {

    private ProfileTabBinding binding;

    private ProfileViewModel viewModel;

    private List<RoundThing> rounds;

    private FirebaseAuth auth;

    private SharedPreferences preferences;

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
                // User chose the "Settings" item, show the app settings UI...
                Log.d("LOGOUT", "");

                binding.profileTable.setVisibility(View.GONE);
                preferences.edit().clear();
                preferences.edit().apply();
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getActivity(), LoginActivity.class));
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

        viewModel.setA(binding.progressA);

        preferences = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);

        loadProfileData();

        return retval;
    }

    private void loadProfileData() {
        Float scoAvg = preferences.getFloat("scoAvg", 0f);
        int totalStrokes = preferences.getInt("totalStrokes", 0);
        float roundsPlayed = preferences.getFloat("profileRoundsPlayed", 0);
        String currentGolfer = "Tiger Woods";

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            currentGolfer = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }

        if(Float.isNaN(scoAvg)) {
            binding.scoAvg.setText("No 18-Hole Rounds!");
        } else {
            binding.scoAvg.setText(String.valueOf(scoAvg));
        }

        binding.roundsPl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getA().setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.golfer.setText(currentGolfer);
        binding.roundsPl.setText(String.valueOf(roundsPlayed));
        binding.strokesTk.setText(String.valueOf(totalStrokes));

        Log.d("roundsPlayed: ", "" + roundsPlayed);
    }
}
