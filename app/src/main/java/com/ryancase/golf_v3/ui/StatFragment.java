package com.ryancase.golf_v3.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.RoundModel;
import com.ryancase.golf_v3.ViewModels.StatViewModel;
import com.ryancase.golf_v3.databinding.FragmentStatViewBinding;


/**
 * File description here...
 */

public class StatFragment extends android.support.v4.app.Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";
    private final int TEXTVIEW_TEXT_SIZE = 22;

    private FragmentStatViewBinding binding;

    private StatViewModel viewModel;

    private boolean goToFinishRound;

    private Button nextHole;
    private Button finishRound;

    private DatabaseReference reference;
    private RoundModel roundModel;

    public StatFragment() {

    }

    public StatFragment(boolean goToFinishRound) {
        this.goToFinishRound = goToFinishRound;
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("Rounds");

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
        nextHole = new Button(getActivity());
        finishRound = new Button(getActivity());

        if (goToFinishRound) {
            viewModel.setTitle(getActivity().getString(R.string.lastHole));
            viewModel.setScore(String.valueOf(Round.getScore()));
            viewModel.setPar(String.valueOf(Round.getPar()));
            viewModel.setScoreToPar(String.valueOf(Round.getScoreToPar()));
            viewModel.setPutts(String.valueOf(Round.getPutts()));
            viewModel.setGreens(String.valueOf(Round.getGreens()));
            viewModel.setFairways(String.valueOf(Round.getFairways()));
            viewModel.setDriverRating(String.valueOf(Round.getRating("Driver")));
            viewModel.setIronRating(String.valueOf(Round.getRating("Iron")));
            viewModel.setApproachRating(String.valueOf(Round.getRating("Approach")));
            viewModel.setPuttRating(String.valueOf(Round.getRating("Putt")));
        } else {
            viewModel.setTitle(getActivity().getString(R.string.at_the_turn));
            viewModel.setScore(String.valueOf(Round.getFrontNine().getScore()));
            viewModel.setPar(String.valueOf(Round.getFrontNine().getPar()));
            viewModel.setScoreToPar(String.valueOf(Round.getFrontNine().getScoreToPar()));
            viewModel.setPutts(String.valueOf(Round.getFrontNine().madePuttsPercentage()));
            viewModel.setGreens(String.valueOf(Round.getFrontNine().getGreens()));
            viewModel.setFairways(String.valueOf(Round.getFrontNine().getFairwayPercentage()));
            viewModel.setDriverRating(Round.getFrontNine().getAverageDriverRating());
            viewModel.setIronRating(Round.getFrontNine().getAverageIronRating());
            viewModel.setApproachRating(Round.getFrontNine().getAverageApproachRating());
            viewModel.setPuttRating(Round.getFrontNine().getAveragePuttRating());
        }

        viewModel.setFinishHoleButton(finishRound);
        viewModel.setNextHoleButton(nextHole);
        viewModel.setTableLayout(binding.table);
    }

    private void setViewModelElements() {
        configureTableLayout();

        viewModel.getNextHoleButton().setTextColor(getResources().getColor(R.color.white));
        viewModel.getNextHoleButton().setBackgroundColor(getResources().getColor(R.color.fDarkTeal));
        viewModel.getFinishHoleButton().setTextColor(getResources().getColor(R.color.white));
        viewModel.getFinishHoleButton().setBackgroundColor(getResources().getColor(R.color.fDarkTeal));

        if (goToFinishRound) {
            viewModel.getFinishHoleButton().setVisibility(View.GONE);
            viewModel.getNextHoleButton().setText(R.string.finish_round);
            viewModel.getNextHoleButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.push().setValue(roundModel);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            viewModel.getFinishHoleButton().setText(R.string.finish_round);
            viewModel.getFinishHoleButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.push().setValue(roundModel);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
            viewModel.getNextHoleButton().setText("Next Hole");
            viewModel.getNextHoleButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadNextHole(10);
                }
            });
        }
    }

    public TableRow addTableRow(TextView data, TextView title) {
        TableRow tr = new TableRow(getActivity());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.addView(title);
        tr.addView(data);

        return tr;
    }

    public void configureTableLayout() {
        TableLayout.LayoutParams tableParam = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1);

        TextView score = new TextView(getActivity());
        score.setLayoutParams(params);
        score.setText(viewModel.getScore());
        score.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);
        TextView scoreTv = new TextView(getActivity());
        scoreTv.setLayoutParams(params);
        scoreTv.setText("Score:");
        scoreTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);


        TextView par = new TextView(getActivity());
        par.setLayoutParams(params);
        par.setText(viewModel.getPar());
        par.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);
        TextView parTv = new TextView(getActivity());
        parTv.setLayoutParams(params);
        parTv.setText("Par:");
        parTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);

        TextView scoreRelativeToPar = new TextView(getActivity());
        scoreRelativeToPar.setLayoutParams(params);
        scoreRelativeToPar.setText(formatScoreToPar());
        scoreRelativeToPar.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);
        TextView scoreRelativeToParTv = new TextView(getActivity());
        scoreRelativeToParTv.setLayoutParams(params);
        scoreRelativeToParTv.setText("Score To Par:");
        scoreRelativeToParTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);

        TextView putts = new TextView(getActivity());
        putts.setLayoutParams(params);
        putts.setText(viewModel.getPutts());
        putts.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);
        TextView puttsTv = new TextView(getActivity());
        puttsTv.setLayoutParams(params);
        puttsTv.setText("Putts:");
        puttsTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);

        TextView greens = new TextView(getActivity());
        greens.setLayoutParams(params);
        greens.setText(viewModel.getGreens());
        greens.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);
        TextView greensTv = new TextView(getActivity());
        greensTv.setLayoutParams(params);
        greensTv.setText("Greens:");
        greensTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);

        TextView fairways = new TextView(getActivity());
        fairways.setLayoutParams(params);
        fairways.setText(viewModel.getFairways());
        fairways.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);
        TextView fairwaysTv = new TextView(getActivity());
        fairwaysTv.setLayoutParams(params);
        fairwaysTv.setText("Fairways:");
        fairwaysTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);

        TextView driverRating = new TextView(getActivity());
        driverRating.setLayoutParams(params);
        driverRating.setText(viewModel.getDriverRating());
        driverRating.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);
        TextView driverRatingTv = new TextView(getActivity());
        driverRatingTv.setLayoutParams(params);
        driverRatingTv.setText("Driver Rating:");
        driverRatingTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);

        TextView ironRating = new TextView(getActivity());
        ironRating.setLayoutParams(params);
        ironRating.setText(viewModel.getIronRating());
        ironRating.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);
        TextView ironRatingTv = new TextView(getActivity());
        ironRatingTv.setLayoutParams(params);
        ironRatingTv.setText("Iron Rating:");
        ironRatingTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);

        TextView approachRating = new TextView(getActivity());
        approachRating.setLayoutParams(params);
        approachRating.setText(viewModel.getApproachRating());
        approachRating.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);
        TextView approachRatingTv = new TextView(getActivity());
        approachRatingTv.setLayoutParams(params);
        approachRatingTv.setText("Approach Rating:");
        approachRatingTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);

        TextView puttRating = new TextView(getActivity());
        puttRating.setLayoutParams(params);
        puttRating.setText(viewModel.getPuttRating());
        puttRating.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);
        TextView puttRatingTv = new TextView(getActivity());
        puttRatingTv.setLayoutParams(params);
        puttRatingTv.setText("Putt Rating:");
        puttRatingTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTVIEW_TEXT_SIZE);

        TableRow tr = new TableRow(getActivity());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        nextHole.setLayoutParams(params);
        finishRound.setLayoutParams(params);
        tr.setPadding(40, 20, 40, 0);
        tr.addView(nextHole);
        tr.addView(finishRound);

