package com.mkm.aiphoto_admobmediation;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;
import com.zeugmasolutions.localehelper.LocaleHelper;
import com.zeugmasolutions.localehelper.LocaleHelperApplicationDelegate;

import org.json.JSONObject;

public class MKM extends Application {
    private static MKM queShot;

    public static Context context;

    private static MKM instance;


    public static int number_layout;
    public static int number_filter;
    public static int number_color;
    public static int number_border;
    public static int number_ratio;
    public static int number_radius;
    public static int number_gradient;
    public static int number_tabsticker = -1;
    public static int number_sticker = -1;
    public static String text;

    public static String namep;
    public static String linkp;

    public static boolean is_open_app_from_noti;

    public static MKM getInstance() {
        return instance;
    }

    public static MMKV mmkv;

    private LocaleHelperApplicationDelegate localeAppDelegate = new LocaleHelperApplicationDelegate();

    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                StrictMode.class.getMethod("disableDeathOnFileUriExposure", new Class[0]).invoke( null, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        MMKV.initialize(this);
        mmkv = MMKV.defaultMMKV();
        context = this;
    }

    public static Context getContext() {
        return queShot.getContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(localeAppDelegate.attachBaseContext(base));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localeAppDelegate.onConfigurationChanged(this);
    }

    @Override
    public Context getApplicationContext() {
        return LocaleHelper.INSTANCE.onAttach(super.getApplicationContext());
    }

}
