package com.mkm.aiphoto_admobmediation.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.mkm.aiphoto_admobmediation.Assets.FilterFileAsset;
import com.mkm.aiphoto_admobmediation.Listener.FilterListener;
import com.mkm.aiphoto_admobmediation.MKM;
import com.mkm.aiphoto_admobmediation.R;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
    private List<Bitmap> bitmaps;
    private Context context;
    public static List<FilterFileAsset.FiltersCode> filterBeanList;
    public static FilterListener filterListener;
    public static int selectedIndex = 0;

    public FilterAdapter(List<Bitmap> bitmapList, FilterListener filterListener, Context mContext, List<FilterFileAsset.FiltersCode> mfilterBeanList) {
        this.filterListener = filterListener;
        this.bitmaps = bitmapList;
        this.context = mContext;
        this.filterBeanList = mfilterBeanList;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_filter, viewGroup, false));
    }

    public void reset() {
        this.selectedIndex = 0;
        notifyDataSetChanged();
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.text_view_filter_name.setText(this.filterBeanList.get(i).getName());
        viewHolder.text_view_filter_name.setTextColor(ContextCompat.getColor(context, R.color.white));
        viewHolder.round_image_view_filter_item.setImageBitmap(this.bitmaps.get(i));
        if (this.selectedIndex == i) {
            viewHolder.text_view_filter_name.setTextColor(ContextCompat.getColor(context, R.color.white));
            viewHolder.text_view_filter_name.setBackgroundColor(ContextCompat.getColor(context, R.color.mainColor));
            viewHolder.viewSelected.setVisibility(View.VISIBLE);
            return;
        }
        viewHolder.text_view_filter_name.setTextColor(ContextCompat.getColor(context, R.color.white));
        viewHolder.text_view_filter_name.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
        viewHolder.viewSelected.setVisibility(View.GONE);

    }

    public int getItemCount() {
        return this.bitmaps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView round_image_view_filter_item;
        TextView text_view_filter_name;
        View viewSelected, lockPro;;

        ViewHolder(View view) {
            super(view);
            this.round_image_view_filter_item = view.findViewById(R.id.round_image_view_filter_item);
            this.text_view_filter_name = view.findViewById(R.id.text_view_filter_name);
            this.viewSelected = view.findViewById(R.id.view_selected);

            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    FilterAdapter.this.selectedIndex = ViewHolder.this.getLayoutPosition();
                    FilterAdapter.this.filterListener.onFilterSelected(selectedIndex,((FilterFileAsset.FiltersCode) FilterAdapter.this.filterBeanList.get(FilterAdapter.this.selectedIndex)).getCode());
                    MKM.number_filter = FilterAdapter.this.selectedIndex;
                    FilterAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }
}
