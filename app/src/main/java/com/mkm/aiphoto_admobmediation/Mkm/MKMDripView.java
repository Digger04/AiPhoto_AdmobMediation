package com.mkm.aiphoto_admobmediation.Mkm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.mkm.aiphoto_admobmediation.Drip.Imagescale.TouchListener;

public class MKMDripView extends AppCompatImageView {
    TouchListener multiTouchListener;

    public MKMDripView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MKMDripView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        setPadding(0, 0, 0, 0);
    }

    public MKMDripView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.multiTouchListener = null;
        initBorderPaint();
    }

    private void initBorderPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(-1);
        paint.setStrokeWidth(0.0f);
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setOnTouchListenerCustom(TouchListener multiTouchListener2) {
        this.multiTouchListener = multiTouchListener2;
        setOnTouchListener(multiTouchListener2);
    }
}

