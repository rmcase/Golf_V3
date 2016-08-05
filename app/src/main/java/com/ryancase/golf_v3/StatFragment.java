package com.ryancase.golf_v3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.ryancase.golf_v3.databinding.FragmentStatViewBinding;


/**
 * File description here...
 */

public class StatFragment extends Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private FragmentStatViewBinding binding;

    private StatViewModel viewModel;

    private boolean goToFinishRound;

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

        binding = DataBindingUtil.bind(retval);


        if (viewModel == null) {
            viewModel = new StatViewModel();
        }
        binding.setViewModel(viewModel);

        bindViewModelElements();

        setViewModelElements();

        return retval;
    }

    private void bindViewModelElements() {
        if (goToFinishRound) {
            viewModel.setTitle(getActivity().getString(R.string.lastHole));
            viewModel.setScore(String.valueOf(Round.getScore()));
            viewModel.setPar(String.valueOf(Round.getPar()));
        } else {
            viewModel.setTitle(getActivity().getString(R.string.at_the_turn));
            viewModel.setScore(String.valueOf(Round.getFrontNine().getScore()));
            viewModel.setPar(String.valueOf(Round.getFrontNine().getPar()));
        }

        viewModel.setNextHoleButton(binding.nextHole);
        viewModel.setTableLayout(binding.table);
    }

    private void setViewModelElements() {
        TextView score = new TextView(getActivity());
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1);
        score.setLayoutParams(params);
        score.setText(viewModel.getScore());
        score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

        TextView scoreTv = new TextView(getActivity());
        scoreTv.setLayoutParams(params);
        scoreTv.setText("Score:");
        scoreTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

        viewModel.getTableLayout().addView(addTableRow(score, scoreTv));

        TextView par = new TextView(getActivity());
        score.setLayoutParams(params);
        score.setText(viewModel.getPar());
        score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

        TextView parTv = new TextView(getActivity());
        scoreTv.setLayoutParams(params);
        scoreTv.setText("Par:");
        scoreTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

        viewModel.getTableLayout().addView(addTableRow(par, parTv));

        if (goToFinishRound) {
            viewModel.getNextHoleButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
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

    private void loadNextHole(int nextHoleNum) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(nextHoleNum);
        fragmentTransaction.add(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

}
