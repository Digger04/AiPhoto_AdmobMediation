package com.mkm.aiphoto_admobmediation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.mkm.aiphoto_admobmediation.Adapter.Adapter_share;
import com.mkm.aiphoto_admobmediation.Model.model_share;
import com.mkm.aiphoto_admobmediation.R;

import java.util.ArrayList;
import java.util.List;

public class FinishActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private List<model_share> list;
    private Adapter_share adapter_share;

    private ImageView img_picture;
    private ImageView img_back;
    private ImageView img_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_finish);

        initView();
        Back();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void Back() {
        img_back.setOnClickListener(view -> {
            finish();
        });
        img_home.setOnClickListener(view -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }

    private void initView() {
        recyclerview = findViewById(R.id.recyclerview);
        img_picture = findViewById(R.id.img_picture);
        img_back = findViewById(R.id.img_back);
        img_home = findViewById(R.id.img_home);

        img_picture.setImageBitmap(MKMCollageActivity.bm);

        list = new ArrayList<>();
        list.add(new model_share(getDrawable(R.drawable.share), "Other"));
        list.add(new model_share(getDrawable(R.drawable.ic_facebook), "Faceboook"));
        list.add(new model_share(getDrawable(R.drawable.instagram), "Instagram"));
        list.add(new model_share(getDrawable(R.drawable.twitter), "Twitter"));
        list.add(new model_share(getDrawable(R.drawable.whatsapp), "Whatsapp"));
        list.add(new model_share(getDrawable(R.drawable.messenger), "Messenger"));

        adapter_share = new Adapter_share(list, getApplicationContext());

        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        recyclerview.setAdapter(adapter_share);
    }
}