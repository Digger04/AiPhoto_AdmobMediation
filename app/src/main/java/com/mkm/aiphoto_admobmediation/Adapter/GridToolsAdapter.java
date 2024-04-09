package com.mkm.aiphoto_admobmediation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mkm.aiphoto_admobmediation.Module.Module;
import com.mkm.aiphoto_admobmediation.R;

import java.util.ArrayList;
import java.util.List;
import static com.mkm.aiphoto_admobmediation.MKM.context;
public class GridToolsAdapter extends RecyclerView.Adapter<GridToolsAdapter.ViewHolder> {

    public OnItemSelected mOnItemSelected;

    public List<ToolModel> mToolList = new ArrayList<>();

    public interface OnItemSelected {
        void onToolSelected(Module toolType);
    }

    public GridToolsAdapter(OnItemSelected onItemSelected, boolean z) {
        this.mOnItemSelected = onItemSelected;
        this.mToolList.add(new ToolModel(context.getString(R.string.layout) +"", R.drawable.ic_collage, Module.LAYER));
        this.mToolList.add(new ToolModel(context.getString(R.string.ratio) +"", R.drawable.ic_ratio, Module.RATIO));
        this.mToolList.add(new ToolModel(context.getString(R.string.border) +"", R.drawable.ic_border, Module.PADDING));
        this.mToolList.add(new ToolModel(context.getString(R.string.background) +"", R.drawable.ic_background, Module.GRADIENT));
        this.mToolList.add(new ToolModel(context.getString(R.string.filter) +"", R.drawable.ic_filter, Module.FILTER));
        this.mToolList.add(new ToolModel(context.getString(R.string.text)+ "", R.drawable.ic_text, Module.TEXT));
        this.mToolList.add(new ToolModel(context.getString(R.string.stickers) +"", R.drawable.ic_sticker, Module.STICKER));
    }

    class ToolModel {

        public int mToolIcon;

        public String mToolName;

        public Module mToolType;

        ToolModel(String str, int i, Module toolType) {
            this.mToolName = str;
            this.mToolIcon = i;
            this.mToolType = toolType;
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collage_tool, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ToolModel toolModel = this.mToolList.get(i);
        viewHolder.text_view_tool_name.setText(toolModel.mToolName);
        viewHolder.image_view_tool_icon.setImageResource(toolModel.mToolIcon);

        viewHolder.relative_layout_wrapper_tool.setOnClickListener(view1 -> {
            GridToolsAdapter.this.mOnItemSelected.onToolSelected((GridToolsAdapter.this.mToolList.get(i)).mToolType);
        });
    }

    public int getItemCount() {
        return this.mToolList.size();
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
            //        this.relative_layout_wrapper_tool.setOnClickListener(view1 -> GridToolsAdapter.this.mOnItemSelected.onToolSelected((GridToolsAdapter.this.mToolList.get(ViewHolder.this.getLayoutPosition())).mToolType));
        }
    }
}
