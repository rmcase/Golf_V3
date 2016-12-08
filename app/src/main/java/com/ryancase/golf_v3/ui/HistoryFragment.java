package com.ryancase.golf_v3.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.gson.Gson;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.RoundObject;
import com.ryancase.golf_v3.RoundThing;
import com.ryancase.golf_v3.ViewModels.HistoryViewModel;
import com.ryancase.golf_v3.databinding.FragmentHistoryBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * File description here...
 */

public class HistoryFragment extends android.support.v4.app.Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private FragmentHistoryBinding binding;

    private HistoryViewModel viewModel;

    private List<String> dates;

    private List<Integer> scores;

    private List<RoundThing> rounds;

    private SharedPreferences preferences;

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

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.fragment_history, container, false);

        binding = DataBindingUtil.bind(retval);

        preferences = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);

        if (viewModel == null) {
            viewModel = new HistoryViewModel();
        }
        binding.setViewModel(viewModel);

        bindViewModelElements();

        populateViewModelElements();

        loadHistoryData();

        return retval;
    }

    private void populateViewModelElements() {
        dates = new ArrayList<>();
        scores = new ArrayList<>();
        rounds = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
    }

    private void bindViewModelElements() {
        viewModel.setHistoryList(binding.historyList);
        progressBar = binding.loadingIndicator;

    }

    private void loadHistoryData() {
        int numberOfRoundsToLoad;
        numberOfRoundsToLoad = preferences.getInt("roundsPlayed", 0);

        Gson gson = new Gson();

        for (int i = 0; i < numberOfRoundsToLoad; i++) {
            String objectToLoad = preferences.getString("round" + i, "");
            rounds.add(gson.fromJson(objectToLoad, RoundThing.class));

            String newDate = "";
            SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy");
            try {
                Date date = sd.parse(rounds.get(i).getDate());

                newDate = sd.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dates.add(rounds.get(i).getCourse().toUpperCase() + "\t\t\t\t\t" + newDate);
        }

        progressBar.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.history_list_item, dates);

        viewModel.getHistoryList().setAdapter(adapter);

        viewModel.getHistoryList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoundThing roundSelected = rounds.get(position);

                viewModel.getHistoryList().setVisibility(View.GONE);

                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HistoryItemFragment stat = new HistoryItemFragment(roundSelected);
                fragmentTransaction.add(R.id.content_view_history, stat, "STAT");
                fragmentTransaction.commit();
            }
        });
    }

//    private void loadRounds() {
//        roundThings = new ArrayList<>(RoundObject.getHistoryListRounds());
//
//        progressBar.setVisibility(View.GONE);
//
//        for (RoundThing r : roundThings) {
//            String newDate = "";
//            SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy");
//            try {
//                Date date = sd.parse(r.getDate());
//
//                newDate = sd.format(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            dates.add(String.format(r.getCourse().toUpperCase() + "\t\t\t\t\t" + newDate));
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.history_list_item, dates);
//
//        viewModel.getHistoryList().setAdapter(adapter);
//
//        viewModel.getHistoryList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                RoundThing roundSelected = roundThings.get(position);
//
//                viewModel.getHistoryList().setVisibility(View.GONE);
//
//                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                HistoryItemFragment stat = new HistoryItemFragment(roundSelected);
//                fragmentTransaction.add(R.id.content_view_history, stat, "STAT");
//                fragmentTransaction.commit();
//            }
//        });
//    }

}
