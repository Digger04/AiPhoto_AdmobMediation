package com.mkm.aiphoto_admobmediation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mkm.aiphoto_admobmediation.Model.model_share;
import com.mkm.aiphoto_admobmediation.R;

import java.util.List;

public class Adapter_share extends RecyclerView.Adapter<Adapter_share.Viewholder> {

    private List<model_share> list;
    private Context context;

    public Adapter_share(List<model_share> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public List<model_share> getList() {
        return list;
    }

    public void setList(List<model_share> list) {
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_share, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.img_share.setImageDrawable(list.get(position).getImage());
        holder.txt_name.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView img_share;
        private TextView txt_name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            img_share = itemView.findViewById(R.id.img_share);
            txt_name = itemView.findViewById(R.id.txt_name);
        }
    }
}
