package com.ryancase.golf_v3.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private Button historyButton;
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
            currentUser = auth.getCurrentUser();
        }

        startButton = (Button) findViewById(R.id.startButton);
        if (startButton != null) {
            startButton.setVisibility(View.VISIBLE);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHoleFragment();
            }
        });


        historyButton = (Button) findViewById(R.id.historyButton);

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHistoryFragment();
            }
        });

    }

    private void loadHoleFragment() {
        Nine front = new Nine();
        Nine back = new Nine();
        Round.setFrontNine(front);
        Round.setBackNine(back);
//        Round.setRoundId(currentUser.getUid());
        Round.setRoundId("r.c8700@gmail.com");
        Round.setCourse("bear creek");
        Round.setDatePlayed(new Date());

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(1);
        startButton.setVisibility(View.INVISIBLE);
        historyButton.setVisibility(View.INVISIBLE);
        fragmentTransaction.add(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    private void loadHistoryFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HistoryFragment hist = new HistoryFragment();
        startButton.setVisibility(View.INVISIBLE);
        historyButton.setVisibility(View.INVISIBLE);
        fragmentTransaction.add(R.id.content_view, hist, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }


}