//        TableRow tRow = new TableRow(getActivity());
//        tRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
//        finishRound.setLayoutParams(params);
//        tRow.setPadding(40, 20, 40, 0);
//        tRow.addView(finishRound);

        viewModel.getTableLayout().addView(addTableRow(scoreRelativeToPar, scoreRelativeToParTv), tableParam);
        viewModel.getTableLayout().addView(addTableRow(score, scoreTv), tableParam);
        viewModel.getTableLayout().addView(addTableRow(par, parTv), tableParam);
        viewModel.getTableLayout().addView(addTableRow(putts, puttsTv), tableParam);
        viewModel.getTableLayout().addView(addTableRow(greens, greensTv), tableParam);
        viewModel.getTableLayout().addView(addTableRow(fairways, fairwaysTv), tableParam);
        viewModel.getTableLayout().addView(addTableRow(driverRating, driverRatingTv), tableParam);
        viewModel.getTableLayout().addView(addTableRow(ironRating, ironRatingTv), tableParam);
        viewModel.getTableLayout().addView(addTableRow(approachRating, approachRatingTv), tableParam);
        viewModel.getTableLayout().addView(addTableRow(puttRating, puttRatingTv), tableParam);
        viewModel.getTableLayout().addView(tr, tableParam);
//        viewModel.getTableLayout().addView(tRow, tableParam);
    }

    private void loadNextHole(int nextHoleNum) {
        binding.table.setVisibility(View.GONE);
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(nextHoleNum);
        fragmentTransaction.add(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    private String formatScoreToPar() {
        String retval = viewModel.getScoreToPar();

        if (retval.equals(String.valueOf(0))) {
            retval = "E";
        } else if (Integer.parseInt(retval) > 0) {
            retval = "+" + retval;
        }

        return retval;
    }

}
