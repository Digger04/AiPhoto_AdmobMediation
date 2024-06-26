package com.mkm.aiphoto_admobmediation.Adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mkm.aiphoto_admobmediation.Layer.Slant.NumberSlantLayout;
import com.mkm.aiphoto_admobmediation.Layer.Straight.NumberStraightLayout;
import com.mkm.aiphoto_admobmediation.MKM;
import com.mkm.aiphoto_admobmediation.Mkm.Grid.PolishLayout;
import com.mkm.aiphoto_admobmediation.Mkm.MKMSquareView;
import com.mkm.aiphoto_admobmediation.R;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
    private List<Bitmap> bitmapData = new ArrayList();
    private List<PolishLayout> layoutData = new ArrayList();


    public OnItemClickListener onItemClickListener;

    public int selectedIndex = 0;

    public interface OnItemClickListener {
        void onItemClick(PolishLayout puzzleLayout, int i);
    }

    public GridViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new GridViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collage, viewGroup, false));
    }

    public void setSelectedIndex(int i) {
        this.selectedIndex = i;
    }

    public void onBindViewHolder(GridViewHolder collageViewHolder, final int i) {

        try {
            final PolishLayout collageLayout = this.layoutData.get(i);
            collageViewHolder.square_collage_view.setNeedDrawLine(true);
            collageViewHolder.square_collage_view.setNeedDrawOuterLine(true);
            collageViewHolder.square_collage_view.setTouchEnable(false);
            collageViewHolder.square_collage_view.setLineSize(6);
            collageViewHolder.square_collage_view.setQueShotLayout(collageLayout);
            if (this.selectedIndex == i) {
                collageViewHolder.square_collage_view.setBackgroundColor(Color.parseColor("#0078FF"));
            } else {
                collageViewHolder.square_collage_view.setBackgroundColor(0);
            }
            collageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (GridAdapter.this.onItemClickListener != null) {
                        int i = 0;
                        if (collageLayout instanceof NumberSlantLayout) {
                            i = ((NumberSlantLayout) collageLayout).getTheme();
                        } else if (collageLayout instanceof NumberStraightLayout) {
                            i = ((NumberStraightLayout) collageLayout).getTheme();
                        }
                        GridAdapter.this.onItemClickListener.onItemClick(collageLayout, i);
                    }
                    Log.d("fewfwe", "ddddd: " + i);
                    MKM.number_layout = i;
                    Log.d("fefewf", "id: " + MKM.number_layout);
                    GridAdapter.this.selectedIndex = i;
                    GridAdapter.this.notifyDataSetChanged();

                }
            });
            if (this.bitmapData != null) {
                int size = this.bitmapData.size();
                if (collageLayout.getAreaCount() > size) {
                    for (int i2 = 0; i2 < collageLayout.getAreaCount(); i2++) {
                        collageViewHolder.square_collage_view.addQuShotCollage(this.bitmapData.get(i2 % size));
                    }
                    return;
                }
                collageViewHolder.square_collage_view.addPieces(this.bitmapData);
            }
        }catch (Exception e) {
            Log.d("fewefw", "error:" + e);
        }

    }

    public int getItemCount() {
        if (this.layoutData == null) {
            return 0;
        }
        return this.layoutData.size();
    }

    public void refreshData(List<PolishLayout> list, List<Bitmap> list2) {
        this.layoutData = list;
        this.bitmapData = list2;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        MKMSquareView square_collage_view;

        public GridViewHolder(View view) {
            super(view);
            this.square_collage_view = view.findViewById(R.id.squareCollageView);
        }
    }
}

