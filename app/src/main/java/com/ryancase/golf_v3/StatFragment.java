//package com.ryancase.golf_v3;
//
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.content.Intent;
//import android.databinding.DataBindingUtil;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.ryancase.golf_v3.databinding.FragmentHoleBinding;
//import com.ryancase.golf_v3.databinding.FragmentStatViewBinding;
//
//
///**
// * File description here...
// */
//
//public class StatFragment extends Fragment implements HoleView {
//
//    private final String FRAGMENT_TAG = "HOLE";
//
//    private FragmentStatViewBinding binding;
//
//    private StatViewModel viewModel;
//
//    public StatFragment() {
//
//    }
//
////    @Override
////    public void onResume() {
////
////        super.onResume();
////
////        getView().setFocusableInTouchMode(true);
////        getView().requestFocus();
////        getView().setOnKeyListener(new View.OnKeyListener() {
////            @Override
////            public boolean onKey(View v, int keyCode, KeyEvent event) {
////
////                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
////
////                    // handle back button
////                    startActivity(new Intent(getActivity(), MainActivity.class));
////                    return true;
////
////                }
////                return false;
////            }
////        });
////    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View retval = inflater.inflate(R.layout.fragment_hole, container, false);
//
//        binding = DataBindingUtil.bind(retval);
//
//
//        if (viewModel == null) {
//            viewModel = new StatViewModel();
//        }
//        binding.setViewModel(viewModel);
//
//        viewModel.setTitle(getActivity().getString(R.string.at_the_turn));
//
//        return retval;
//    }
//
//    private void loadNextHole(int nextHoleNum) {
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        StatFragment holenextHoleNumFragment(nextHoleNum);
//        fragmentTransaction.add(R.id.content_view, hole, FRAGMENT_TAG);
//        fragmentTransac
//
//    private void loadAtTheTurn() {
//    }
//
//tTheTurn() {
//    }
//
//}
