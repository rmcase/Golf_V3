package com.ryancase.golf_v3.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ryancase.golf_v3.R;
import com.ryancase.golf_v3.Round;

public class MainActivity extends AppCompatActivity {

    private final String FRAGMENT_TAG = "HOLE";

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private String email;

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

        Log.d("USEREMAIL:", "" + email);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


}
