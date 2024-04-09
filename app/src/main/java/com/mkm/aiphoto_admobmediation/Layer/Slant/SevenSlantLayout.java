package com.mkm.aiphoto_admobmediation.Layer.Slant;

import com.mkm.aiphoto_admobmediation.Mkm.Grid.PolishLayout;
import com.mkm.aiphoto_admobmediation.Mkm.Grid.PolishLine;

public class SevenSlantLayout extends NumberSlantLayout {
    public int getThemeCount() {
        return 2;
    }

    public SevenSlantLayout(SlantCollageLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
    }

    public SevenSlantLayout(int i) {
        super(i);
    }

    public void layout() {
        if (this.theme == 0) {
            addLine(0, PolishLine.Direction.VERTICAL, 0.33333334f);
            addLine(1, PolishLine.Direction.VERTICAL, 0.5f);
            addLine(0, PolishLine.Direction.HORIZONTAL, 0.5f, 0.5f);
            addLine(1, PolishLine.Direction.HORIZONTAL, 0.33f, 0.33f);
            addLine(3, PolishLine.Direction.HORIZONTAL, 0.5f, 0.5f);
            addLine(2, PolishLine.Direction.HORIZONTAL, 0.5f, 0.5f);
        }
    }

    public PolishLayout clone(PolishLayout quShotLayout) {
        return new SevenSlantLayout((SlantCollageLayout) quShotLayout, true);
    }
}
