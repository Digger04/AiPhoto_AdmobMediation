package com.mkm.aiphoto_admobmediation.Mkm;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.mkm.aiphoto_admobmediation.Event.StickerIconEvent;
import com.mkm.aiphoto_admobmediation.Sticker.DrawableSticker;

public class MKMStickerIcons extends DrawableSticker implements StickerIconEvent {
    public static final String ALIGN = "ALIGN";

    public static final String EDIT = "EDIT";

    public static final String FLIP = "FLIP";

    public static final String DELETE = "DELETE";

    public static final String ROTATE = "ROTATE";

    public static final String SCALE = "SCALE";

    private StickerIconEvent iconEvent;

    private float iconExtraRadius = 10.0F;

    private float iconRadius = 30.0F;

    private int position = 0;

    private String tag;

    private float x;

    private float y;

    public MKMStickerIcons(Drawable paramDrawable, int paramInt, String paramString) {
        super(paramDrawable);
        this.position = paramInt;
        this.tag = paramString;
    }

    public void draw(Canvas paramCanvas, Paint paramPaint) {
        paramCanvas.drawCircle(this.x, this.y, this.iconRadius, paramPaint);
        draw(paramCanvas);
    }

    public float getIconRadius() {
        return this.iconRadius;
    }

    public int getPosition() {
        return this.position;
    }

    public String getTag() {
        return this.tag;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void onActionDown(MKMStickerView paramStickerView, MotionEvent paramMotionEvent) {
        if (this.iconEvent != null)
            this.iconEvent.onActionDown(paramStickerView, paramMotionEvent);
    }

    public void onActionMove(MKMStickerView paramStickerView, MotionEvent paramMotionEvent) {
        if (this.iconEvent != null)
            this.iconEvent.onActionMove(paramStickerView, paramMotionEvent);
    }

    public void onActionUp(MKMStickerView paramStickerView, MotionEvent paramMotionEvent) {
        if (this.iconEvent != null)
            this.iconEvent.onActionUp(paramStickerView, paramMotionEvent);
    }

    public void setIconEvent(StickerIconEvent paramStickerIconEvent) {
        this.iconEvent = paramStickerIconEvent;
    }


    public void setTag(String paramString) {
        this.tag = paramString;
    }

    public void setX(float paramFloat) {
        this.x = paramFloat;
    }

    public void setY(float paramFloat) {
        this.y = paramFloat;
    }

}


