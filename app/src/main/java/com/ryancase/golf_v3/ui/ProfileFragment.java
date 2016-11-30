package com.ryancase.golf_v3.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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

        getActivity().setTitle("Profile");

        binding = DataBindingUtil.bind(retval);
        if (viewModel == null) {
            viewModel = new ProfileViewModel();
        }
        binding.setViewModel(viewModel);

        rounds = new ArrayList<>();

        auth = FirebaseAuth.getInstance();

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profileTable.setVisibility(View.GONE);
                auth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        loadRounds();

        return retval;
    }

    private void loadRounds() {
        database = FirebaseDatabase.getInstance().getReference();
        Query roundQue = database.child("Rounds").orderByChild("roundId").equalTo(Round.getRoundId());

        roundQue.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RoundThing round = dataSnapshot.getValue(RoundThing.class);
                rounds.add(round);

                int totalStrokes = 0;
                float scoAvg = 0;
                int roundsPlayed = 0;

                Log.d("ROUNDS", "" + rounds.size());

                for (RoundThing r : rounds) {
                    totalStrokes += (r.getBackNine().getScore() + r.getFrontNine().getScore());
                }
                scoAvg = (float) totalStrokes / rounds.size();
                roundsPlayed = rounds.size();

                viewModel.setRoundsPlayed(String.valueOf(roundsPlayed));
                viewModel.setTotalStrokes(String.valueOf(totalStrokes));
                viewModel.setScoringAverage(String.valueOf(scoAvg));

                Log.d("SCOAVG", "" + scoAvg);
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
}
