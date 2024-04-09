package com.mkm.aiphoto_admobmediation.Adapter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.mkm.aiphoto_admobmediation.Activity.MKMCollageActivity;
import com.mkm.aiphoto_admobmediation.MKM;
import com.mkm.aiphoto_admobmediation.Model.Model_Sticker;
import com.mkm.aiphoto_admobmediation.R;
import com.mkm.aiphoto_admobmediation.Sticker.StoreSticker;

import java.io.File;
import java.util.List;

public class StickersTabAdapter extends RecyclerTabLayout.Adapter<StickersTabAdapter.ViewHolder> {
    private Context context;
    private PagerAdapter mAdapater = this.mViewPager.getAdapter();
    private int number_install;
    private File dir;

    private List<Model_Sticker> list;

    public StickersTabAdapter(ViewPager viewPager, Context context, List<Model_Sticker> list) {
        super(viewPager);
        this.context = context;
        this.list = list;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tab_sticker, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        if (list.get(i).isDefault()) {
            Glide.with(context).load(this.context.getDrawable(Integer.parseInt(list.get(i).getPath()))).into(viewHolder.imageView);
        }else {
            Glide.with(context).load(list.get(i).getPath()).into(viewHolder.imageView);
        }

//        switch (i) {
//            case 0:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.bubble));
//                break;
//            case 1:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.rainbow));
//                break;
//            case 2:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.cartoon));
//                break;
//            case 3:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.child));
//                break;
//            case 4:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.flower));
//                break;
//            case 5:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.amoji));
//                break;
//            case 6:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.delicious));
//                break;
//            case 7:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.hand));
//                break;
//            case 8:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.popular));
//                break;
//            case 9:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.valentine));
//                break;
//            case 10:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.emoj));
//                break;
//            case 11:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.rage));
//                break;
//            case 12:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.christmas));
//                break;
//            case 13:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.unicorn));
//                break;
//            case 14:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.sticker));
//                break;
//            case 15:
//                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.stickernewyear));
//                break;
//        }
        viewHolder.imageView.setSelected(i == getCurrentIndicatorPosition());
    }

    public int getItemCount() {
        // return this.mAdapater.getCount() + number_install;
        if (list.size()>0) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.image);

            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    StickersTabAdapter.this.getViewPager().setCurrentItem(ViewHolder.this.getAdapterPosition());

                    MKM.number_tabsticker = getAdapterPosition();
                }
            });

            try {

                if (MKMCollageActivity.number_tabsticker > -1) {
                    StickersTabAdapter.this.getViewPager().setCurrentItem(MKMCollageActivity.number_tabsticker);

                    MKM.number_tabsticker = MKMCollageActivity.number_tabsticker;

                    Log.d("eefwef", "Tstk:" + MKMCollageActivity.number_tabsticker);

                    MKMCollageActivity.number_tabsticker = -1;
                }

            }catch (Exception e) {
                Log.d("ffewefw", "dddd: " + e);
            }

            dir = new File(Environment.getExternalStorageDirectory().toString()
                    + "/" + StoreSticker.PathStore);

            File[] FileList = dir.listFiles();

            number_install = 0;
            if (FileList != null) {
                for (File value : FileList) {
                    if (value.isDirectory() && value.getName().equals("newyear")) {
                        number_install++;
                        break;
                    }
                }
            }
        }
    }
}

