package com.mkm.aiphoto_admobmediation.Activity;

import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.multidex.BuildConfig;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mkm.aiphoto_admobmediation.Dialog.DetailsDialog;
import com.mkm.aiphoto_admobmediation.MKM;
import com.mkm.aiphoto_admobmediation.Mkm.MKMPickerView;
import com.mkm.aiphoto_admobmediation.Picker.ImageCaptureManager;
import com.mkm.aiphoto_admobmediation.Preference.Preference;
import com.mkm.aiphoto_admobmediation.R;
import com.mkm.aiphoto_admobmediation.Sticker.StoreSticker;
import com.mkm.aiphoto_admobmediation.Ultils.AdsUtils1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class HomeActivity extends BaseActivity {

    private ViewFlipper viewflipper;

    private ImageCaptureManager imageCaptureManager;
    private ImageView imageViewStar;
    private ImageView img_indiviewfliper1;
    private ImageView img_indiviewfliper2;
    private ImageView img_indiviewfliper3;
    private ImageView img_indiviewfliper4;

    private RelativeLayout relative_layout_edit_camera;
    private RelativeLayout relative_layout_edit_collage;
    private RelativeLayout relative_layout_edit_photo;
    private RelativeLayout layout_draft;
    public static Context contextApp;
    private boolean installp;
    private ProgressDialog pDialog;
    private String nameStore;
    private ImageView img_menu;

    private boolean ispermission = false;
    private boolean askPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_home);

        setStatusbar();
        setViewFliper();
        initView();
        ClickCamera();
        ClickEdit();
        ClickCollage();
        Draft();
        ClickMenu();
    }
    private void requestPermission() {

        Log.d("MKM","requestPermission");

//        Bundle bundle = new Bundle();
//        mFirebaseAnalytics.logEvent("requestPermission", bundle);

        if (SDK_INT >= 30) {

            Dialog dialog = new Dialog(this);

            if (!Environment.isExternalStorageManager()) {

                if( askPermission == true){
                    return ;
                }

                askPermission = true;

                Log.d("MKM","requestPermission show popup");

                dialog.setContentView(R.layout.dialog_permission);
                dialog.setCanceledOnTouchOutside(false);

                Window window = dialog.getWindow();
                if (window == null) {
                    Log.d("MKM","requestPermission window null");
                    return;
                }

                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    dialog.getWindow().setLayout(-1, -2);
                }

                Button bt_ok = dialog.findViewById(R.id.txt_grant);
                bt_ok.setOnClickListener(v -> {

                    try {
                        Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
                        startActivity(intent);
                        dialog.dismiss();
                    } catch (Exception ex) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        startActivity(intent);
                        dialog.dismiss();
                    }

                    askPermission = false;

                    dialog.dismiss();
                });

                dialog.show();

            } else {

//                if (dialog != null && dialog.isShowing())
                dialog.dismiss();

                Log.d("MKM","requestPermission isPermission true");

                ispermission = true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(HomeActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(HomeActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            222);
                }
            }
        }
    }

    private void verifyStoragePermissions(Activity activity) {
        if (SDK_INT < 30) {
            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        423
                );
            } else {
                ispermission = true;
            }
        }
    }

    public boolean checkPermonRission() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.MANAGE_EXTERNAL_STORAGE") == 0;
    }

    private void ClickMenu() {
        img_menu.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingActivity.class));
        });
    }

    private void setStatusbar() {

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private void Draft() {

        layout_draft.setOnClickListener(view -> {
            startActivity(new Intent(this, DraftActivity.class));
        });

    }

    private void ClickCollage() {
        relative_layout_edit_collage.setOnClickListener(v -> {
            goToCollage();
        });
    }

    private void goToCollage() {

        String[] arrPermissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
        if (Build.VERSION.SDK_INT >= 29 && SDK_INT < 33)arrPermissions=new String[]{"android.permission.READ_EXTERNAL_STORAGE"};
        if (Build.VERSION.SDK_INT == 33)arrPermissions=new String[]{"android.permission.READ_MEDIA_IMAGES"};

        Dexter.withContext(HomeActivity.this).withPermissions(arrPermissions).withListener(new MultiplePermissionsListener() {
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    Intent intent = new Intent(HomeActivity.this, GridPickerActivity.class);
                    intent.putExtra(GridPickerActivity.KEY_LIMIT_MAX_IMAGE, 9);
                    intent.putExtra(GridPickerActivity.KEY_LIMIT_MIN_IMAGE, 2);
                    startActivityForResult(intent, 1001);
                }
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    DetailsDialog.showDetailsDialog(HomeActivity.this);
                }
            }

            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(dexterError -> Toast.makeText(HomeActivity.this, getString(R.string.error_occurred) +"", Toast.LENGTH_SHORT).show()).onSameThread().check();
    }

    private void ClickEdit() {
        relative_layout_edit_photo.setOnClickListener(v -> {
            goToEditPhoto();
        });
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            super.onActivityResult(i, i2, intent);
        } else if (i == 1) {
            if (this.imageCaptureManager == null) {
                this.imageCaptureManager = new ImageCaptureManager(this);
            }
            new Handler(Looper.getMainLooper()).post(() -> HomeActivity.this.imageCaptureManager.galleryAddPic());
            Intent intent2 = new Intent(getApplicationContext(), EditorActivity.class);
            intent2.putExtra(MKMPickerView.KEY_SELECTED_PHOTOS, this.imageCaptureManager.getCurrentPhotoPath());
            startActivity(intent2);
        }
    }

    private void goToEditPhoto() {

        String[] arrPermissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
        if (Build.VERSION.SDK_INT >= 29 && SDK_INT < 33)arrPermissions=new String[]{"android.permission.READ_EXTERNAL_STORAGE"};
        if (Build.VERSION.SDK_INT == 33)arrPermissions=new String[]{"android.permission.READ_MEDIA_IMAGES"};
        Dexter.withContext(HomeActivity.this).withPermissions(arrPermissions).withListener(new MultiplePermissionsListener() {
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    MKMPickerView.builder().setPhotoCount(1).setPreviewEnabled(false).setShowCamera(false).start(HomeActivity.this);
                    Toast.makeText(HomeActivity.this, "is permission", Toast.LENGTH_SHORT).show();
                }
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    DetailsDialog.showDetailsDialog(HomeActivity.this);
                    Toast.makeText(HomeActivity.this, "no permission", Toast.LENGTH_SHORT).show();
                }
            }

            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(dexterError -> Toast.makeText(HomeActivity.this, getString(R.string.error_occurred) +"", Toast.LENGTH_SHORT).show()).onSameThread().check();

        //  }
    }

    private void ClickCamera() {
        relative_layout_edit_camera.setOnClickListener(v -> {

            String[] arrPermissions = {"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            if (Build.VERSION.SDK_INT >= 29)arrPermissions=new String[]{"android.permission.CAMERA"};

            Dexter.withContext(HomeActivity.this).withPermissions(arrPermissions).withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        takePhotoFromCamera();
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        DetailsDialog.showDetailsDialog(HomeActivity.this);
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(dexterError -> Toast.makeText(HomeActivity.this, getString(R.string.error_occurred) +"", Toast.LENGTH_SHORT).show()).onSameThread().check();
        });
    }

    public void takePhotoFromCamera() {
        try {
            startActivityForResult(this.imageCaptureManager.dispatchTakePictureIntent(), 1);
        } catch (IOException | ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void initView() {

        relative_layout_edit_camera = findViewById(R.id.relative_layout_edit_camera);
        relative_layout_edit_collage = findViewById(R.id.relative_layout_edit_collage);
        relative_layout_edit_photo = findViewById(R.id.relative_layout_edit_photo);
        layout_draft = findViewById(R.id.layout_draft);
        img_menu = findViewById(R.id.img_menu);
        imageCaptureManager = new ImageCaptureManager(this);
    }

    private void setViewFliper() {
        viewflipper = findViewById(R.id.viewflipper);
        imageViewStar = findViewById(R.id.imageViewStar);
        img_indiviewfliper1 = findViewById(R.id.img_indiviewfliper1);
        img_indiviewfliper2 = findViewById(R.id.img_indiviewfliper2);
        img_indiviewfliper3 = findViewById(R.id.img_indiviewfliper3);
        img_indiviewfliper4 = findViewById(R.id.img_indiviewfliper4);

        ArrayList<Drawable> arrayList = new ArrayList<Drawable>();

        arrayList.add(getDrawable(R.drawable.img_viewfliper1));
        arrayList.add(getDrawable(R.drawable.img_viewfliper2));
        arrayList.add(getDrawable(R.drawable.img_viewfliper3));
        arrayList.add(getDrawable(R.drawable.img_viewfliper4));

        for (int i = 0; i < arrayList.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(arrayList.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewflipper.addView(imageView);
        }

        Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.viewfliper_in);
        Animation out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.viewfliper_out);

        viewflipper.setInAnimation(in);
        viewflipper.setOutAnimation(out);

        viewflipper.startFlipping();
        viewflipper.setAutoStart(true);
        viewflipper.setFlipInterval(4000);

        viewflipper.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (viewflipper.getDisplayedChild() == 0) {
                    img_indiviewfliper1.setImageDrawable(getDrawable(R.drawable.ic_sl_indiviewfliper));
                    img_indiviewfliper2.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                    img_indiviewfliper3.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                    img_indiviewfliper4.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                }else if (viewflipper.getDisplayedChild() == 1) {
                    img_indiviewfliper2.setImageDrawable(getDrawable(R.drawable.ic_sl_indiviewfliper));
                    img_indiviewfliper1.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                    img_indiviewfliper3.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                    img_indiviewfliper4.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                }else if (viewflipper.getDisplayedChild() == 2) {
                    img_indiviewfliper3.setImageDrawable(getDrawable(R.drawable.ic_sl_indiviewfliper));
                    img_indiviewfliper2.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                    img_indiviewfliper1.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                    img_indiviewfliper4.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                }else if (viewflipper.getDisplayedChild() == 3){
                    img_indiviewfliper4.setImageDrawable(getDrawable(R.drawable.ic_sl_indiviewfliper));
                    img_indiviewfliper2.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                    img_indiviewfliper3.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                    img_indiviewfliper1.setImageDrawable(getDrawable(R.drawable.bgr_indiviewfliper));
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imageViewStar.setOnClickListener(view -> {

        });
    }

    public void onResume() {
        super.onResume();

        try {
            if (installp) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ewfewf", "\n L: " + MKM.linkp + " \n n: " + MKM.linkp);
                        InstallPack();
                        installp = false;
                    }
                },500);
            }
        }catch (Exception e) {

        }

        if (AdsUtils1.isNetworkAvailabel(getApplicationContext())) {
            if (!Preference.isRated(getApplicationContext()) && Preference.getCounter(getApplicationContext()) % 6 == 0) {
//                new RateDialog(this, false).show();
            }
            Preference.increateCounter(getApplicationContext());
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    public static Context getAppContext() {
        return contextApp;
    }

    private void InstallPack() {
        String folder_vault = StoreSticker.PathStore;
        File f = new File(Environment.getExternalStorageDirectory(), folder_vault);
        if (!f.exists()) {
            f.mkdirs();
            DownloadSticker(MKM.linkp, MKM.namep);
        }else {
            DownloadSticker(MKM.linkp, MKM.namep);
        }
    }
    private void DownloadSticker(String link, String name) {
        pDialog = new ProgressDialog(this);
        nameStore = name;

        new DownloadFileFromURL().execute(link);

    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage(getString(R.string.pleasewait_download));
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                int lenghtOfFile = connection.getContentLength();

                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString()
                        + "/" + StoreSticker.PathStore + "/" + nameStore + ".zip");

                Log.d("ewfwf", "path: " + output);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;

                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                output.flush();

                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();

            try {
                unzip(new File(Environment
                        .getExternalStorageDirectory().toString()
                        + "/" + StoreSticker.PathStore + "/" + nameStore +".zip"), new File(
                        Environment.getExternalStorageDirectory().toString()+"/"
                                + StoreSticker.PathStore+"/"
                ));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(HomeActivity.this, getString(R.string.download_complete), Toast.LENGTH_SHORT).show();
        }

    }

    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            }
        } finally {
            zis.close();
        }
    }
    public void onPostCreate(@Nullable Bundle bundle) {
        super.onPostCreate(bundle);
    }
}