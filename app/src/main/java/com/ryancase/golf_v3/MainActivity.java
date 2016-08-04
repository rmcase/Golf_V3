package com.ryancase.golf_v3;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String FRAGMENT_TAG = "HOLE";
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    private void loadHoleFragment() {
        Nine front = new Nine();
        Nine back = new Nine();
        Round.setFrontNine(front);
        Round.setBackNine(back);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HoleFragment hole = new HoleFragment(1);
        startButton.setVisibility(View.INVISIBLE);
        fragmentTransaction.add(R.id.content_view, hole, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }


}
