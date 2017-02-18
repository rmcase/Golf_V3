package com.ryancase.golf_v3.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ryancase.golf_v3.Hole;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.RoundModel;
import com.ryancase.golf_v3.ViewModels.StatViewModel;
import com.ryancase.golf_v3.databinding.FragmentStatViewBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * File description here...
 */

@SuppressLint("ValidFragment")
public class StatFragment extends android.support.v4.app.Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";
    private final int TEXTVIEW_TEXT_SIZE = 22;

    private FragmentStatViewBinding binding;

    private StatViewModel viewModel;

    private boolean goToFinishRound;
    private boolean isNewCourse;

    private Button nextHole;
    private Button finishRound;

    private List<Hole> scores;
    private List<Hole> scoresB, scoresF;
    private List<String> scoreInts;

    private FirebaseUser currentUser;
    private FirebaseAuth auth;

    private DatabaseReference reference;
    private RoundModel roundModel;

    public StatFragment() {

    }

    public StatFragment(boolean isNewCourse, List<Hole> scores) {
        this.isNewCourse = isNewCourse;
        this.scores = scores;
    }

    public StatFragment(boolean goToFinishRound, List<Hole> scoresFront, List<Hole> scoresBack) {
        this.goToFinishRound = goToFinishRound;
        this.scoresF = scoresFront;
        this.scoresB = scoresBack;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.fragment_stat_view, container, false);

        if(goToFinishRound) {
            getActivity().setTitle("19th Hole");
        } else {
            getActivity().setTitle("At The Turn");
        }

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null) {
            currentUser = auth.getCurrentUser();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //TODO: change to UUID for app config
//        reference = database.getReference("2A5wfxt9uScm8zlcxxkwj8J6rB42");
        reference = database.getReference(currentUser.getUid());


        binding = DataBindingUtil.bind(retval);


        if (viewModel == null) {
            viewModel = new StatViewModel();
        }
        binding.setViewModel(viewModel);

        roundModel = new RoundModel(Round.getFrontNine(), Round.getBackNine(), Round.getRoundId(), Round.getDatePlayed(), Round.getCourse());

        bindViewModelElements();

        setViewModelElements();

        return retval;
    }

    private void bindViewModelElements() {
        nextHole = binding.nextHole;
        finishRound = binding.finishRound;
        scoreInts = new ArrayList<>();

        if(!goToFinishRound) {
            for (int i = 0; i < 9; i++) {
                scoreInts.add(i, String.valueOf(scores.get(i).getScore()));
            }
            for (int i = 9; i < 18; i++ ) {
                scoreInts.add(i, String.valueOf(0));
            }
        } else {
            for (int i = 0; i < 9; i++) {
                scoreInts.add(i, String.valueOf(scoresF.get(i).getScore()));
            }
            for (int i = 9; i < 18; i++) {
                scoreInts.add(i, String.valueOf(scoresB.get(i-9).getScore()));
            }
        }

        viewModel.setScores(scoreInts);

        if (goToFinishRound) {
            binding.holeScoresBackRow.setVisibility(View.VISIBLE);
            binding.holeNumbersBackRow.setVisibility(View.VISIBLE);
            viewModel.setTitle(getActivity().getString(R.string.lastHole));
            viewModel.setScore(String.valueOf(Round.getScore()));
            viewModel.setPar(String.valueOf(Round.getPar()));
            viewModel.setScoreToPar(String.valueOf(Round.getScoreToPar()));
            viewModel.setPutts(String.valueOf(Round.getPutts()));
            viewModel.setGreens(String.valueOf(Round.getGreens()));
            viewModel.setFairways(String.valueOf(Round.getFairways()));
            viewModel.setScrambling(String.valueOf(Round.getScrambling()));
            viewModel.setDriverRating(String.valueOf(Round.getRating("Driver")));
            viewModel.setIronRating(String.valueOf(Round.getRating("Iron")));
            viewModel.setApproachRating(String.valueOf(Round.getRating("Approach")));
            viewModel.setPuttRating(String.valueOf(Round.getRating("Putt")));
        } else {
            viewModel.setTitle(getActivity().getString(R.string.at_the_turn));
            viewModel.setScore(String.valueOf(Round.getFrontNine().getScore()));
            viewModel.setPar(String.valueOf(Round.getFrontNine().getPar()));
            viewModel.setScoreToPar(String.valueOf(Round.getFrontNine().getScoreToPar()));
            viewModel.setPutts(String.valueOf(Round.getFrontNine().getPutts()));
            viewModel.setGreens(String.valueOf(Round.getFrontNine().getGreens()));
            viewModel.setFairways(String.valueOf(Round.getFrontNine().getFairwayPercentage()));
            viewModel.setDriverRating(Round.getFrontNine().getAverageDriverRating());
            viewModel.setIronRating(Round.getFrontNine().getAverageIronRating());
            viewModel.setScrambling(String.valueOf(Round.getFrontNine().getScrambling()));
            viewModel.setApproachRating(Round.getFrontNine().getAverageApproachRating());
            viewModel.setPuttRating(Round.getFrontNine().getAveragePuttRating());
        }

        int scoreToPar = Integer.valueOf(viewModel.getScoreToPar());

        if(scoreToPar == 0) {
            viewModel.setScoreToPar("E");
            binding.relScoreTv.setTextColor(getResources().getColor(R.color.fTeal));
        } else if(scoreToPar > 0) {
            viewModel.setScoreToPar("+" + String.valueOf(scoreToPar));
        } else {
            viewModel.setScoreToPar(String.valueOf(scoreToPar));
        }
        if(scoreToPar < 0) {
            binding.relScoreTv.setTextColor(getResources().getColor(R.color.red));
        }

        viewModel.setFinishHoleButton(finishRound);
        viewModel.setNextHoleButton(nextHole);
        viewModel.setTableLayout(binding.table);
    }

    private void setViewModelElements() {

        if (goToFinishRound) {
            viewModel.getFinishHoleButton().setVisibility(View.GONE);
            viewModel.getNextHoleButton().setText(R.string.finish_round);
            viewModel.getNextHoleButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.push().setValue(roundModel);

                    Intent intent = new Intent(getActivity(), SplashActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            viewModel.getFinishHoleButton().setText(R.string.finish_round);
            viewModel.getFinishHoleButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.push().setValue(roundModel);

                    Intent intent = new Intent(getActivity(), SplashActivity.class);
                    startActivity(intent);
                }
            });
            viewModel.getNextHoleButton().setText("Next Hole");
            viewModel.getNextHoleButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadNextHole(10, isNewCourse);
                }
            });
        }
    }

    private void loadNextHole(int nextHoleNum, boolean isNewCourse) {
        binding.table.setVisibility(View.GONE);
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(nextHoleNum, isNewCourse);
        fragmentTransaction.replace(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

}
