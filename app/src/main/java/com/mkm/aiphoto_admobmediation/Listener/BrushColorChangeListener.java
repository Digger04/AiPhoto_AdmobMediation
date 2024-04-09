package com.mkm.aiphoto_admobmediation.Listener;

import com.mkm.aiphoto_admobmediation.Draw.BrushDrawingView;

public interface BrushColorChangeListener {
    void onStartDrawing();

    void onStopDrawing();

    void onViewAdd(BrushDrawingView brushDrawingView);

    void onViewRemoved(BrushDrawingView brushDrawingView);
}

