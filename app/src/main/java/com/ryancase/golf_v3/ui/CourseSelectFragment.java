package com.ryancase.golf_v3.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import java.util.List;


/**
 * File description here...
 */

public class CourseSelectFragment extends Fragment implements HoleView {

    private final String FRAGMENT_TAG = "HOLE";

    private FragmentCourseSelectBinding binding;

    private CourseSelectViewModel viewModel;

    private String title;

    private Button startButton;

    private DatabaseReference reference;

    private List<RoundModel> modelList;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    public CourseSelectFragment() {

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

        viewModel.setTitle("Course Select");

        startButton = binding.newCourseButton;

        startButton.setText("Add Course");

        loadPlayedCourses();

        return retval;
    }

    private void loadPlayedCourses() {
        reference.addValueEventListener(new ValueEventListener() {
            List<RoundModel> roundModels = new ArrayList<RoundModel>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CourseSelectViewModel viewModel;
                viewModel = new CourseSelectViewModel();

                binding.setViewModel(viewModel);

                RecyclerView recyclerView = binding.coursesRecycler;

                recyclerView.setLayoutManager(new LinearLayoutManager());
                viewModel.setTitle("Course Select");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("roundId").getValue().equals(currentUser.getUid())) {
                        roundModels.add(snapshot.getValue(RoundModel.class));
                    }
                    Log.d("List size", "" + roundModels.size());
                }

                viewModel.setCourseName(roundModels.get(0).getCourse());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadHoleFragment() {
        Nine front = new Nine();
        Nine back = new Nine();
        Round.setFrontNine(front);
        Round.setBackNine(back);
        Round.setRoundId(currentUser.getUid());
        Round.setCourse("Bear Creek");
        Round.setDatePlayed(new Date());

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(1);
        startButton.setVisibility(View.INVISIBLE);
        fragmentTransaction.add(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }
}
