package com.mkm.aiphoto_admobmediation.Listener;

import com.mkm.aiphoto_admobmediation.Draw.Drawing;

public interface OnMKMEditorListener {
    void onAddViewListener(Drawing viewType, int i);


    void onRemoveViewListener(int i);

    void onRemoveViewListener(Drawing viewType, int i);

    void onStartViewChangeListener(Drawing viewType);

    void onStopViewChangeListener(Drawing viewType);
}
