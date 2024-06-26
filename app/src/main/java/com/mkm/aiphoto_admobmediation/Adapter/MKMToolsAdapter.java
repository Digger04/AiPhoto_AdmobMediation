package com.mkm.aiphoto_admobmediation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mkm.aiphoto_admobmediation.MKM;
import com.mkm.aiphoto_admobmediation.Module.Module;
import com.mkm.aiphoto_admobmediation.R;

import java.util.ArrayList;
import java.util.List;

public class MKMToolsAdapter extends RecyclerView.Adapter<MKMToolsAdapter.ViewHolder> {

    public OnQuShotItemSelected onQuShotItemSelected;
    public List<ModuleModel> toolModelArrayList = new ArrayList<>();

    public interface OnQuShotItemSelected {
        void onQuShotToolSelected(Module module);
    }

    public MKMToolsAdapter(OnQuShotItemSelected onItemSelected) {
        this.onQuShotItemSelected = onItemSelected;
        this.toolModelArrayList.add(new ModuleModel(MKM.context.getString(R.string.crop) +"", R.drawable.ic_crop, Module.CROP));
        this.toolModelArrayList.add(new ModuleModel(MKM.context.getString(R.string.filter) +"", R.drawable.ic_filter, Module.FILTER));
        this.toolModelArrayList.add(new ModuleModel(MKM.context.getString(R.string.adjust) +"", R.drawable.ic_set, Module.ADJUST));
        this.toolModelArrayList.add(new ModuleModel("HSL", R.drawable.ic_hsl, Module.HSL));
        this.toolModelArrayList.add(new ModuleModel(MKM.context.getString(R.string.effect) +"", R.drawable.ic_effect, Module.EFFECT));
        this.toolModelArrayList.add(new ModuleModel(MKM.context.getString(R.string.ratio) +"", R.drawable.ic_ratio, Module.RATIO));
        this.toolModelArrayList.add(new ModuleModel(MKM.context.getString(R.string.text) +"", R.drawable.ic_text, Module.TEXT));
        this.toolModelArrayList.add(new ModuleModel(MKM.context.getString(R.string.stickers) +"", R.drawable.ic_sticker, Module.STICKER));
        this.toolModelArrayList.add(new ModuleModel(MKM.context.getString(R.string.blur) +"", R.drawable.ic_blur, Module.BLURE));
        this.toolModelArrayList.add(new ModuleModel(MKM.context.getString(R.string.draw) +"", R.drawable.ic_paint, Module.DRAW));
        this.toolModelArrayList.add(new ModuleModel(MKM.context.getString(R.string.mirror) +"", R.drawable.ic_mirror, Module.MIRROR));
        this.toolModelArrayList.add(new ModuleModel(MKM.context.getString(R.string.frame) +"", R.drawable.ic_frame, Module.BACKGROUND));
        this.toolModelArrayList.add(new ModuleModel("SQ/BG", R.drawable.ic_splash_square, Module.SQ_BG));
    }

    class ModuleModel {
        public int toolIcon;
        public String toolName;
        public Module toolType;

        ModuleModel(String str, int i, Module toolType) {
            this.toolName = str;
            this.toolIcon = i;
            this.toolType = toolType;
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_editing_tool, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ModuleModel toolModel = this.toolModelArrayList.get(i);
        viewHolder.text_view_tool_name.setText(toolModel.toolName);
        viewHolder.image_view_tool_icon.setImageResource(toolModel.toolIcon);
    }

    public int getItemCount() {
        return this.toolModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view_tool_icon;
        TextView text_view_tool_name;
        RelativeLayout relative_layout_wrapper_tool;

        ViewHolder(View view) {
            super(view);
            this.image_view_tool_icon = view.findViewById(R.id.image_view_tool_icon);
            this.text_view_tool_name = view.findViewById(R.id.text_view_tool_name);
            this.text_view_tool_name.setSelected(true);
            this.relative_layout_wrapper_tool = view.findViewById(R.id.relative_layout_wrapper_tool);
            this.relative_layout_wrapper_tool.setOnClickListener(view1 ->
                    MKMToolsAdapter.this.onQuShotItemSelected.onQuShotToolSelected((MKMToolsAdapter.this.toolModelArrayList.get(ViewHolder.this.getLayoutPosition())).toolType));
        }
    }
}

