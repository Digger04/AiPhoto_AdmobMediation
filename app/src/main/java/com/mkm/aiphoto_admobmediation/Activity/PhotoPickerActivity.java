package com.mkm.aiphoto_admobmediation.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mkm.aiphoto_admobmediation.Entity.Photo;
import com.mkm.aiphoto_admobmediation.Event.OnItemCheckListener;
import com.mkm.aiphoto_admobmediation.Fragment.ImagePagerFragment;
import com.mkm.aiphoto_admobmediation.Fragment.PhotoPickerFragment;
import com.mkm.aiphoto_admobmediation.Mkm.MKMPickerView;
import com.mkm.aiphoto_admobmediation.R;

import java.util.ArrayList;

public class PhotoPickerActivity extends AppCompatActivity {
    private boolean forwardMain;
    private ImagePagerFragment imagePagerFragment;
    private int maxCount = 9;
    private ArrayList<String> originalPhotos = null;
    private PhotoPickerFragment pickerFragment;
    private boolean showGif = false;
    public PhotoPickerActivity getActivity() {
        return this;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        boolean booleanExtra = getIntent().getBooleanExtra(MKMPickerView.EXTRA_SHOW_CAMERA, true);
        boolean booleanExtra2 = getIntent().getBooleanExtra(MKMPickerView.EXTRA_SHOW_GIF, false);
        boolean booleanExtra3 = getIntent().getBooleanExtra(MKMPickerView.EXTRA_PREVIEW_ENABLED, true);
        this.forwardMain = getIntent().getBooleanExtra(MKMPickerView.MAIN_ACTIVITY, false);
        setShowGif(booleanExtra2);

        setContentView(R.layout.activity_photo_picker);

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("");
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            supportActionBar.setElevation(25.0f);
        }


        this.maxCount = getIntent().getIntExtra(MKMPickerView.EXTRA_MAX_COUNT, 9);
        int intExtra = getIntent().getIntExtra(MKMPickerView.EXTRA_GRID_COLUMN, 4);
        this.originalPhotos = getIntent().getStringArrayListExtra(MKMPickerView.EXTRA_ORIGINAL_PHOTOS);
        this.pickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentByTag("tag");
        if (this.pickerFragment == null) {
            this.pickerFragment = PhotoPickerFragment.newInstance(booleanExtra, booleanExtra2, booleanExtra3, intExtra, this.maxCount, this.originalPhotos);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, this.pickerFragment, "tag").commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        this.pickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new OnItemCheckListener() {
            public final boolean onItemCheck(int i, Photo photo, int i2) {
                if (!forwardMain) {
                    Intent intent = new Intent(PhotoPickerActivity.this, EditorActivity.class);
                    intent.putExtra(MKMPickerView.KEY_SELECTED_PHOTOS, photo.getPath());
                    startActivity(intent);
                    finish();
                    return true;
                }
                MKMCollageActivity.getQueShotGridActivityInstance().replaceCurrentPiece(photo.getPath());
                finish();
                return true;
            }
        });
    }

    public void onBackPressed() {
        if (this.imagePagerFragment == null || !this.imagePagerFragment.isVisible()) {
            super.onBackPressed();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setShowGif(boolean z) {
        this.showGif = z;
    }
}
