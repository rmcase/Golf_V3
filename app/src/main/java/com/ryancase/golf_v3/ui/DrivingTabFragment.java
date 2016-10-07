package com.ryancase.golf_v3.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.ViewModels.HistoryRoundViewModel;

/**
 * Created by ryancase on 10/6/16.
 */

public class DrivingTabFragment extends Fragment {

    public DrivingTabFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.driving_stat_tab, container, false);

        Log.d("SAVEDINT:", "" + getArguments().getInt("Arg for Frag1"));

        bindViewModelElements();

        setViewModelElements();

        return retval;
    }

    private void bindViewModelElements() {

    }

    private void setViewModelElements() {

    }
}
