package com.ryancase.golf_v3.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ryancase.golf_v3.R;

public class MainActivity extends AppCompatActivity {

    private final String FRAGMENT_TAG = "HOLE";

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
        setContentView(R.layout.activity_main_tabs);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        auth = FirebaseAuth.getInstance();

        email = "";

        if (auth.getCurrentUser() != null) {
            currentUser = auth.getCurrentUser();
            email = currentUser.getEmail();
        }


    }

    public void loadHistory() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HistoryFragment hist = new HistoryFragment();
        fragmentTransaction.add(R.id.content_view, hist, FRAGMENT_TAG);
        fragmentTransaction.commit();
    }


}

class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"New Round", "History", "Statistics"};
    private Context context;

    public SampleFragmentPagerAdapter(android.support.v4.app.FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new OverviewTabFragment();
            }
            case 1: {
                return new CourseSelectionFragment();
            }
            case 2: {
                return new HistoryFragment();
            }
            case 3: {
                return new StatisticsFragment();
            }
            default: {
                return new Fragment();
            }
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int[] imageResId = {
                R.drawable.profile,
                R.drawable.golf_flag,
                R.drawable.bulletlist_sm,
                R.drawable.linegraph_sm
        };

        Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;

        // Generate title based on item position
//        return tabTitles[position];
    }
}
