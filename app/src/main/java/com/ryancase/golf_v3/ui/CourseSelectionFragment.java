package com.ryancase.golf_v3.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.RoundThing;
import com.ryancase.golf_v3.ViewModels.CourseSelectViewModel;
import com.ryancase.golf_v3.databinding.FragmentCourseSelectBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * File description here...
 */

public class CourseSelectionFragment extends Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private FragmentCourseSelectBinding binding;

    private CourseSelectViewModel viewModel;

    private DatabaseReference database;

    private InputMethodManager mgr;

    private List<String> courseNames;

    public CourseSelectionFragment() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                // User chose the "Settings" item, show the app settings UI...
                startActivity(new Intent(getActivity(), MainActivity.class));

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
        inflater.inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu, inflater);

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
        View retval = inflater.inflate(R.layout.fragment_course_select, container, false);

        getActivity().setTitle("Course Select");

        setHasOptionsMenu(true);

        binding = DataBindingUtil.bind(retval);


        if (viewModel == null) {
            viewModel = new CourseSelectViewModel();
        }
        binding.setViewModel(viewModel);

        mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        bindViewModelElements();

        populateViewModelElements();

        loadRounds();

        return retval;
    }

    private void populateViewModelElements() {
        viewModel.getNewCourseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCourseName();
            }
        });

        courseNames = new ArrayList<>();
    }

    private void bindViewModelElements() {
        viewModel.setPreviousCourseList(binding.savedCoursesList);
        viewModel.setNewCourseButton(binding.newCourseButton);
        viewModel.setBeginRoundButton(binding.beginRoundButton);
    }

    private void loadRounds() {
        database = FirebaseDatabase.getInstance().getReference();
        Query roundQuery = database.child("Rounds").orderByChild("date");

        roundQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                RoundThing round = dataSnapshot.getValue(RoundThing.class);

                courseNames.add(round.getCourse().toUpperCase());
                HashSet<String> set = new HashSet<>(courseNames);
                courseNames.clear();
                courseNames.addAll(set);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.history_list_item, courseNames);

                viewModel.getPreviousCourseList().setAdapter(adapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        viewModel.getPreviousCourseList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String course = courseNames.get(position);
                Round.setCourse(course);
                loadFirstHole();
            }
        });
    }

    private void selectCourseName() {
        viewModel.getNewCourseButton().setVisibility(View.GONE);

//        binding.courseNameTv.setVisibility(View.VISIBLE);

        binding.courseNameEt.requestFocus();
        mgr.showSoftInput(binding.courseNameEt, InputMethodManager.SHOW_IMPLICIT);

        binding.courseNameEt.setSingleLine(true);

        binding.courseNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    viewModel.getBeginRoundButton().setVisibility(View.VISIBLE);
                } else {
                    viewModel.getBeginRoundButton().setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewModel.getBeginRoundButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Round.setCourse(binding.courseNameEt.getText().toString().toUpperCase());
                mgr.hideSoftInputFromWindow(binding.courseNameEt.getWindowToken(), 0);
                loadFirstHole();
            }
        });
    }

    private void loadFirstHole() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(1);
        fragmentTransaction.add(R.id.content_view, hole, FRAGMENT_TAG);
        mgr.hideSoftInputFromWindow(binding.courseNameEt.getWindowToken(), 0);
        fragmentTransaction.commit();
    }
}
