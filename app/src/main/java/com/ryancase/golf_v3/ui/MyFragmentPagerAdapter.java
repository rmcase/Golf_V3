package com.ryancase.golf_v3.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.ryancase.golf_v3.R;

/**
 * Created by ryancase on 11/23/16.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"Profile", "Course Select", "History", "Statistics"};
    private Context context;

    public MyFragmentPagerAdapter(android.support.v4.app.FragmentManager fm, Context context) {
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
                return new ProfileFragment();
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
                return new ProfileFragment();
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
