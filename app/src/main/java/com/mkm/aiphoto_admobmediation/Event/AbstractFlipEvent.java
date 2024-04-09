package com.mkm.aiphoto_admobmediation.Event;

import android.view.MotionEvent;

import com.mkm.aiphoto_admobmediation.Mkm.MKMStickerView;

public abstract class AbstractFlipEvent implements StickerIconEvent {
    protected abstract int getFlipDirection();

    public void onActionDown(MKMStickerView paramStickerView, MotionEvent paramMotionEvent) {
    }

    public void onActionMove(MKMStickerView paramStickerView, MotionEvent paramMotionEvent) {
    }

    public void onActionUp(MKMStickerView paramStickerView, MotionEvent paramMotionEvent) {
        paramStickerView.flipCurrentSticker(getFlipDirection());
    }
}