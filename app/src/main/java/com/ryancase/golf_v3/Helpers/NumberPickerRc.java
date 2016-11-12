package com.ryancase.golf_v3.Helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.ryancase.golf_v3.R;

/**
 * Created by ryancase on 11/11/16.
 */

public class NumberPickerRc extends android.widget.NumberPicker {

    public NumberPickerRc(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    private void updateView(View view) {
        if(view instanceof EditText){
            ((EditText) view).setTextSize(16);
            ((EditText) view).setTextColor(getResources().getColor(R.color.fDarkPurple));
        }
    }

}
