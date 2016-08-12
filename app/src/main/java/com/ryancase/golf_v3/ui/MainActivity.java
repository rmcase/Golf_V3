package com.ryancase.golf_v3.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ryancase.golf_v3.Nine;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final String FRAGMENT_TAG = "HOLE";
    private Button startButton;
    private Button logoutButton;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            Log.d("Current User: ", "" + auth.getCurrentUser().getEmail());
            currentUser = auth.getCurrentUser();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        startButton = (Button) findViewById(R.id.startButton);
        if (startButton != null) {
            startButton.setVisibility(View.VISIBLE);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCourseSelectFragment();
            }
        });
    }

    private void loadCourseSelectFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CourseSelectFragment hole = new CourseSelectFragment();
        startButton.setVisibility(View.INVISIBLE);
        fragmentTransaction.add(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }


}
