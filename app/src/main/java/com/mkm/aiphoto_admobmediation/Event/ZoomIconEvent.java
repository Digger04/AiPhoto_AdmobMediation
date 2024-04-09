package com.mkm.aiphoto_admobmediation.Event;

import android.view.MotionEvent;

import com.mkm.aiphoto_admobmediation.Mkm.MKMStickerView;

public class ZoomIconEvent implements StickerIconEvent {
    public void onActionDown(MKMStickerView paramStickerView, MotionEvent paramMotionEvent) {
    }

    public void onActionMove(MKMStickerView paramStickerView, MotionEvent paramMotionEvent) {
        paramStickerView.zoomAndRotateCurrentSticker(paramMotionEvent);
    }

    public void onActionUp(MKMStickerView paramStickerView, MotionEvent paramMotionEvent) {
        if (paramStickerView.getOnStickerOperationListener() != null)
            paramStickerView.getOnStickerOperationListener().onStickerZoom(paramStickerView.getCurrentSticker());
    }
}

