package com.mkm.aiphoto_admobmediation.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mkm.aiphoto_admobmediation.Activity.DraftActivity;
import com.mkm.aiphoto_admobmediation.Activity.EditorActivity;
import com.mkm.aiphoto_admobmediation.Activity.GridPickerActivity;
import com.mkm.aiphoto_admobmediation.Activity.MKMCollageActivity;
import com.mkm.aiphoto_admobmediation.Database.DataProject;
import com.mkm.aiphoto_admobmediation.Database.Model_project;
import com.mkm.aiphoto_admobmediation.Mkm.MKMPickerView;
import com.mkm.aiphoto_admobmediation.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Project extends RecyclerView.Adapter<Adapter_Project.Viewholder> {

    private Context context;
    private List<Model_project> list;
    private ArrayList<String> listString;
    private int posi;

    public Adapter_Project(Context context, List<Model_project> list) {
        this.context = context;
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Model_project> getList() {
        return list;
    }

    public void setList(List<Model_project> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.row_project, null);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Glide.with(context).load(list.get(position).getPath()).into(holder.img_project);

        holder.txt_size.setText(list.get(position).getSize());

        holder.txt_days.setText(list.get(position).getDays());

        Log.d("fewfe", "time: " + list.get(position).getDays());

        if (list.get(position).getGridView().length() > 0) {
            holder.txt_style.setText("Collage");
        }else {
            holder.txt_style.setText("Editor");
        }

        holder.img_edit.setOnClickListener(view -> {

            posi = position;
            if (list.get(position).getGridView().length() > 0) {
                ALert(list.get(position).getPath(), list.get(position).getStyle(),
                        list.get(position).getGridView(), list.get(position).getNumber_layout()
                        , list.get(position).getNumber_filter(), list.get(position).getNumber_color(),
                        list.get(position).getNumber_border(), list.get(position).getNumber_radius(),
                        list.get(position).getNumber_gradient(), list.get(position).getNumber_ratio(),
                        list.get(position).getNumber_tabsticker(), list.get(position).getNumber_sticker(),
                        list.get(position).getText());

            }else {
                ALert(list.get(position).getPath(), list.get(position).getStyle(),
                        list.get(position).getPolishView(), list.get(position).getNumber_layout(),
                        list.get(position).getNumber_filter(), list.get(position).getNumber_color(),
                        list.get(position).getNumber_border(), list.get(position).getNumber_radius(),
                        list.get(position).getNumber_gradient(), list.get(position).getNumber_ratio(),
                        list.get(position).getNumber_tabsticker(), list.get(position).getNumber_sticker(),
                        list.get(position).getText());
            }
        });

        holder.img_trash.setOnClickListener(view -> {
            AlertDelete(list.get(position).getPath());
        });
    }

    private void AlertDelete(String path) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(context.getString(R.string.you_want_to_remove));

        builder.setPositiveButton(context.getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                for (int a = 0; a < DataProject.getInstance(context).daoSql().getall().size(); a++) {
                    if (DataProject.getInstance(context).daoSql().getall().get(a).getPath().equals(path)) {
                        DataProject.getInstance(context).daoSql().delete(
                                DataProject.getInstance(context).daoSql().getall().get(a)
                        );

                        Intent intent = new Intent(context, DraftActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        break;
                    }
                }
            }
        });

        builder.setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    private void ALert(String path, String style, String pathtyle, int number, int numberfilter, int numbercolor,
                       int numberborder, int number_radius, int number_gradient, int numberratio,
                       int number_tabsticker, int number_sticker, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(context.getString(R.string.you_want_to_edit_this_project));

        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditProject(path, style, pathtyle, number, numberfilter, numbercolor,
                        numberborder, number_radius, number_gradient, numberratio,
                        number_tabsticker, number_sticker, text);
            }
        });

        builder.setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    private void EditProject(String path, String style, String pathtyle, int number, int numberfilter,
                             int numbercolor, int numberborder, int number_radius, int number_gradient,
                             int number_ratio, int number_tabsticker, int number_sticker, String text) {

        listString = new ArrayList<>();
        if (list.get(posi).getPath1() != null) {
            listString.add(list.get(posi).getPath1());
        }
        if (list.get(posi).getPath2() != null) {
            listString.add(list.get(posi).getPath2());
        }
        if (list.get(posi).getPath3() != null) {
            listString.add(list.get(posi).getPath3());
        }
        if (list.get(posi).getPath4() != null) {
            listString.add(list.get(posi).getPath4());
        }
        if (list.get(posi).getPath5() != null) {
            listString.add(list.get(posi).getPath5());
        }
        if (list.get(posi).getPath6() != null) {
            listString.add(list.get(posi).getPath6());
        }
        if (list.get(posi).getPath7() != null) {
            listString.add(list.get(posi).getPath7());
        }
        if (list.get(posi).getPath8() != null) {
            listString.add(list.get(posi).getPath8());
        }
        if (list.get(posi).getPath9() != null) {
            listString.add(list.get(posi).getPath9());
        }

        if (style.equals("editor")) {
            Intent intent = new Intent(context, EditorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(MKMPickerView.KEY_SELECTED_PHOTOS, list.get(posi).getPath());
            intent.putExtra("number_layout", number);
            intent.putExtra("number_filter", numberfilter);
            intent.putExtra("number_color", numbercolor);
            intent.putExtra("number_boder", numberborder);
            intent.putExtra("number_radius", number_radius);
            intent.putExtra("number_gradient", number_gradient);
            intent.putExtra("number_ratio", number_ratio);
            intent.putExtra("number_tabsticker", number_tabsticker);
            intent.putExtra("number_sticker", number_sticker);
            intent.putExtra("text_add", text);
            context.startActivity(intent);
            Log.d("efwfewfwe", "style path: " + path);
        }else {
            Log.d("wewfwef", "number: " + listString);
            Intent intent = new Intent(context, MKMCollageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(GridPickerActivity.KEY_STYLE, pathtyle +"");
            intent.putExtra("number_layout", number);
            intent.putExtra("number_filter", numberfilter);
            intent.putExtra("number_color", numbercolor);
            intent.putExtra("number_boder", numberborder);
            intent.putExtra("number_radius", number_radius);
            intent.putExtra("number_gradient", number_gradient);
            intent.putExtra("number_ratio", number_ratio);
            intent.putExtra("number_tabsticker", number_tabsticker);
            intent.putExtra("number_sticker", number_sticker);
            intent.putExtra("text_add", text);
            intent.putStringArrayListExtra(GridPickerActivity.KEY_DATA_RESULT, listString);
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {

        if (list.size() > 0) {
            return list.size();
        }

        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView img_project;
        private RelativeLayout layout_project;
        private ImageView img_trash;
        private TextView txt_size;
        private TextView txt_days;
        private ImageView img_edit;
        private TextView txt_style;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            img_project = itemView.findViewById(R.id.img_project);
            layout_project = itemView.findViewById(R.id.layout_project);
            img_trash = itemView.findViewById(R.id.img_trash);
            txt_days = itemView.findViewById(R.id.txt_days);
            txt_size = itemView.findViewById(R.id.txt_size);
            img_edit = itemView.findViewById(R.id.img_edit);
            txt_style = itemView.findViewById(R.id.txt_style);
        }
    }
}

