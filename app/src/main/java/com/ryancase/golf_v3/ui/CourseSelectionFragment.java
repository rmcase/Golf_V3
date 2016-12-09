package com.ryancase.golf_v3.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.gson.Gson;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.Nine;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.RoundThing;
import com.ryancase.golf_v3.ViewModels.CourseSelectViewModel;
import com.ryancase.golf_v3.databinding.FragmentCourseSelectBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static android.view.View.GONE;

/**
 * File description here...
 */

public class CourseSelectionFragment extends android.support.v4.app.Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private FragmentCourseSelectBinding binding;

    private CourseSelectViewModel viewModel;

    private InputMethodManager mgr;

    private List<RoundThing> rounds;

    private SharedPreferences preferences;

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
        setHasOptionsMenu(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retval = inflater.inflate(R.layout.fragment_course_select, container, false);

        binding = DataBindingUtil.bind(retval);

        preferences = getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);

        if (viewModel == null) {
            viewModel = new CourseSelectViewModel();
        }
        binding.setViewModel(viewModel);

        mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        bindViewModelElements();

        populateViewModelElements();

        loadPreviouslyPlayedCourses();
        selectCourseName();

        return retval;
    }

    private void populateViewModelElements() {
        courseNames = new ArrayList<>();
        rounds = new ArrayList<>();
    }

    private void bindViewModelElements() {
        viewModel.setPreviousCourseList(binding.savedCoursesList);
//        viewModel.setNewCourseButton(binding.newCourseButton);
        viewModel.setBeginRoundButton(binding.beginRoundButton);
    }

    private void loadPreviouslyPlayedCourses() {
        int numberOfRoundsToLoad;
        numberOfRoundsToLoad = preferences.getInt("roundsPlayed", 0);

        Gson gson = new Gson();

        for (int i = 0; i < numberOfRoundsToLoad; i++) {
            String objectToLoad = preferences.getString("round" + i, "");
            rounds.add(gson.fromJson(objectToLoad, RoundThing.class));
        }

        for (RoundThing round : rounds) {
            courseNames.add(round.getCourse().toUpperCase());
        }
        HashSet<String> set = new HashSet<>(courseNames);
        courseNames.clear();
        courseNames.addAll(set);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.course_list_item, courseNames);

        viewModel.getPreviousCourseList().setAdapter(adapter);

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
        binding.courseNameEt.setVisibility(View.VISIBLE);

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
                    viewModel.getBeginRoundButton().setVisibility(GONE);
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
        binding.courseNameEt.setVisibility(GONE);
        binding.previousCourseTv.setVisibility(GONE);
        binding.beginRoundButton.setVisibility(GONE);
        binding.courseNameInputLayout.setVisibility(GONE);
        viewModel.getPreviousCourseList().setVisibility(GONE);

        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(1);
        Round.setFrontNine(new Nine());
        Round.setBackNine(new Nine());
        Round.setDatePlayed(new Date());
        fragmentTransaction.replace(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.addToBackStack("CourseSelectionFragment");
        mgr.hideSoftInputFromWindow(binding.courseNameEt.getWindowToken(), 0);
        fragmentTransaction.commit();
    }
}
