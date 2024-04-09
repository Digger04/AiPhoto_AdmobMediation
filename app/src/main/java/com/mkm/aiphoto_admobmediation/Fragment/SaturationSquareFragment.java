package com.mkm.aiphoto_admobmediation.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mkm.aiphoto_admobmediation.Adapter.SplashSquareAdapter;
import com.mkm.aiphoto_admobmediation.Assets.StickerFileAsset;
import com.mkm.aiphoto_admobmediation.Mkm.MKMSplashSquareView;
import com.mkm.aiphoto_admobmediation.Mkm.MKMSplashSticker;
import com.mkm.aiphoto_admobmediation.R;

public class SaturationSquareFragment extends DialogFragment implements SplashSquareAdapter.SplashChangeListener {
    private static final String TAG = "SaturationSquareFragment";
    private ImageView image_view_background;
    public Bitmap bitmap;
    private Bitmap SaturationBitmap;
    private FrameLayout frame_layout;
    public boolean isSplashView;
    public RecyclerView recycler_view_splash;
    public TextView image_view_shape;
    public SplashSaturationListener splashSaturationListener;
    private MKMSplashSticker polishSplashSticker;
    public MKMSplashSquareView polishSplashView;
    private ViewGroup viewGroup;

    public interface SplashSaturationListener {
        void onSaveSplash(Bitmap bitmap);
    }

    public void setPolishSplashView(boolean z) {
        this.isSplashView = z;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static SaturationSquareFragment show(@NonNull AppCompatActivity appCompatActivity, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, SplashSaturationListener splashSaturationListener, boolean z) {
        SaturationSquareFragment splashDialog = new SaturationSquareFragment();
        splashDialog.setBitmap(bitmap2);
        splashDialog.setSaturationBitmap(bitmap4);
        splashDialog.setSplashSaturationListener(splashSaturationListener);
        splashDialog.setPolishSplashView(z);
        splashDialog.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return splashDialog;
    }

    public void setSaturationBitmap(Bitmap bitmap2) {
        this.SaturationBitmap = bitmap2;
    }


    public void setSplashSaturationListener(SplashSaturationListener splashSaturationListener) {
        this.splashSaturationListener = splashSaturationListener;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @SuppressLint("WrongConstant")
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup2, @Nullable Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.fragment_square, viewGroup2, false);
        this.viewGroup = viewGroup2;
        this.image_view_background = inflate.findViewById(R.id.imageViewBackground);
        this.polishSplashView = inflate.findViewById(R.id.splashView);
        this.frame_layout = inflate.findViewById(R.id.frame_layout);
        this.image_view_background.setImageBitmap(this.SaturationBitmap);
        this.image_view_shape = inflate.findViewById(R.id.textViewTitle);
        if (this.isSplashView) {
            this.polishSplashView.setImageBitmap(this.bitmap);
            this.image_view_shape.setText("SPLASH SQ");
        }
        this.recycler_view_splash = inflate.findViewById(R.id.recyclerViewSplashSquare);
        this.recycler_view_splash.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.recycler_view_splash.setHasFixedSize(true);
        this.recycler_view_splash.setAdapter(new SplashSquareAdapter(getContext(), this, this.isSplashView));
        if (this.isSplashView) {
            this.polishSplashSticker = new MKMSplashSticker(StickerFileAsset.loadBitmapFromAssets(getContext(), "frame/image_mask_1.webp"), StickerFileAsset.loadBitmapFromAssets(getContext(), "frame/image_frame_1.webp"));
            this.polishSplashView.addSticker(this.polishSplashSticker);
        }

        this.polishSplashView.refreshDrawableState();
        this.polishSplashView.setLayerType(2, null);
        this.image_view_shape.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaturationSquareFragment.this.polishSplashView.setcSplashMode(0);
                SaturationSquareFragment.this.recycler_view_splash.setVisibility(View.VISIBLE);
                SaturationSquareFragment.this.polishSplashView.refreshDrawableState();
                SaturationSquareFragment.this.polishSplashView.invalidate();
            }
        });
        inflate.findViewById(R.id.imageViewSaveSplash).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaturationSquareFragment.this.splashSaturationListener.onSaveSplash(SaturationSquareFragment.this.polishSplashView.getBitmap(SaturationSquareFragment.this.SaturationBitmap));
                SaturationSquareFragment.this.dismiss();
            }
        });
        inflate.findViewById(R.id.imageViewCloseSplash).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaturationSquareFragment.this.dismiss();
            }
        });
        return inflate;
    }


    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(-16777216));
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.polishSplashView.getSticker().release();
        if (this.SaturationBitmap != null) {
            this.SaturationBitmap.recycle();
        }
        this.SaturationBitmap = null;
        this.bitmap = null;
    }

    public void onSelected(MKMSplashSticker splashSticker2) {
        this.polishSplashView.addSticker(splashSticker2);
    }

}
