package com.mkm.aiphoto_admobmediation.Picker;

import androidx.annotation.RequiresApi;

public class PermissionConstant {
    public static final String[] PERMISSIONS_CAMERA = {"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    @RequiresApi(api = 16)
    public static final String[] PERMISSIONS_EXTERNAL_READ = {"android.permission.READ_EXTERNAL_STORAGE"};
    public static final String[] PERMISSIONS_EXTERNAL_WRITE = {"android.permission.WRITE_EXTERNAL_STORAGE"};

}
