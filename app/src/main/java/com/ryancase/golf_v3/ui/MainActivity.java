package com.ryancase.golf_v3.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ryancase.golf_v3.Nine;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;

import java.util.Date;

import br.com.bloder.magic.view.MagicButton;

public class MainActivity extends AppCompatActivity {

    private final String FRAGMENT_TAG = "HOLE";
//    private Button startButton;
//    private Button historyButton;
//    private Button statisticsButton;
    private MagicButton startButton, historyButton, statisticsButton;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

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

        if (auth.getCurrentUser() != null) {
            currentUser = auth.getCurrentUser();
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

//        MagicButton magicButton = (MagicButton) findViewById(R.id.magic_button);
//        magicButton.setMagicButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadHoleFragment();
//            }
//        });

    }

    private void loadHoleFragment() {
        Nine front = new Nine();
        Nine back = new Nine();
        Round.setFrontNine(front);
        Round.setBackNine(back);
//        Round.setRoundId(currentUser.getEmail());
        Round.setRoundId("r.c8700@gmail.com");
//        Round.setCourse("Bear Creek");
        Round.setDatePlayed(new Date());

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CourseSelectionFragment selectionFragment = new CourseSelectionFragment();
        startButton.setVisibility(View.INVISIBLE);
        historyButton.setVisibility(View.INVISIBLE);
        statisticsButton.setVisibility(View.INVISIBLE);
        fragmentTransaction.add(R.id.content_view, selectionFragment, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    private void loadHistoryFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HistoryFragment hist = new HistoryFragment();
        startButton.setVisibility(View.INVISIBLE);
        historyButton.setVisibility(View.INVISIBLE);
        statisticsButton.setVisibility(View.INVISIBLE);
        fragmentTransaction.add(R.id.content_view, hist, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    private void loadStatisticsFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StatisticsFragment stats = new StatisticsFragment();
        startButton.setVisibility(View.INVISIBLE);
        historyButton.setVisibility(View.INVISIBLE);
        statisticsButton.setVisibility(View.INVISIBLE);
        fragmentTransaction.add(R.id.content_view, stats, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }
}
