package com.mkm.aiphoto_admobmediation.Event;

import com.mkm.aiphoto_admobmediation.Entity.Photo;

public interface Selectable {

    int getSelectedItemCount();

    boolean isSelected(Photo photo);

}

