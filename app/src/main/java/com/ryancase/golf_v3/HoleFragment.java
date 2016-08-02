package com.ryancase.golf_v3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryancase.golf_v3.databinding.FragmentHoleBinding;

import java.util.Arrays;


/**
 * File description here...
 */

public class HoleFragment extends Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private final Hole[] holes = new Hole[9];

    private int holeNum;
    private FragmentHoleBinding binding;

    private HoleViewModel viewModel;

    private Round round;
    private Hole hole;

    public HoleFragment(int holeNum, Round round) {
        hole = new Hole();

        this.round = round;

        this.holeNum = holeNum;
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
        View retval = inflater.inflate(R.layout.fragment_hole, container, false);

        binding = DataBindingUtil.bind(retval);


        if (viewModel == null) {
            viewModel = new HoleViewModel(this);
        }
        binding.setViewModel(viewModel);


        viewModel.setTitle("Hole " + holeNum);

        viewModel.setScoreSelect(binding.scoreSelector);
        viewModel.getScoreSelect().setMinValue(1);
        viewModel.getScoreSelect().setMaxValue(10);

        viewModel.setParSelect(binding.parSelector);
        viewModel.getParSelect().setMinValue(3);
        viewModel.getParSelect().setMaxValue(5);

        viewModel.setNextHoleButton(binding.nextHole);
        viewModel.getNextHoleButton().setText(R.string.next_hole);
        viewModel.getNextHoleButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hole.setScore(viewModel.getScoreSelect().getValue());
                hole.setPar(viewModel.getParSelect().getValue());
                hole.setPutts(getPutts());

                if (holeNum < 9) {

                } else if (holeNum >= 9) {
                }


                int nextHole = holeNum + 1;
                if (nextHole < 10) {
                    loadNextHole(nextHole);
                } else if (nextHole == 10) {
                    loadAtTheTurn();
                } else if (nextHole == 19) {
                    loadFinishRound();
                }
            }
        });

        return retval;
    }

    private void loadFinishRound() {
    }

    private void loadNextHole(int nextHoleNum) {
        holes[holeNum] = hole;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(nextHoleNum, round);
        fragmentTransaction.add(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    private void loadAtTheTurn() {
        round.getFrontNine().setHoles(holes);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        StatFragment stat = new StatFragment();
//        fragmentTransaction.add(R.id.content_view, stat, "STAT");
        fragmentTransaction.commit();

        Log.d("FRont", "" + Arrays.toString(round.getFrontNine().getHoles()));
    }

    private int getPutts() {
        viewModel.setOnePutt(binding.onePutt);
        viewModel.setTwoPutt(binding.twoPutt);
        viewModel.setThreePutt(binding.threePutt);
        viewModel.setFourPutt(binding.fourPutt);

        int retval = 0;

        if (viewModel.getOnePutt().isChecked()) {
            retval = 1;
        } else if (viewModel.getTwoPutt().isChecked()) {
            retval = 2;
        } else if (viewModel.getThreePutt().isChecked()) {
            retval = 3;
        } else if (viewModel.getFourPutt().isChecked()) {
            retval = 4;
        }

        return retval;
    }

    @Override
    public Hole getHole(Hole hole) {
        return hole;
    }
}
