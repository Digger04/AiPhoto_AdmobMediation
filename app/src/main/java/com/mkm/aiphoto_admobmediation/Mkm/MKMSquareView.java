package com.mkm.aiphoto_admobmediation.Mkm;

import android.content.Context;
import android.util.AttributeSet;

public class MKMSquareView extends MKMGridView {
    public MKMSquareView(Context context) {
        super(context);
    }

    public MKMSquareView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MKMSquareView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }


    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth > measuredHeight) {
            measuredWidth = measuredHeight;
        }
        setMeasuredDimension(measuredWidth, measuredWidth);
    }
}


