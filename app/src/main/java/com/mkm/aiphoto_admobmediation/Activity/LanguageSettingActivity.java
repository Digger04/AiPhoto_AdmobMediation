package com.mkm.aiphoto_admobmediation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mkm.aiphoto_admobmediation.Adapter.ILanguageAdapter;
import com.mkm.aiphoto_admobmediation.Adapter.LanguageAdapter;
import com.mkm.aiphoto_admobmediation.MKM;
import com.mkm.aiphoto_admobmediation.Model.LanguageCode;
import com.mkm.aiphoto_admobmediation.MyUtils;
import com.mkm.aiphoto_admobmediation.R;
import com.mkm.aiphoto_admobmediation.SharedPreferencesUtils;
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity;
import com.zeugmasolutions.localehelper.LocaleHelper;
import com.zeugmasolutions.localehelper.LocaleHelperActivityDelegate;
import com.zeugmasolutions.localehelper.LocaleHelperActivityDelegateImpl;

import java.util.Locale;

public class LanguageSettingActivity extends LocaleAwareCompatActivity implements ILanguageAdapter {

    private ImageView back_language;
    private Window window;

    private final LocaleHelperActivityDelegate localeDelegate = new LocaleHelperActivityDelegateImpl();
    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return localeDelegate.getAppCompatDelegate(super.getDelegate());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localeDelegate.onCreate(this);
        setFullScreen();
        setContentView(R.layout.activity_language_setting);

        setStatusbar();
        back_language = findViewById(R.id.back_language);

        back_language.setOnClickListener(view -> {
            finish();
        });

        RecyclerView recyclerView = findViewById(R.id.rv_language);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        LanguageAdapter adapter = new LanguageAdapter(this, LanguageCode.languages, this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.back_language).setOnClickListener(view -> finish());
    }

    public void setFullScreen() {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
    }

    private void setStatusbar() {
        window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick2(String value) {
        Locale mCurrentLocale = MyUtils.convertLanguage(value);
        localeDelegate.setLocale(this, mCurrentLocale);
        MKM.mmkv.encode(SharedPreferencesUtils.KEY_LOCALE_LANGUAGE, LanguageCode.getLanguageCode(value));
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }

    @NonNull
    @Override
    public Context createConfigurationContext(@NonNull Configuration overrideConfiguration) {
        Context context = super.createConfigurationContext(overrideConfiguration);
        return LocaleHelper.INSTANCE.onAttach(context);
    }

    @NonNull
    @Override
    public Context getApplicationContext() {
        return localeDelegate.getApplicationContext(super.getApplicationContext());
    }
}