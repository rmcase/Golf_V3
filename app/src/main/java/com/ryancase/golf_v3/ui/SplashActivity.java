package com.ryancase.golf_v3.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.ryancase.golf_v3.Round;
import com.ryancase.golf_v3.RoundThing;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private String email;

    private DatabaseReference database;

    private List<RoundThing> rounds;
    private List<String> roundStrings;

    private FirebaseAuth auth;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        email = "";

        if (auth.getCurrentUser() != null) {
            currentUser = auth.getCurrentUser();
            email = currentUser.getEmail();
            Round.setRoundId(email);
        } else {
            Log.d("USER IS NULL:", "");
        }

        Log.d("USERMAIL:", "" + email);

        preferences = getSharedPreferences("PREF", MODE_PRIVATE);
        editor = preferences.edit();

        editor.clear();
        editor.commit();


        rounds = new ArrayList<>();
        roundStrings = new ArrayList<>();

        database = FirebaseDatabase.getInstance().getReference();

        Query roundQue;

        if (currentUser == null) {
            loadLoginActivity();
        } else {
            roundQue = database.child(currentUser.getUid());

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

                    for (int i = 0; i < rounds.size(); i++) {
                        if (rounds.get(i).getBackNine().getScore() == 0 || rounds.get(i).getFrontNine().getScore() == 0) {
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
                        editor.putString("round" + i, str);
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


                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void loadLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
