package com.ryancase.golf_v3.ui;

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
import android.widget.CheckBox;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ryancase.golf_v3.Hole;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.ViewModels.HoleViewModel;
import com.ryancase.golf_v3.databinding.FragmentHoleBinding;

/**
 * File description here...
 */

public class HoleFragment extends Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private int holeNum;
    private FragmentHoleBinding binding;

    private HoleViewModel viewModel;

    public HoleFragment(int holeNum) {
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

        bindViewModelElements();

        populateViewModelElements();

        return retval;
    }

    private void populateViewModelElements() {
        viewModel.getOnePutt().setText(R.string.onePutt);
        viewModel.getTwoPutt().setText(R.string.twoPutt);
        viewModel.getThreePutt().setText(R.string.threePutt);
        viewModel.getFourPutt().setText(R.string.fourPutt);
        viewModel.getGreenCheck().setText("GIR");
        viewModel.getFairwayCheck().setText("Fairway");
        viewModel.getUpAndDownCheck().setText("Up and Down");

        viewModel.getScoreSelect().setMinValue(1);
        viewModel.getScoreSelect().setMaxValue(10);
        viewModel.getScoreSelect().setValue(4);
        viewModel.getParSelect().setMinValue(3);
        viewModel.getParSelect().setMaxValue(5);
        viewModel.getParSelect().setValue(4);

        if (holeNum == 18) {
            viewModel.getNextHoleButton().setText("Finish Round");
        } else {
            viewModel.getNextHoleButton().setText(R.string.next_hole);
        }
        viewModel.getNextHoleButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hole hole = new Hole.Builder()
                        .score(viewModel.getScoreForHole())
                        .par(viewModel.getParForHole())
                        .putts(viewModel.getNumberOfPutts())
                        .green(viewModel.getGreenStat())
                        .fairway(viewModel.getFairwayStat())
                        .upAndDown(viewModel.getUpAndDownStat())
                        .scoreToPar(viewModel.getScoreRelativeToPar())
                        .build();

                saveHole(hole);

                int nextHole = holeNum + 1;

                if (nextHole < 10 || (nextHole > 10 && nextHole < 19)) {
                    loadNextHole(nextHole);
                } else if (nextHole == 10) {
                    loadAtTheTurn();
                } else if (nextHole == 19) {
                    loadFinishRound();
                }
            }
        });
    }

    private void saveHole(Hole hole) {
        if (holeNum <= 9) {
            Round.getFrontNine().addHole(hole);

        } else if (holeNum > 9) {
            Round.getBackNine().addHole(hole);
        }
    }

    private void bindViewModelElements() {
        viewModel.setUpAndDownCheck(binding.upAndDownCheck);
        viewModel.setFairwayCheck(binding.fairwayCheck);
        viewModel.setGreenCheck(binding.greenCheck);
        viewModel.setNextHoleButton(binding.nextHole);
        viewModel.setParSelect(binding.parSelector);
        viewModel.setScoreSelect(binding.scoreSelector);
        viewModel.setOnePutt(binding.onePutt);
        viewModel.setTwoPutt(binding.twoPutt);
        viewModel.setThreePutt(binding.threePutt);
        viewModel.setFourPutt(binding.fourPutt);
        viewModel.setTitle("Hole " + holeNum);
        viewModel.setPuttTv("Putts");

        for (int i = 1; i < 5; i++) {
            setPuttOnClickListener(i);
        }
    }

    private void loadNextHole(int nextHoleNum) {
        if (nextHoleNum < 10)
            Log.d("Hole " + holeNum, "" + Round.getFrontNine().getHoles().get(holeNum - 1).toString());
        else
            Log.d("Hole " + holeNum, "" + Round.getBackNine().getHoles().get(holeNum -1).toString());

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(nextHoleNum);
        fragmentTransaction.add(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    private void loadAtTheTurn() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StatFragment stat = new StatFragment();
        fragmentTransaction.add(R.id.content_view, stat, "STAT");
        fragmentTransaction.commit();
    }

    private void loadFinishRound() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StatFragment stat = new StatFragment(true);
        fragmentTransaction.add(R.id.content_view, stat, "STAT");
        fragmentTransaction.commit();
    }

    private void setPuttOnClickListener(int puttNum) {
        switch (puttNum) {
            case 1: {
                viewModel.getOnePutt().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            viewModel.getTwoPutt().setChecked(false);
                            viewModel.getThreePutt().setChecked(false);
                            viewModel.getFourPutt().setChecked(false);

                            viewModel.getParSelect().setEnabled(false);
                            viewModel.getScoreSelect().setEnabled(false);

                            predictStats(1);
                        } else {
                            viewModel.getGreenCheck().setChecked(false);
                            viewModel.getParSelect().setEnabled(true);
                            viewModel.getScoreSelect().setEnabled(true);
                        }
                    }
                });
                break;
            }
            case 2: {
                viewModel.getTwoPutt().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            viewModel.getOnePutt().setChecked(false);
                            viewModel.getThreePutt().setChecked(false);
                            viewModel.getFourPutt().setChecked(false);

                            viewModel.getParSelect().setEnabled(false);
                            viewModel.getScoreSelect().setEnabled(false);

                            predictStats(2);
                        } else {
                            viewModel.getGreenCheck().setChecked(false);
                            viewModel.getParSelect().setEnabled(true);
                            viewModel.getScoreSelect().setEnabled(true);
                        }
                    }
                });
                break;
            }
            case 3: {
                viewModel.getThreePutt().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            viewModel.getOnePutt().setChecked(false);
                            viewModel.getTwoPutt().setChecked(false);
                            viewModel.getFourPutt().setChecked(false);

                            viewModel.getParSelect().setEnabled(false);
                            viewModel.getScoreSelect().setEnabled(false);

                            predictStats(3);
                        } else {
                            viewModel.getGreenCheck().setChecked(false);
                            viewModel.getParSelect().setEnabled(true);
                            viewModel.getScoreSelect().setEnabled(true);
                        }
                    }
                });
                break;
            }
            case 4: {
                viewModel.getFourPutt().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            viewModel.getOnePutt().setChecked(false);
                            viewModel.getTwoPutt().setChecked(false);
                            viewModel.getThreePutt().setChecked(false);

                            viewModel.getParSelect().setEnabled(false);
                            viewModel.getScoreSelect().setEnabled(false);

                            predictStats(4);
                        } else {
                            viewModel.getGreenCheck().setChecked(false);
                            viewModel.getParSelect().setEnabled(true);
                            viewModel.getScoreSelect().setEnabled(true);
                        }
                    }
                });
                break;
            }
        }
    }

    private void predictStats(int puttNum) {
        switch (puttNum) {
            case 1: {
                if (viewModel.getScoreRelativeToPar() >= 0) {
                    viewModel.getGreenCheck().setChecked(false);
                } else if (viewModel.getScoreRelativeToPar() <= 0) {
                    viewModel.getGreenCheck().setChecked(true);
                }
                break;
            }
            case 2: {
                if (viewModel.getScoreRelativeToPar() <= 0) {
                    viewModel.getGreenCheck().setChecked(true);
                } else if (viewModel.getScoreRelativeToPar() > 0) {
                    viewModel.getGreenCheck().setChecked(false);
                }
                break;
            }
            case 3: {
                if (viewModel.getScoreRelativeToPar() == 1 || (viewModel.getScoreRelativeToPar() == 0 && viewModel.getParForHole() > 4)) {
                    viewModel.getGreenCheck().setChecked(true);
                } else if (viewModel.getScoreRelativeToPar() > 1) {
                    viewModel.getGreenCheck().setChecked(false);
                }
                break;
            }
            case 4: {
                viewModel.getGreenCheck().setChecked(false);
            }
        }
    }
}
