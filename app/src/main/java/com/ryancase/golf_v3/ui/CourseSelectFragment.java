package com.ryancase.golf_v3.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ryancase.golf_v3.HoleView;
import com.ryancase.golf_v3.Nine;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.RoundModel;
import com.ryancase.golf_v3.ViewModels.CourseSelectViewModel;
import com.ryancase.golf_v3.databinding.FragmentCourseSelectBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * File description here...
 */

public class CourseSelectFragment extends Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private FragmentCourseSelectBinding binding;

    private CourseSelectViewModel viewModel;

    private String title;

    private Button addCourse;
    private EditText newCourseName;

    private DatabaseReference reference;

    private List<RoundModel> modelList;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    public CourseSelectFragment() {

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

        getActionBar().setTitle("Course Select");

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        binding = DataBindingUtil.bind(retval);

        modelList = new ArrayList<>();

        if (viewModel == null) {
            viewModel = new CourseSelectViewModel();
        }
        binding.setViewModel(viewModel);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("Rounds");

        newCourseName = binding.newCourseName;
        addCourse = binding.newCourseButton;

        addCourse.setText("Add Course");

        loadPlayedCourses();

        return retval;
    }

    private void loadPlayedCourses() {
        reference.addValueEventListener(new ValueEventListener() {
            List<String> courseNames = new ArrayList<String>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final CourseSelectViewModel viewModel;
                viewModel = new CourseSelectViewModel();
                binding.setViewModel(viewModel);

                ListView roundListView = binding.courseListView;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("roundId").getValue().equals(currentUser.getUid())) {
                        courseNames.add(snapshot.getValue(RoundModel.class).getCourse());
                    }
                    Log.d("List size", "" + courseNames.size());
                }

                addCourse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        courseNames.add(newCourseName.getText().toString());

                        courseNames = polishList(courseNames);
                    }
                });

                //Removing Duplicate Course Names
                courseNames = polishList(courseNames);

                viewModel.setCourseList(courseNames);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(roundListView.getContext(), R.layout.course_list_item, viewModel.getCourseList());
                roundListView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

                roundListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedCourse = viewModel.getCourseList().get(position);
                        loadHoleFragment(selectedCourse);
                    }
                });
            }

            /**
             * Removes duplicate items
             * Capitalizes words
             *
             * @param list
             * @return polished list
             */
            public List<String> polishList(List<String> list) {
                List<String> retval;
                retval = list;

                Set<String> hashSet = new HashSet<>();
                hashSet.addAll(retval);
                retval.clear();
                retval.addAll(hashSet);

                for(String s : list) {
                    
                }

                return retval;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private android.support.v7.app.ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    private void loadHoleFragment(String course) {
        Nine front = new Nine();
        Nine back = new Nine();
        Round.setFrontNine(front);
        Round.setBackNine(back);
        Round.setRoundId(currentUser.getUid());
        Round.setCourse(course);
        Round.setDatePlayed(new Date());

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(1);
        addCourse.setVisibility(View.INVISIBLE);
        fragmentTransaction.add(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }
}
