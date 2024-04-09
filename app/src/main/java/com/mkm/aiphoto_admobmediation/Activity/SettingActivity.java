package com.mkm.aiphoto_admobmediation.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.mkm.aiphoto_admobmediation.R;

public class SettingActivity extends AppCompatActivity {

    private LinearLayout linearLayoutLanguage;
    private LinearLayout linearLayoutApps;
    private LinearLayout linearLayoutFeedback;
    private LinearLayout linearLayoutPrivacy;
    private LinearLayout linearLayoutRate;
    private ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_setting);


        initView();
        ChangeLanguage();
        Back();
    }

    private void Back() {
        img_back.setOnClickListener(view -> {
            finish();
        });
    }

    private void ChangeLanguage() {
        linearLayoutLanguage.setOnClickListener(view -> {
            startActivity(new Intent(this, LanguageSettingActivity.class));
        });
    }

    private void initView() {
        linearLayoutLanguage = findViewById(R.id.linearLayoutLanguage);
        linearLayoutApps = findViewById(R.id.linearLayoutApps);
        linearLayoutFeedback = findViewById(R.id.linearLayoutFeedback);
        linearLayoutPrivacy = findViewById(R.id.linearLayoutPrivacy);
        linearLayoutRate = findViewById(R.id.linearLayoutRate);
        img_back = findViewById(R.id.img_back);
    }
}
