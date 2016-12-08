package com.ryancase.golf_v3.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.gson.Gson;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
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

    private DatabaseReference database;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profileTable.setVisibility(View.GONE);
                auth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        loadProfileData();

        return retval;
    }

    private void loadProfileData() {
        Float scoAvg = preferences.getFloat("scoAvg", 0f);
        int totalStrokes = preferences.getInt("totalStrokes", 0);
        int roundsPlayed = preferences.getInt("roundsPlayed", 0);

        binding.scoAvg.setText(String.valueOf(scoAvg));
        binding.roundsPl.setText(String.valueOf(roundsPlayed));
        binding.strokesTk.setText(String.valueOf(totalStrokes));

        Log.d("roundPlayed: ", "" + roundsPlayed);
    }
}
