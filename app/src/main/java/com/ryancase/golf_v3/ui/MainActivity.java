package com.ryancase.golf_v3.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void loadRounds() {
        database = FirebaseDatabase.getInstance().getReference();
//        Query roundQue = database.child("Rounds").orderByChild("roundId").equalTo("r.c8700@gmail.com");
        Query roundQue = database.child("Rounds").orderByChild("roundId").equalTo(Round.getRoundId());

        roundQue.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RoundThing round = dataSnapshot.getValue(RoundThing.class);
                rounds.add(round);

                int totalStrokes = 0;
                float scoAvg = 0;
                int roundsPlayed = 0;

                Gson gson = new Gson();

                roundsPlayed = rounds.size();

                for(int i=0; i< roundsPlayed; i++) {
                    String str = gson.toJson(rounds.get(i));
                    roundStrings.add(str);
                    editor.putString("round"+i, str);
                    totalStrokes += (rounds.get(i).getBackNine().getScore() + rounds.get(i).getFrontNine().getScore());
                }

                scoAvg = (float) totalStrokes / rounds.size();

                editor.putFloat("scoAvg", scoAvg);
                editor.putInt("roundsPlayed", roundsPlayed);
                editor.putInt("totalStrokes", totalStrokes);

                editor.commit();

                // Get the ViewPager and set it's PagerAdapter so that it can display items
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),
                        MainActivity.this));

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
