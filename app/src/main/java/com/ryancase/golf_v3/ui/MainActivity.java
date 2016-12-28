package com.ryancase.golf_v3.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.gson.Gson;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.RoundThing;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String FRAGMENT_TAG = "HOLE";

    private FirebaseUser currentUser;
    private String email;

    private DatabaseReference database;

    private List<RoundThing> rounds;
    private List<String> roundStrings;

    private FirebaseAuth auth;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabs);

        auth = FirebaseAuth.getInstance();

        email = "";

        if (auth.getCurrentUser() != null) {
            currentUser = auth.getCurrentUser();
            email = currentUser.getEmail();
            Round.setRoundId(email);
        } else {
            Log.d("USER IS NULL:", "");
        }

        Log.d("USREMAIL:", "" + email);

        preferences = getSharedPreferences("PREF", MODE_PRIVATE);
        editor = preferences.edit();

        editor.clear();
        editor.commit();


        rounds = new ArrayList<>();
        roundStrings = new ArrayList<>();

        loadRounds();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void loadRounds() {
        database = FirebaseDatabase.getInstance().getReference();

        Query roundQue;

        if(currentUser == null) {
            roundQue = database.child("2A5wfxt9uScm8zlcxxkwj8J6rB42");
        } else {
            roundQue = database.child(currentUser.getUid());
        }

        roundQue.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RoundThing round = dataSnapshot.getValue(RoundThing.class);
                rounds.add(round);

                int totalStrokes = 0;
                float scoAvg, nineScoAvg;
                float roundsPlayed = 0;
                float fullRounds = 0;
                int halfRounds = 0;
                int allTimeScoreToPar = 0, allTimeScoreToParNine = 0;
                int strokesFromHalfRounds = 0;
                int strokesFromFullRounds = 0;

                Gson gson = new Gson();

                for(int i=0; i< rounds.size(); i++) {
                    if(rounds.get(i).getBackNine().getScore() == 0 || rounds.get(i).getFrontNine().getScore() == 0) {
                        roundsPlayed += 0.5f;
                        halfRounds++;
                        allTimeScoreToParNine += (rounds.get(i).getBackNine().getScoreToPar() + rounds.get(i).getFrontNine().getScoreToPar());
                        strokesFromHalfRounds += (rounds.get(i).getBackNine().getScore() + rounds.get(i).getFrontNine().getScore());
                    } else {
                        fullRounds++;
                        allTimeScoreToPar += (rounds.get(i).getBackNine().getScoreToPar() + rounds.get(i).getFrontNine().getScoreToPar());
                        strokesFromFullRounds += (rounds.get(i).getBackNine().getScore() + rounds.get(i).getFrontNine().getScore());
                        roundsPlayed++;
                    }

                    String str = gson.toJson(rounds.get(i));
                    roundStrings.add(str);
                    editor.putString("round"+i, str);
                    totalStrokes += (rounds.get(i).getBackNine().getScore() + rounds.get(i).getFrontNine().getScore());
                }

                int fullRoundsInt = (int) fullRounds;

                nineScoAvg = (float) strokesFromHalfRounds / halfRounds;
                scoAvg = (float) strokesFromFullRounds / fullRounds;

                editor.putInt("allTimeScoreToPar", allTimeScoreToPar);
                editor.putInt("allTimeScoreToParNine", allTimeScoreToParNine);
                editor.putFloat("scoAvg", scoAvg);
                editor.putFloat("nineScoAvg", nineScoAvg);
                editor.putInt("fullRounds", fullRoundsInt);
                editor.putInt("halfRounds", halfRounds);
                editor.putInt("roundsPlayed", rounds.size());
                editor.putInt("totalStrokes", totalStrokes);

                editor.commit();

                // Get the ViewPager and set it's PagerAdapter so that it can display items
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),
                        MainActivity.this));

                viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        switch (position) {
                            case 0: {
                                setTitle("Profile");
                                break;
                            }
                            case 1: {
                                setTitle("Play");
                                break;
                            }
                            case 2: {
                                setTitle("History");
                                break;
                            }
                            case 3: {
                                setTitle("Statistics");
                                break;
                            }
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                // Give the TabLayout the ViewPager
                TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
                tabLayout.setupWithViewPager(viewPager);
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
    }

}
