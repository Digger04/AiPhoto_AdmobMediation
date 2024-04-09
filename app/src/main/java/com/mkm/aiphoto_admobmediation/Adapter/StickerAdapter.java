package com.mkm.aiphoto_admobmediation.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mkm.aiphoto_admobmediation.Activity.MKMCollageActivity;
import com.mkm.aiphoto_admobmediation.Assets.StickerFileAsset;
import com.mkm.aiphoto_admobmediation.MKM;
import com.mkm.aiphoto_admobmediation.R;

import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

    public Context context;
    public int screenWidth;
    public OnClickSplashListener onClickSplashListener;

    public List<String> stringList;

    public interface OnClickSplashListener {
        void addSticker(int i, Bitmap bitmap);
    }

    public StickerAdapter(Context context2, List<String> list, int i, OnClickSplashListener onClickSplashListener) {
        this.context = context2;
        this.stringList = list;
        this.screenWidth = i;
        this.onClickSplashListener = onClickSplashListener;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_sticker, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Bitmap bitmap = StickerFileAsset.loadBitmapFromAssets(this.context, this.stringList.get(i));
        if (bitmap != null) {
            Glide.with(context).load(bitmap).into(viewHolder.sticker);
        }else {
            Bitmap bm = BitmapFactory.decodeFile(stringList.get(i));
            Glide.with(context).load(bm).into(viewHolder.sticker);
        }
    }

    public int getItemCount() {
        return this.stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView sticker;
        private View lockPro;

        public ViewHolder(View view) {
            super(view);

            this.sticker = view.findViewById(R.id.image_view_item_sticker);
            view.setOnClickListener(this);

            try {
                if (MKMCollageActivity.number_sticker > -1) {

                    Bitmap bitmap = null;

                    try {
                        bitmap = BitmapFactory.decodeFile(StickerAdapter.this.stringList.get(MKMCollageActivity.number_sticker));
                    }catch (Exception e) {
                        StickerAdapter.this.onClickSplashListener.addSticker(MKMCollageActivity.number_sticker,StickerFileAsset.loadBitmapFromAssets(StickerAdapter.this.context, (String) StickerAdapter.this.stringList.get(MKMCollageActivity.number_sticker)));
                    }

                    if (bitmap == null) {
                        StickerAdapter.this.onClickSplashListener.addSticker(MKMCollageActivity.number_sticker,StickerFileAsset.loadBitmapFromAssets(StickerAdapter.this.context, (String) StickerAdapter.this.stringList.get(MKMCollageActivity.number_sticker)));
                    }else {
                        StickerAdapter.this.onClickSplashListener.addSticker(getAdapterPosition(),bitmap);
                    }

                    Log.d("eefwef", "stk:" + MKMCollageActivity.number_sticker);

                    Log.d("eefwef", "size:" + stringList.size());

                    MKMCollageActivity.number_sticker = -1;
                }
            }catch (Exception e) {

            }
        }

        public void onClick(View view) {
            Bitmap bitmap = BitmapFactory.decodeFile(StickerAdapter.this.stringList.get(getAdapterPosition()));
            if (bitmap == null) {
                StickerAdapter.this.onClickSplashListener.addSticker(getAdapterPosition(),StickerFileAsset.loadBitmapFromAssets(StickerAdapter.this.context, (String) StickerAdapter.this.stringList.get(getAdapterPosition())));
            }else {
                StickerAdapter.this.onClickSplashListener.addSticker(getAdapterPosition(),bitmap);
            }

            MKM.number_sticker = getAdapterPosition();
        }
    }
}
