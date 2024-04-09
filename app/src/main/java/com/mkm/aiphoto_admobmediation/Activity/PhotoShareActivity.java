package com.mkm.aiphoto_admobmediation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mkm.aiphoto_admobmediation.Constants.Constants;
import com.mkm.aiphoto_admobmediation.Database.DataProject;
import com.mkm.aiphoto_admobmediation.Database.Model_project;
import com.mkm.aiphoto_admobmediation.MKM;
import com.mkm.aiphoto_admobmediation.Picker.ImageCaptureManager;
import com.mkm.aiphoto_admobmediation.Preference.Preference;
import com.mkm.aiphoto_admobmediation.R;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class PhotoShareActivity extends BaseActivity implements View.OnClickListener {
    private File file;
    ImageCaptureManager createImageFile1;
    private int number_layout;
    private int number_filter;
    private int number_color;
    private int number_boder;
    private int number_radius;
    private int number_dradient;
    private int number_ratio;
    private int number_tabsticker;
    private int number_sticker;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView((int) R.layout.activity_photo_share);
        createImageFile1=new ImageCaptureManager(this);

        String string = getIntent().getExtras().getString("path");
        this.file = new File(string);

        Glide.with(getApplicationContext()).load(this.file).into((ImageView) findViewById(R.id.image_view_preview));
        findViewById(R.id.image_view_preview).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                PhotoShareActivity.this.onClick(view);
            }
        });

        findViewById(R.id.imageViewBack).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                onBackPressed();
            }
        });

        findViewById(R.id.imageViewHome).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public final void onClick(View view) {


                Intent intent = new Intent(PhotoShareActivity.this, HomeActivity.class);
                intent.setFlags(67108864);
                startActivity(intent);
                finish();
            }
        });
        if (!Preference.isRated(this)) {
//            new RateDialog(this, false).show();
        }

        Save_Project();
    }

    private void Save_Project() {
        RelativeLayout Save = findViewById(R.id.layout_save);

        Save.setOnClickListener(view -> {
            SaveProject();
        });
    }

    private void SaveProject() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.do_you_want_to_save_the_project));
        builder.setCancelable(false);

        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String style ="";
                String path = "";

                if (getIntent()!=null) {
                    style = getIntent().getStringExtra("intent");
                    path = getIntent().getStringExtra("path");
                }

                if (style.equals("collage")) {

                    Log.d("efwwef", "polish: " + MKMCollageActivity.PlGridview);

                    File file = new File(path);
                    Date lastModDate = new java.sql.Date(file.lastModified());
                    Bitmap bt = BitmapFactory.decodeFile(path);

                    Calendar calendar = Calendar.getInstance();
                    int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutes = calendar.get(Calendar.MINUTE);


//                    if (PolishHomeActivity.number_layout > 0) {
//                        number_layout = PolishHomeActivity.number_layout;
//                    }

                    number_layout = MKM.number_layout;
                    number_filter = MKM.number_filter;
                    number_color = MKM.number_color;
                    number_boder = MKM.number_border;
                    number_radius = MKM.number_radius;
                    number_dradient = MKM.number_gradient;
                    number_ratio = MKM.number_ratio;
                    number_tabsticker = MKM.number_tabsticker;
                    number_sticker = MKM.number_sticker;
                    text = MKM.text;

                    MKM.number_ratio = 0;
                    MKM.number_color = 0;
                    MKM.number_gradient = 0;
                    MKM.number_layout = 0;
                    MKM.number_filter = 0;
                    MKM.number_border = 0;
                    MKM.number_radius = 0;
                    MKM.number_tabsticker = -1;
                    MKM.number_sticker = -1;
                    MKM.text = "";

                    DataProject.getInstance(getApplicationContext()).daoSql().insert(new Model_project(
                            path, "collage", lastModDate.toString() +" : " + hour24hrs + "h"
                            + minutes +"m", bt.getWidth() + "x" + bt.getHeight(), null, MKMCollageActivity.PlGridview.toString() +"",
                            MKMCollageActivity.path1, MKMCollageActivity.path2, MKMCollageActivity.path3,
                            MKMCollageActivity.path4, MKMCollageActivity.path5, MKMCollageActivity.path6,
                            MKMCollageActivity.path7, MKMCollageActivity.path8, MKMCollageActivity.path9,
                            text, number_layout, number_filter, number_color, number_boder, number_radius, number_dradient,
                            number_ratio, number_tabsticker, number_sticker
                    ));
                }else {
                    File file = new File(path);
                    Date lastModDate = new java.sql.Date(file.lastModified());
                    Bitmap bt = BitmapFactory.decodeFile(path);

                    Calendar calendar = Calendar.getInstance();
                    int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutes = calendar.get(Calendar.MINUTE);

                    DataProject.getInstance(getApplicationContext()).daoSql().insert(new Model_project(
                            path+"", "editor", lastModDate.toString() +" : " + hour24hrs + "h"
                            + minutes +"m", bt.getWidth() + "x" + bt.getHeight(), EditorActivity.Polistview.toString() +"", ""
                            ,null, null, null, null, null, null, null, null, null,
                            null,0, 0, 0, 0, 0, 0,
                            0, 0, 0
                    ));
                }
                Toast.makeText(PhotoShareActivity.this, getString(R.string.save_project_finish), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PhotoShareActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.show();
    }

    public void onDestroy() {
        super.onDestroy();
    }


    @SuppressLint("WrongConstant")
    public void startAcitivity(PhotoShareActivity saveAndShareActivity, View view) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(saveAndShareActivity.getApplicationContext(), getResources().getString(R.string.file_provider), saveAndShareActivity.file));
        intent.addFlags(3);
        saveAndShareActivity.startActivity(Intent.createChooser(intent, "Share"));
    }

    public void onResume() {
        super.onResume();
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        if (view != null) {
            int id = view.getId();
            if (id != R.id.image_view_preview) {
                switch (id) {
                    case R.id.linearLayoutShareOne:
//                        startAcitivity(PhotoShareActivity.this, view);
                        return;
                    case R.id.linear_layout_facebook:
//                        sharePhoto(Constants.FACEBOOK);
                        sharephotosocialplateforms(Uri.parse(file.getAbsolutePath().toString()), Constants.FACEBOOK);
                        return;
                    case R.id.linear_layout_instagram:
//                        sharePhoto(Constants.INSTAGRAM);
                        sharephotosocialplateforms(Uri.parse(file.getAbsolutePath().toString()), Constants.INSTAGRAM);
                        return;
                    case R.id.linear_layout_messenger:
//                        sharePhoto(Constants.MESSEGER);
                        sharephotosocialplateforms(Uri.parse(file.getAbsolutePath().toString()),Constants.MESSEGER);
                        return;
                    case R.id.linear_layout_share_more:
                        Uri createCacheFile = createCacheFile();
                        if (createCacheFile != null) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.SEND");
                            intent.addFlags(1);
                            intent.setDataAndType(createCacheFile, getContentResolver().getType(createCacheFile));
                            intent.putExtra("android.intent.extra.STREAM", createCacheFile);
                            startActivity(Intent.createChooser(intent, "Choose an app"));
                            return;
                        }
                        Toast.makeText(this, getString(R.string.fail_to_sharing) +"", 0).show();
                        return;
                    default:
                        switch (id) {
                            case R.id.linear_layout_twitter:
//                                sharePhoto(Constants.TWITTER);
                                sharephotosocialplateforms(Uri.parse(file.getAbsolutePath().toString()),Constants.TWITTER);
                                return;
                            case R.id.linear_layout_whatsapp:
//                                sharePhoto(Constants.WHATSAPP);
                                sharephotosocialplateforms(Uri.parse(file.getAbsolutePath().toString()),Constants.WHATSAPP);
                                return;
                            default:
                                return;
                        }
                }
            } else {
                Intent intent4 = new Intent();
                intent4.setAction("android.intent.action.VIEW");
                intent4.setDataAndType(FileProvider.getUriForFile(getApplicationContext(), getResources().getString(R.string.file_provider), this.file), "image/*");
                intent4.addFlags(3);
                startActivity(intent4);
            }
        }
    }

    public void sharephotosocialplateforms(Uri contentUri,String packagestr){

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // temp permission for receiving app to read this file
        shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        shareIntent.setType("image/*");
        shareIntent.setPackage(packagestr);
        if(packagestr.equals("com.instagram.android")){
            shareIntent.setType("image/jpeg");

        }
        startActivity(shareIntent);
    }

    @SuppressLint("WrongConstant")
    public void sharePhoto(String str) {
        if (isPackageInstalled(this, str)) {
            Uri createCacheFile = createCacheFile();
            if (createCacheFile != null) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.addFlags(1);
                intent.setDataAndType(createCacheFile, getContentResolver().getType(createCacheFile));
                intent.putExtra("android.intent.extra.STREAM", createCacheFile);
                intent.setPackage(str);
                startActivity(intent);
                return;
            }
            Toast.makeText(this, getString(R.string.fail_to_sharing) +"", 0).show();
            return;
        }
        Toast.makeText(this, getString(R.string.cant_find_this_app) +"", 0).show();
        Intent intent2 = new Intent("android.intent.action.VIEW");
        intent2.setData(Uri.parse("market://details?id=" + str));
        startActivity(intent2);
    }

    @SuppressLint("WrongConstant")
    public static boolean isPackageInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 128);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    private Uri createCacheFile() {
//        return FileProvider.getUriForFile(getApplicationContext(), getResources().getString(R.string.file_provider), this.file);
        File createImageFile = null;
        try {
            createImageFile = createImageFile1.createImageFile();
            return Uri.fromFile(new File(createImageFile.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
