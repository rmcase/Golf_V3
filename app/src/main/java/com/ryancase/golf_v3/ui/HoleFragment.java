package com.ryancase.golf_v3.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ryancase.golf_v3.Hole;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.ViewModels.HoleViewModel;
import com.ryancase.golf_v3.databinding.FragmentHoleBinding;

import static android.view.View.GONE;

/**
 * File description here...
 */

public class HoleFragment extends android.support.v4.app.Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private int holeNum;
    private FragmentHoleBinding binding;

    private HoleViewModel viewModel;

    public HoleFragment() {

    }

    public HoleFragment(int holeNum) {
        this.holeNum = holeNum;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_score:
                // User chose the "Settings" item, show the app settings UI...
                showCurrentStatsDialog();
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
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void showCurrentStatsDialog() {
        android.support.v4.app.FragmentManager fm = getFragmentManager();
        android.support.v4.app.DialogFragment dialog = new ScorecardFragment(Round.getScore(), Round.getRelativeScore(), Round.getPutts(), Round.getFrontNine(), Round.getBackNine()); // creating new object
        dialog.show(fm, "");

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
                    CourseSelectionFragment csf = new CourseSelectionFragment();
                    fragmentTransaction.replace(R.id.content_view, csf, "STAT");
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
        View retval = inflater.inflate(R.layout.fragment_hole, container, false);

        getActivity().setTitle("Hole " + holeNum + "\t\t—\t\t" + Round.getCourse());

        setHasOptionsMenu(true);

        binding = DataBindingUtil.bind(retval);


        if (viewModel == null) {
            viewModel = new HoleViewModel(this);
        }
        binding.setViewModel(viewModel);

        bindViewModelElements();

        populateViewModelElements();

        binding.theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.holeTable.setVisibility(GONE);
                    binding.ratingTable.setVisibility(View.VISIBLE);
                } else {
                    binding.holeTable.setVisibility(View.VISIBLE);
                    binding.ratingTable.setVisibility(GONE);
                }
            }
        });

//        switch (binding.DriverRadioGroup.getCheckedRadioButtonId()) {
//            case 0: {
//
//            }
//            case 1: {
//
//            }
//            case 2: {
//
//            }
//            case 3: {
//
//            }
//        }

        return retval;
    }

    private void populateViewModelElements() {
        String[] ratingStrings = {"N/A", "A", "B", "C", "D"};

        viewModel.getOnePutt().setText(R.string.onePutt);
        viewModel.getTwoPutt().setText(R.string.twoPutt);
        viewModel.getThreePutt().setText(R.string.threePutt);
        viewModel.getGreenCheck().setText("GIR");
        viewModel.getFairwayCheck().setText("Fairway");
        viewModel.getUpAndDownCheck().setText("Scrambling");
        viewModel.getScoreSelect().setMinValue(1);
        viewModel.getScoreSelect().setMaxValue(10);
        viewModel.getScoreSelect().setValue(4);
        viewModel.getParSelect().setMinValue(3);
        viewModel.getParSelect().setMaxValue(5);
        viewModel.getParSelect().setValue(4);

        Round.setScore(Round.getScore() + viewModel.getScoreForHole());
        Round.setPutts(Round.getPutts() + viewModel.getNumberOfPutts());
        Round.setRelativeScore(Round.getRelativeScore() + viewModel.getScoreRelativeToPar());

        if (holeNum == 18) {
            viewModel.getNextHoleButton().setText("Finish Round");
        } else {
            viewModel.getNextHoleButton().setText(R.string.next_hole);
        }
        viewModel.getNextHoleButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = viewModel.getDriverRg().indexOfChild(viewModel.getDriverRg().findViewById(viewModel.getDriverRg().getCheckedRadioButtonId()));
                viewModel.setDriverRating(index);
                index = viewModel.getIronRg().indexOfChild(viewModel.getIronRg().findViewById(viewModel.getIronRg().getCheckedRadioButtonId()));
                viewModel.setIronRating(index);
                Hole hole = new Hole.Builder()
                        .score(viewModel.getScoreForHole())
                        .par(viewModel.getParForHole())
                        .putts(viewModel.getNumberOfPutts())
                        .green(viewModel.getGreenStat())
                        .fairway(viewModel.getFairwayStat())
                        .upAndDown(viewModel.getUpAndDownStat())
                        .scoreToPar(viewModel.getScoreRelativeToPar())
                        .driverRating(viewModel.getDriverRating())
                        .ironRating(viewModel.getIronRating())
                        .approachRating(viewModel.getApproachRating())
                        .puttRating(viewModel.getPuttRating())
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
        viewModel.setTitle("Hole " + holeNum);
        viewModel.setPuttTv("Putts");
        viewModel.setApproachRg(binding.ApproachRadioGroup);
        viewModel.setDriverRg(binding.DriverRadioGroup);
        viewModel.setPuttRg(binding.PuttRadioGroup);
        viewModel.setIronRg(binding.IronRadioGroup);


        for (int i = 1; i < 4; i++) {
            setPuttOnClickListener(i);
        }
    }

    private void loadNextHole(int nextHoleNum) {
        if (nextHoleNum < 10)
            Log.d("Hole " + holeNum, "" + Round.getFrontNine().getHoles().get(holeNum - 1).toString());
        else
            Log.d("Hole " + holeNum, "" + Round.getBackNine().getHoles().get(holeNum - 10).toString());

        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(nextHoleNum);
        fragmentTransaction.replace(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    private void loadAtTheTurn() {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StatFragment stat = new StatFragment();
        fragmentTransaction.add(R.id.content_view, stat, "STAT");
        fragmentTransaction.commit();
    }

    private void loadFinishRound() {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StatFragment stat = new StatFragment(true);
        fragmentTransaction.add(R.id.content_view, stat, "STAT");
        fragmentTransaction.commit();
    }

    private void setRatingOnClickListener() {
        viewModel.getRatingConfirm().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewModel.getClubToRate().getText() == "Driver") {
                    viewModel.setDriverRating(viewModel.getRatingSelector().getValue());
                    viewModel.getClubToRate().setText("Iron");
                    viewModel.getRatingConfirm().setChecked(false);
                } else if (viewModel.getClubToRate().getText() == "Iron") {
                    viewModel.setIronRating(viewModel.getRatingSelector().getValue());
                    viewModel.getClubToRate().setText("Approach");
                    viewModel.getRatingConfirm().setChecked(false);
                } else if (viewModel.getClubToRate().getText() == "Approach") {
                    viewModel.setApproachRating(viewModel.getRatingSelector().getValue());
                    viewModel.getClubToRate().setText("Putt");
                    viewModel.getRatingConfirm().setChecked(false);
                } else if (viewModel.getClubToRate().getText() == "Putt") {
                    viewModel.setPuttRating(viewModel.getRatingSelector().getValue());
                    viewModel.getClubToRate().setText("");
                    viewModel.getRatingConfirm().setEnabled(false);
                } else {

                }


            }
        });
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
        }
    }
}
