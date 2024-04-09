package com.mkm.aiphoto_admobmediation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mkm.aiphoto_admobmediation.Model.PathModelPix;
import com.mkm.aiphoto_admobmediation.R;
import com.mkm.aiphoto_admobmediation.Support.Constants;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {
    public void isPermissionGranted(boolean z, String str) {}

    public void setFullScreen() {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 52) {
            isPermissionGranted(iArr[0] == 0, strArr[0]);
        }
    }

    public ArrayList<PathModelPix> getIconAllFrames() {
        ArrayList<PathModelPix> arrSticker = new ArrayList<>();
        for (int i = 1; i <= Constants.PIX_CATEGORY_SIZE; i++) {
            PathModelPix pathModel = new PathModelPix();
            pathModel.setPathInt(getResources().getIdentifier(Constants.PIX_ICON_FILE_NAME + i, "drawable", getPackageName()));
            arrSticker.add(pathModel);
        }
        return arrSticker;
    }

    public ArrayList<PathModelPix> getMaskAll() {
        ArrayList<PathModelPix> arrSticker = new ArrayList<>();
        for (int i = 1; i <= Constants.PIX_CATEGORY_SIZE; i++) {
            PathModelPix pathModel = new PathModelPix();
            pathModel.setPathInt(getResources().getIdentifier(Constants.PIX_MASK_FILE_NAME + i, "drawable", getPackageName()));
            arrSticker.add(pathModel);
        }
        return arrSticker;
    }


}

