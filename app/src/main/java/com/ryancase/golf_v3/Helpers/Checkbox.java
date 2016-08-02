package com.ryancase.golf_v3.Helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.ryancase.golf_v3.R;

/**
 * Created by ryancase on 11/4/15.
 */
public class Checkbox extends CheckBox {

    int check = 0;

    public Checkbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setButtonDrawable(new StateListDrawable());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void setChecked(boolean t) {
        if (t) {
            this.setBackgroundResource(R.drawable.check_select);
            this.setTextColor(Color.GREEN);
            check = 1;
        } else {
            this.setBackgroundResource(R.drawable.check_deselect);
            this.setTextColor(getResources().getColor(R.color.dgray));
            check = 2;
        }
        super.setChecked(t);
        super.setTextAlignment(TEXT_ALIGNMENT_CENTER);
    }

    public int checker() {
        if (check == 1)
            return 1;
        else
            return 0;
    }
}
