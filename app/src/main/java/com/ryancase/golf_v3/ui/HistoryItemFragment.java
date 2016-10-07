package com.ryancase.golf_v3.ui;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
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

        tabHost = binding.tabHost;
        tabHost.setup(getActivity(), getChildFragmentManager(), R.layout.driving_stat_tab);

        Bundle overviewStats = new Bundle();
        overviewStats.putInt("score", score);
        overviewStats.putInt("frontScore", frontScore);
        overviewStats.putInt("backScore", backScore);
        overviewStats.putInt("scoreToPar", scoreToPar);
        overviewStats.putInt("par", par);
        tabHost.addTab(tabHost.newTabSpec("OverviewTab").setIndicator("Overview"), OverviewTabFragment.class, overviewStats);

        Bundle drivingStats = new Bundle();
        drivingStats.putInt("", 1);
        tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Driving"), DrivingTabFragment.class, drivingStats);

        Bundle approachStats = new Bundle();
        approachStats.putInt("", 2);
        tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Approach"), DrivingTabFragment.class, approachStats);

        Bundle arg3 = new Bundle();
        arg3.putInt("", 3);
        tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Putt"), DrivingTabFragment.class, arg3);

        for(int i=0; i<4; i++) {
            TextView x = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            x.setTextSize(10);
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

}
