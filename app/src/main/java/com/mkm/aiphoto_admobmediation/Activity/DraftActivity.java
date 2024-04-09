package com.mkm.aiphoto_admobmediation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mkm.aiphoto_admobmediation.Adapter.Adapter_Project;
import com.mkm.aiphoto_admobmediation.Database.DataProject;
import com.mkm.aiphoto_admobmediation.Database.Model_project;
import com.mkm.aiphoto_admobmediation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DraftActivity extends AppCompatActivity {

    private ImageView img_back;

    private List<Model_project> list;
    private Adapter_Project adapter_project;

    private RecyclerView recyclerview;
    private LinearLayout layout_null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_draft);

        initView();
        Back();
        getData();
    }

    @Override
    protected void onResume() {

        try {
            getData();
        }catch (Exception e) {

        }

        super.onResume();
    }

    private void getData() {
        list = new ArrayList<>();
        list = DataProject.getInstance(getApplicationContext()).daoSql().getall();

        if (list.size() > 0) {
            layout_null.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);
        }

        Collections.reverse(list);

        adapter_project = new Adapter_Project(this, list);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setAdapter(adapter_project);
    }

    private void Back() {
        img_back.setOnClickListener(view -> {
            finish();
        });
    }

    private void initView() {
        layout_null = findViewById(R.id.layout_null);
        img_back = findViewById(R.id.img_back);
        recyclerview = findViewById(R.id.recyclerview);
    }

}