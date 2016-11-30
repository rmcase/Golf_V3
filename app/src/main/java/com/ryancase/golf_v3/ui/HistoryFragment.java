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
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.RoundThing;
import com.ryancase.golf_v3.ViewModels.HistoryViewModel;
import com.ryancase.golf_v3.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * File description here...
 */

public class HistoryFragment extends android.support.v4.app.Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private FragmentHistoryBinding binding;

    private HistoryViewModel viewModel;

    private List<RoundThing> roundThings;

    private List<String> dates;

    private List<Integer> scores;

    private DatabaseReference database;

    private ProgressBar progressBar;

    public HistoryFragment() {
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
        setHasOptionsMenu(false);

        getActivity().setTitle(R.string.history);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.fragment_history, container, false);

        getActivity().setTitle(R.string.history);

        binding = DataBindingUtil.bind(retval);


        if (viewModel == null) {
            viewModel = new HistoryViewModel();
        }
        binding.setViewModel(viewModel);

        bindViewModelElements();

        populateViewModelElements();

        loadRounds();

        return retval;
    }

    private void populateViewModelElements() {
        roundThings = new ArrayList<>();
        dates = new ArrayList<>();
        scores = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
    }

    private void bindViewModelElements() {
       viewModel.setHistoryList(binding.historyList);
        progressBar = binding.loadingIndicator;

    }

    private void loadRounds() {
        database = FirebaseDatabase.getInstance().getReference();
        Query roundQuery = database.child("Rounds").orderByChild("roundId").equalTo(Round.getRoundId());

        roundQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                RoundThing round = dataSnapshot.getValue(RoundThing.class);

                roundThings.add(round);

                progressBar.setVisibility(View.GONE);
                Log.d("THEMOFO:", "" + round.getDate());

                int score = round.getBackNine().getScore() + round.getFrontNine().getScore();

                dates.add(String.format(score + "\t\t\t" + round.getCourse().toUpperCase() + "\t\t\t\t\t" + round.getDate()));

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.history_list_item, dates);

                viewModel.getHistoryList().setAdapter(adapter);

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

        viewModel.getHistoryList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoundThing roundSelected = roundThings.get(position);

                viewModel.getHistoryList().setVisibility(View.GONE);

                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HistoryItemFragment stat = new HistoryItemFragment(roundSelected);
                fragmentTransaction.add(R.id.content_view_history, stat, "STAT");
                fragmentTransaction.commit();
            }
        });
    }


}
