package com.mkm.aiphoto_admobmediation.Event;

import android.view.MotionEvent;

import com.mkm.aiphoto_admobmediation.Mkm.MKMStickerView;

public interface StickerIconEvent {
    void onActionDown(MKMStickerView paramStickerView, MotionEvent paramMotionEvent);

    void onActionMove(MKMStickerView paramStickerView, MotionEvent paramMotionEvent);

    void onActionUp(MKMStickerView paramStickerView, MotionEvent paramMotionEvent);
}

