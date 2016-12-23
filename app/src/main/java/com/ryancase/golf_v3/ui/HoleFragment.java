package com.ryancase.golf_v3.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.ryancase.golf_v3.Hole;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.ViewModels.HoleViewModel;
import com.ryancase.golf_v3.databinding.FragmentHoleBinding;

import info.hoang8f.android.segmented.SegmentedGroup;

import static android.view.View.GONE;

/**
 * File description here...
 */

public class HoleFragment extends android.support.v4.app.Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private int holeNum, p;
    private FragmentHoleBinding binding;
    private boolean isNewCourse;
    private SharedPreferences preferences, prevParPref;

    private SegmentedGroup puttGroup;

    private int parToGet;

    private RelativeLayout mRoot;

    private HoleViewModel viewModel;

    public HoleFragment() {

    }

    public HoleFragment(int holeNum, boolean isNewCourse) {
        this.holeNum = holeNum;
        this.isNewCourse = isNewCourse;
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
        android.support.v4.app.DialogFragment dialog = new ScorecardFragment(Round.getScore(), Round.getPutts(), Round.getFrontNine(), Round.getBackNine()); // creating new object
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

        mRoot = (RelativeLayout) retval.findViewById(R.id.holeRelLayout);

        setHasOptionsMenu(true);

        binding = DataBindingUtil.bind(retval);

        preferences = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        prevParPref = getActivity().getSharedPreferences("PREFCS", Context.MODE_PRIVATE);

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

        return retval;
    }

    private void populateViewModelElements() {
        viewModel.getGreenCheck().setText("GIR");
        viewModel.getFairwayCheck().setText("Fairway");
        viewModel.getUpAndDownCheck().setText("Scrambling");
        viewModel.getScoreSelect().setValue(4);

        Round.setScore(Round.getScore() + viewModel.getScoreForHole());
        Round.setPutts(Round.getPutts() + viewModel.getNumberOfPutts());

        parToGet = holeNum - 1;

        if (!isNewCourse && prevParPref.getInt("par" + parToGet, 0) != 0) {
            viewModel.getParSelect().setVisibility(GONE);
        }

        if (holeNum == 18) {
            viewModel.getNextHoleButton().setText("Finish Round");
        } else {
            viewModel.getNextHoleButton().setText(R.string.next_hole);
        }

//        if(viewModel.getParSelect().getVisibility() == View.VISIBLE) {
//            Log.d("CheckedId", "" + viewModel.getParSelect().getCheckedRadioButtonId());
//            if(viewModel.getParSelect().getCheckedRadioButtonId() == -1) {
//                viewModel.getNextHoleButton().setEnabled(false);
//
//                View coordinatorLayout = getActivity().findViewById(R.id.snackbarPosition);
//                Snackbar snackbar = Snackbar
//                        .make(coordinatorLayout, "Please enter a par", Snackbar.LENGTH_LONG);
//
//                snackbar.show();
//            } else {
//                viewModel.getNextHoleButton().setEnabled(true);
//            }
//
//        }
        viewModel.getNextHoleButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClubRatings();

                boolean loadNextHole = true;

                viewModel.setNumberOfPutts(puttGroup.indexOfChild(puttGroup.findViewById(puttGroup.getCheckedRadioButtonId())) + 1);

                if (!isNewCourse && prevParPref.getInt("par" + parToGet, 0) != 0) {
                    Log.d("PARLOAD:", "" + prevParPref.getInt("par" + parToGet, 0));
                    viewModel.setParForHole(prevParPref.getInt("par" + parToGet, 0));
                } else {
                    viewModel.getParSelect().setVisibility(View.VISIBLE);

                    if(viewModel.getParSelect().getCheckedRadioButtonId() == -1) {
                        loadNextHole = false;
                        Snackbar snackbar = Snackbar.make(mRoot, "Please enter a Par for the hole", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        viewModel.setParForHole(viewModel.getParSelect().indexOfChild(viewModel.getParSelect().findViewById(viewModel.getParSelect().getCheckedRadioButtonId())) + 3);
                    }
                        Log.d("New Course", "");
                }

                viewModel.setScoreRelativeToPar(viewModel.getScoreForHole() - viewModel.getParForHole());


                Log.d("REL-SCORE", "" + viewModel.getScoreRelativeToPar());

                if(loadNextHole) {
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
            }
        });
    }

    private void setClubRatings() {
        int ratingIndex = viewModel.getDriverRg().indexOfChild(viewModel.getDriverRg().findViewById(viewModel.getDriverRg().getCheckedRadioButtonId()));
        viewModel.setDriverRating(ratingIndex);

        ratingIndex = viewModel.getIronRg().indexOfChild(viewModel.getIronRg().findViewById(viewModel.getIronRg().getCheckedRadioButtonId()));
        viewModel.setIronRating(ratingIndex);

        ratingIndex = viewModel.getApproachRg().indexOfChild(viewModel.getApproachRg().findViewById(viewModel.getApproachRg().getCheckedRadioButtonId()));
        viewModel.setApproachRating(ratingIndex);

        ratingIndex = viewModel.getPuttRg().indexOfChild(viewModel.getPuttRg().findViewById(viewModel.getPuttRg().getCheckedRadioButtonId()));
        viewModel.setPuttRating(ratingIndex);
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
        viewModel.setParSelect(binding.parSegmentedGroup);
        viewModel.setScoreSelect(binding.scoreSelector);
        viewModel.setTitle("Hole " + holeNum);
        viewModel.setPuttTv("Putts");
        viewModel.setApproachRg(binding.ApproachRadioGroup);
        viewModel.setDriverRg(binding.DriverRadioGroup);
        viewModel.setPuttRg(binding.PuttRadioGroup);
        viewModel.setIronRg(binding.IronRadioGroup);

        puttGroup = binding.puttSegmentedGroup;
        //New Putt Listener
//        binding.puttSegmentedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton rb = (RadioButton) group.findViewById(checkedId);
//                int index = group.indexOfChild(rb);
//                Log.d("RBID", "" + checkedId);
//            }
//        });
    }

    private void loadNextHole(int nextHoleNum) {
        if (nextHoleNum < 10)
            Log.d("Hole " + holeNum, "" + Round.getFrontNine().getHoles().get(holeNum - 1).toString());
        else
            Log.d("Hole " + holeNum, "" + Round.getBackNine().getHoles().get(holeNum - 10).toString());

        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(nextHoleNum, isNewCourse);
        fragmentTransaction.replace(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    private void loadAtTheTurn() {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StatFragment stat = new StatFragment(isNewCourse, Round.getFrontNine().getHoles());
        fragmentTransaction.replace(R.id.content_view, stat, "STAT");
        fragmentTransaction.commit();
    }

    private void loadFinishRound() {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StatFragment stat = new StatFragment(true, Round.getFrontNine().getHoles(), Round.getBackNine().getHoles());
        fragmentTransaction.replace(R.id.content_view, stat, "STAT");
        fragmentTransaction.commit();
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
