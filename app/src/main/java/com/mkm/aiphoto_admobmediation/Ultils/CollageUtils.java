package com.mkm.aiphoto_admobmediation.Ultils;

import com.mkm.aiphoto_admobmediation.Layer.Slant.SlantLayoutHelper;
import com.mkm.aiphoto_admobmediation.Layer.Straight.StraightLayoutHelper;
import com.mkm.aiphoto_admobmediation.Mkm.Grid.PolishLayout;

import java.util.ArrayList;
import java.util.List;

public class CollageUtils {

    private CollageUtils() {}

    public static List<PolishLayout> getCollageLayouts(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(SlantLayoutHelper.getAllThemeLayout(i));
        arrayList.addAll(StraightLayoutHelper.getAllThemeLayout(i));
        return arrayList;
    }
}
