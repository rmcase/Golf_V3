package com.ryancase.golf_v3.Helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ryancase on 11/13/15.
 */
public class TextViewCust extends TextView {

    public TextViewCust(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/BebasNeue.otf"));
    }
}
