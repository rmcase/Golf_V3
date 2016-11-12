package com.ryancase.golf_v3.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ryancase.golf_v3.Nine;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;

import java.util.Date;

import br.com.bloder.magic.view.MagicButton;
import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {

    private final String FRAGMENT_TAG = "HOLE";
    private MagicButton startButton, historyButton, statisticsButton;
    private FButton signOutButton;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private String email;

    @Override
    public void onBackPressed() {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return true;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        email = "";

        if (auth.getCurrentUser() != null) {
            currentUser = auth.getCurrentUser();
            email = currentUser.getEmail();
        }

        startButton = (MagicButton) findViewById(R.id.startButton);
        if (startButton != null) {
            startButton.setVisibility(View.VISIBLE);
        }

        startButton.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHoleFragment();
            }
        });


        historyButton = (MagicButton) findViewById(R.id.historyButton);

        historyButton.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHistoryFragment();
            }
        });


        statisticsButton = (MagicButton) findViewById(R.id.statisticsButton);

        statisticsButton.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadStatisticsFragment();
            }
        });

        signOutButton = (FButton) findViewById(R.id.signOutButton);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Log.d("Current User:", "" + auth.getCurrentUser());

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


    }

    private void loadHoleFragment() {
        Nine front = new Nine();
        Nine back = new Nine();
        Round.setFrontNine(front);
        Round.setBackNine(back);
        Round.setRoundId(email);
//        Round.setRoundId("r.c8700@gmail.com");
        Round.setDatePlayed(new Date());

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CourseSelectionFragment selectionFragment = new CourseSelectionFragment();
        startButton.setVisibility(View.GONE);
        historyButton.setVisibility(View.GONE);
        statisticsButton.setVisibility(View.GONE);
        signOutButton.setVisibility(View.GONE);
        fragmentTransaction.add(R.id.content_view, selectionFragment, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    private void loadHistoryFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HistoryFragment hist = new HistoryFragment();
        startButton.setVisibility(View.GONE);
        historyButton.setVisibility(View.GONE);
        statisticsButton.setVisibility(View.GONE);
        signOutButton.setVisibility(View.GONE);
        fragmentTransaction.add(R.id.content_view, hist, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    private void loadStatisticsFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StatisticsFragment stats = new StatisticsFragment();
        startButton.setVisibility(View.GONE);
        historyButton.setVisibility(View.GONE);
        statisticsButton.setVisibility(View.GONE);
        signOutButton.setVisibility(View.GONE);
        fragmentTransaction.add(R.id.content_view, stats, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }
}
