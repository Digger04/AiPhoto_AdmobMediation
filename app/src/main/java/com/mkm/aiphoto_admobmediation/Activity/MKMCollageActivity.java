package com.mkm.aiphoto_admobmediation.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.hold1.keyboardheightprovider.KeyboardHeightProvider;
import com.mkm.aiphoto_admobmediation.Adapter.AspectAdapter;
import com.mkm.aiphoto_admobmediation.Adapter.CollageBackgroundAdapter;
import com.mkm.aiphoto_admobmediation.Adapter.CollageColorAdapter;
import com.mkm.aiphoto_admobmediation.Adapter.FilterAdapter;
import com.mkm.aiphoto_admobmediation.Adapter.GridAdapter;
import com.mkm.aiphoto_admobmediation.Adapter.GridItemToolsAdapter;
import com.mkm.aiphoto_admobmediation.Adapter.GridToolsAdapter;
import com.mkm.aiphoto_admobmediation.Adapter.RecyclerTabLayout;
import com.mkm.aiphoto_admobmediation.Adapter.StickerAdapter;
import com.mkm.aiphoto_admobmediation.Adapter.StickersTabAdapter;
import com.mkm.aiphoto_admobmediation.Assets.FilterFileAsset;
import com.mkm.aiphoto_admobmediation.Assets.StickerFileAsset;
import com.mkm.aiphoto_admobmediation.Database.DataRecent;
import com.mkm.aiphoto_admobmediation.Event.AlignHorizontallyEvent;
import com.mkm.aiphoto_admobmediation.Event.DeleteIconEvent;
import com.mkm.aiphoto_admobmediation.Event.EditTextIconEvent;
import com.mkm.aiphoto_admobmediation.Event.FlipHorizontallyEvent;
import com.mkm.aiphoto_admobmediation.Event.ZoomIconEvent;
import com.mkm.aiphoto_admobmediation.Fragment.CropFragment;
import com.mkm.aiphoto_admobmediation.Fragment.FilterFragment;
import com.mkm.aiphoto_admobmediation.Fragment.TextFragment;
import com.mkm.aiphoto_admobmediation.Listener.FilterListener;
import com.mkm.aiphoto_admobmediation.MKM;
import com.mkm.aiphoto_admobmediation.Mkm.Grid.PolishGrid;
import com.mkm.aiphoto_admobmediation.Mkm.Grid.PolishLayout;
import com.mkm.aiphoto_admobmediation.Mkm.Grid.PolishLayoutParser;
import com.mkm.aiphoto_admobmediation.Mkm.MKMGridView;
import com.mkm.aiphoto_admobmediation.Mkm.MKMPickerView;
import com.mkm.aiphoto_admobmediation.Mkm.MKMStickerIcons;
import com.mkm.aiphoto_admobmediation.Mkm.MKMStickerView;
import com.mkm.aiphoto_admobmediation.Mkm.MKMText;
import com.mkm.aiphoto_admobmediation.Mkm.MKMTextView;
import com.mkm.aiphoto_admobmediation.Model.General;
import com.mkm.aiphoto_admobmediation.Model.Model_Sticker;
import com.mkm.aiphoto_admobmediation.Model.Model_recent;
import com.mkm.aiphoto_admobmediation.Module.Module;
import com.mkm.aiphoto_admobmediation.Picker.PermissionsUtils;
import com.mkm.aiphoto_admobmediation.Preference.Preference;
import com.mkm.aiphoto_admobmediation.R;
import com.mkm.aiphoto_admobmediation.Sticker.DrawableSticker;
import com.mkm.aiphoto_admobmediation.Sticker.Sticker;
import com.mkm.aiphoto_admobmediation.Sticker.StoreSticker;
import com.mkm.aiphoto_admobmediation.Ultils.CollageUtils;
import com.mkm.aiphoto_admobmediation.Ultils.FilterUtils;
import com.mkm.aiphoto_admobmediation.Ultils.SaveFileUtils;
import com.mkm.aiphoto_admobmediation.Ultils.SystemUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.steelkiwi.cropiwa.AspectRatio;

import org.jetbrains.annotations.NotNull;
import org.wysaid.nativePort.CGENativeLibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MKMCollageActivity extends BaseActivity implements GridToolsAdapter.OnItemSelected,
        AspectAdapter.OnNewSelectedListener, StickerAdapter.OnClickSplashListener,
        CollageBackgroundAdapter.BackgroundGridListener, FilterListener,
        CropFragment.OnCropPhoto, CollageColorAdapter.BackgroundColorListener,
        FilterFragment.OnFilterSavePhoto, GridItemToolsAdapter.OnPieceFuncItemSelected,
        GridAdapter.OnItemClickListener {

    private static MKMCollageActivity QueShotGridActivityInstance;
    public static MKMCollageActivity QueShotGridActivityCollage;
    public PolishLayout queShotLayout;
    public MKMGridView queShotGridView;
    public AspectRatio aspectRatio;
    public CollageBackgroundAdapter.SquareView currentBackgroundState;
    private RelativeLayout relativeLayoutLoading;
    public GridToolsAdapter gridToolsAdapter = new GridToolsAdapter(this, true);
    private GridItemToolsAdapter gridItemToolsAdapter = new GridItemToolsAdapter(this);
    public LinearLayout linear_layout_wrapper_sticker_list;
    public Module moduleToolsId;
    public ImageView imageViewAddSticker;
    public float BorderRadius;
    public float Padding;
    private int deviceHeight = 0;
    public int deviceWidth = 0;
    // Guideline
    private Guideline guidelineTools;
    private Guideline guideline;
    public TextFragment.TextEditor textEditor;
    public TextFragment addTextFragment;
    private KeyboardHeightProvider keyboardHeightProvider;
    // ConstraintLayout
    public ConstraintLayout constraint_layout_change_background;
    public ConstraintLayout constrant_layout_change_Layout;
    public ConstraintLayout constraint_layout_filter_layout;
    private ConstraintLayout constraint_layout_collage_layout;
    private RelativeLayout constraint_save_control;
    private ConstraintLayout constraint_layout_wrapper_collage_view;
    public ConstraintLayout constraint_layout_sticker;
    private ConstraintLayout constraintLayoutSaveText;
    private ConstraintLayout constraintLayoutSaveSticker;
    private ConstraintLayout constraintLayoutAddText;
    // RecyclerView
    public RecyclerView recyclerViewTools;
    public RecyclerView recyclerViewFilter;
    private RecyclerView recycler_view_collage;
    private RecyclerView recycler_view_ratio;
    private RecyclerView recycler_view_blur;
    private RecyclerView recycler_view_color;
    private RecyclerView recycler_view_gradient;
    public RecyclerView recyclerViewToolsCollage;
    // ArrayList
    public ArrayList listFilterAll = new ArrayList<>();
    public List<Drawable> drawableList = new ArrayList<>();
    public static List<String> stringList;
    private String pllayout;
    public static List<String> newList;
    public List<Target> targets = new ArrayList();
    // TextView
    private LinearLayout linearLayoutBorder;
    private RelativeLayout relativeLayoutAddText;
    private TextView text_view_save;
    private TextView textViewTitle;
    private TextView textViewSeekBarPadding;
    private TextView textViewSeekBarRadius;
    public TextView textViewCancel;
    public TextView textViewDiscard;
    // SeekBar
    private SeekBar seekBarRadius;
    private SeekBar seekBarPadding;
    public SeekBar seekbarSticker;
    // Ads
    private LinearLayout bannerContainer;

    private Animation slideUpAnimation, slideDownAnimation;

    private LinearLayout linearLayoutLayer;
    private LinearLayout linearLayoutRatio;
    private LinearLayout linearLayoutBorde;
    private TextView textViewListLayer;
    private TextView textViewListRatio;
    private TextView textViewListBorder;
    private View viewCollage;
    private View viewBorder;
    private View viewRatio;

    public static Bitmap bm;

    private LinearLayout linearLayoutColor;
    private LinearLayout linearLayoutGradient;
    private LinearLayout linearLayoutBlur;
    private TextView textViewListColor;
    private TextView textViewListGradient;
    private TextView textViewListBlur;
    private View viewColor;
    private View viewGradient;
    private View viewBlur;
    private General generalModel;

    private List<Model_Sticker> list_tab_sticker;
    private List<Model_Sticker> listSticker;
    private File dir;
    private String namePack;

    private boolean SaveProject;
    private int numberlayout;
    private int numberfilter;
    private int numbercolor;
    private int numberborder;
    private int numberradius;
    private int numbergradient;
    public static int numberratio;
    public static int number_tabsticker;
    public static int number_sticker;
    public static String text_add;
    public static boolean savetext;

    private LottieAnimationView loadingframe;

    public static MKMGridView PlGridview;

    public static String path1;
    public static String path2;
    public static String path3;
    public static String path4;
    public static String path5;
    public static String path6;
    public static String path7;
    public static String path8;
    public static String path9;


    // new
    private ImageView imageViewSaveLayer;
    private ImageView imageViewCloseLayer;
    private ImageView imageViewSaveText;
    private ImageView imageViewCloseText;
    private ImageView imageViewSavebackground;
    private ImageView image_view_save_sticker;
    private ImageView imageViewClosebackground;
    private ImageView imageViewCloseFilter;
    private ImageView imageViewSaveFilter;
    private ImageView image_view_close_sticker;
    private SeekBar seekbar_border;
    private SeekBar seekbar_radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_mkmcollage);
        if (Build.VERSION.SDK_INT < 30) {
            getWindow().setSoftInputMode(72);
        }
        getTabSticker();
        this.deviceWidth = getResources().getDisplayMetrics().widthPixels;
        this.deviceHeight = getResources().getDisplayMetrics().heightPixels;

        findViewById(R.id.image_view_exit).setOnClickListener(view -> MKMCollageActivity.this.onBackPressed());
        this.queShotGridView = findViewById(R.id.collage_view);
        this.bannerContainer = findViewById(R.id.bannerContainer);
        this.constraintLayoutSaveText = findViewById(R.id.constraint_layout_confirm_save_text);
        this.constraintLayoutSaveSticker = findViewById(R.id.constraint_layout_confirm_save_sticker);
        this.constraint_layout_wrapper_collage_view = findViewById(R.id.constraint_layout_wrapper_collage_view);
        this.constraint_layout_filter_layout = findViewById(R.id.constraint_layout_filter_layout);
        this.recyclerViewTools = findViewById(R.id.recycler_view_tools);
        this.recyclerViewTools.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewTools.setAdapter(this.gridToolsAdapter);
        this.recyclerViewToolsCollage = findViewById(R.id.recycler_view_tools_collage);
        this.recyclerViewToolsCollage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewToolsCollage.setAdapter(this.gridItemToolsAdapter);
        this.seekBarPadding = findViewById(R.id.seekbar_border);
        seekbar_border = findViewById(R.id.seekbar_border);
//        this.seekBarPadding.setOnSeekBarChangeListener(this.onSeekBarChangeListener);
        seekbar_border_Change();
//        this.seekBarRadius = findViewById(R.id.seekbar_radius);
//        this.seekBarRadius.setOnSeekBarChangeListener(this.onSeekBarChangeListener);
        seekBarRadius = findViewById(R.id.seekbar_radius);
        seekBarRadius_change();

        this.numberlayout = 0;
        this.numberfilter = 0;
        this.numbercolor = 0;
        this.numberborder = 0;
        this.numberradius = 0;
        this.numbergradient = 0;
        this.numberratio = 0;
        this.number_tabsticker = -1;
        this.number_sticker = -1;
        this.text_add = "";

        this.numberlayout = getIntent().getIntExtra("number_layout", 0);
        this.numberfilter = getIntent().getIntExtra("number_filter", 0);
        this.numbercolor = getIntent().getIntExtra("number_color", 0);
        this.numberborder = getIntent().getIntExtra("number_boder", 0);
        this.numberradius = getIntent().getIntExtra("number_radius", 0);
        this.numbergradient = getIntent().getIntExtra("number_gradient", 0);
        this.numberratio = getIntent().getIntExtra("number_ratio", 0);
        this.number_tabsticker = getIntent().getIntExtra("number_tabsticker", -1);
        this.number_sticker = getIntent().getIntExtra("number_sticker", -1);
        this.text_add = getIntent().getStringExtra("text_add");


        this.stringList = getIntent().getStringArrayListExtra(GridPickerActivity.KEY_DATA_RESULT);

        path1 = null;
        path2 = null;
        path3 = null;
        path4 = null;
        path5 = null;
        path6 = null;
        path7 = null;
        path8 = null;
        path9 = null;

        for (int i = 0; i < this.stringList.size(); i++) {
            switch (i){
                case 0:
                    path1 = this.stringList.get(0);
                    break;
                case 1:
                    path2 = this.stringList.get(1);
                    break;
                case 2:
                    path3 = this.stringList.get(2);
                    break;
                case 3:
                    path4 = this.stringList.get(3);
                    break;
                case 4:
                    path5 = this.stringList.get(4);
                    break;
                case 5:
                    path6 = this.stringList.get(5);
                    break;
                case 6:
                    path7 = this.stringList.get(6);
                    break;
                case 7:
                    path8 = this.stringList.get(7);
                    break;
                case 8:
                    path9 = this.stringList.get(8);
                    break;
            }
        }

        this.relativeLayoutLoading = findViewById(R.id.relative_layout_loading);
        this.recyclerViewFilter = findViewById(R.id.recycler_view_filter);
        this.linearLayoutBorder = findViewById(R.id.linearLayoutPadding);
        this.guidelineTools = findViewById(R.id.guidelineTools);
        this.guideline = findViewById(R.id.guideline);
        this.relativeLayoutAddText = findViewById(R.id.relative_layout_add_text);
        this.relativeLayoutAddText.setVisibility(View.GONE);
        this.constraintLayoutAddText = findViewById(R.id.constraint_layout_confirm_text);

        if (numberlayout > 0) {
            this.queShotLayout = CollageUtils.getCollageLayouts(this.stringList.size()).get(numberlayout);
        }else {
            this.queShotLayout = CollageUtils.getCollageLayouts(this.stringList.size()).get(0);
        }

        this.queShotGridView.setQueShotLayout(this.queShotLayout);
        this.queShotGridView.setTouchEnable(true);
        this.queShotGridView.setNeedDrawLine(false);
        this.queShotGridView.setNeedDrawOuterLine(false);
        this.queShotGridView.setLineSize(4);
        this.queShotGridView.setCollagePadding(6.0f);
        this.queShotGridView.setCollageRadian(15.0f);
        this.queShotGridView.setLineColor(ContextCompat.getColor(this, R.color.white));
        this.queShotGridView.setSelectedLineColor(ContextCompat.getColor(this, R.color.mainColor));
        this.queShotGridView.setHandleBarColor(ContextCompat.getColor(this, R.color.mainColor));
        this.queShotGridView.setAnimateDuration(300);

        this.queShotGridView.setOnQueShotSelectedListener((collage, i) -> {
            MKMCollageActivity.this.recyclerViewTools.setVisibility(View.GONE);
            MKMCollageActivity.this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
            MKMCollageActivity.this.slideUp(MKMCollageActivity.this.recyclerViewToolsCollage);
            MKMCollageActivity.this.setGoneSave();
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) MKMCollageActivity.this.recyclerViewToolsCollage.getLayoutParams();
            layoutParams.bottomMargin = SystemUtil.dpToPx(MKMCollageActivity.this.getApplicationContext(), 10);
            MKMCollageActivity.this.recyclerViewToolsCollage.setLayoutParams(layoutParams);
            MKMCollageActivity.this.moduleToolsId = Module.COLLAGE;
            Log.d("fewfew", "queshot: " + "ok");
        });
        this.queShotGridView.setOnQueShotUnSelectedListener(() -> {
            MKMCollageActivity.this.recyclerViewToolsCollage.setVisibility(View.GONE);
            MKMCollageActivity.this.recyclerViewTools.setVisibility(View.VISIBLE);
            setVisibleSave();
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) recyclerViewToolsCollage.getLayoutParams();
            layoutParams.bottomMargin = 0;
            recyclerViewToolsCollage.setLayoutParams(layoutParams);
            moduleToolsId = Module.NONE;
        });

        //   this.queShotGridView = this.pllayout;

        this.constraint_save_control = findViewById(R.id.constraint_save_control);
        this.queShotGridView.post(() -> MKMCollageActivity.this.loadPhoto());


        //   findViewById(R.id.imageViewSaveLayer).setOnClickListener(this.onClickListener);
        imageViewSaveLayer = findViewById(R.id.imageViewSaveLayer);
        Click_imageViewSaveLayer();
//        findViewById(R.id.imageViewCloseLayer).setOnClickListener(this.onClickListener);
        imageViewCloseLayer = findViewById(R.id.imageViewCloseLayer);
        Click_imageViewCloseLayer();
//        findViewById(R.id.imageViewSaveText).setOnClickListener(this.onClickListener);
        imageViewSaveText = findViewById(R.id.imageViewSaveText);
        Click_imageViewSaveText();
//        findViewById(R.id.imageViewCloseText).setOnClickListener(this.onClickListener);
        imageViewCloseText = findViewById(R.id.imageViewCloseText);
        Click_imageViewCloseText();
//        findViewById(R.id.imageViewClosebackground).setOnClickListener(this.onClickListener);
        imageViewClosebackground = findViewById(R.id.imageViewClosebackground);
        Click_imageViewClosebackground();
//        findViewById(R.id.image_view_close_sticker).setOnClickListener(this.onClickListener);
        image_view_close_sticker = findViewById(R.id.image_view_close_sticker);
        Click_image_view_close_sticker();
//        findViewById(R.id.imageViewSaveFilter).setOnClickListener(this.onClickListener);
        imageViewSaveFilter = findViewById(R.id.imageViewSaveFilter);
        Click_imageViewSaveFilter();
//        findViewById(R.id.imageViewSavebackground).setOnClickListener(this.onClickListener);
        imageViewSavebackground = findViewById(R.id.imageViewSavebackground);
        Click_imageViewSavebackground();
//        findViewById(R.id.image_view_save_sticker).setOnClickListener(this.onClickListener);
        image_view_save_sticker = findViewById(R.id.image_view_save_sticker);
        Click_image_view_save_sticker();
//        findViewById(R.id.imageViewCloseFilter).setOnClickListener(this.onClickListener);
        imageViewCloseFilter = findViewById(R.id.imageViewCloseFilter);
        Click_imageViewCloseFilter();

        this.linearLayoutLayer = findViewById(R.id.linearLayoutCollage);
        this.linearLayoutBorde = findViewById(R.id.linearLayoutBorder);
        this.linearLayoutRatio = findViewById(R.id.linearLayoutRatio);
        this.textViewListLayer = findViewById(R.id.text_view_collage);
        this.textViewListBorder = findViewById(R.id.text_view_border);
        this.textViewListRatio = findViewById(R.id.text_view_ratio);
        this.viewCollage = findViewById(R.id.view_collage);
        this.viewBorder = findViewById(R.id.view_border);
        this.viewRatio = findViewById(R.id.view_ratio);
        this.linearLayoutLayer.setOnClickListener(view -> MKMCollageActivity.this.setLayer());
        this.linearLayoutBorde.setOnClickListener(view -> MKMCollageActivity.this.setBorder());
        this.linearLayoutRatio.setOnClickListener(view -> MKMCollageActivity.this.setRatio());

        this.linearLayoutColor = findViewById(R.id.linearLayoutColor);
        this.linearLayoutGradient = findViewById(R.id.linearLayoutGradient);
        this.linearLayoutBlur = findViewById(R.id.linearLayoutBlur);
        this.textViewListColor = findViewById(R.id.text_view_color);
        this.textViewListGradient = findViewById(R.id.text_view_gradient);
        this.textViewListBlur = findViewById(R.id.text_view_blur);
        this.viewGradient = findViewById(R.id.view_gradient);
        this.viewBlur = findViewById(R.id.view_blur);
        this.viewColor = findViewById(R.id.view_color);
        this.linearLayoutColor.setOnClickListener(view -> MKMCollageActivity.this.setBackgroundColor());
        this.linearLayoutGradient.setOnClickListener(view -> MKMCollageActivity.this.setBackgroundGradient());
        this.linearLayoutBlur.setOnClickListener(view -> MKMCollageActivity.this.selectBackgroundBlur());

        this.constrant_layout_change_Layout = findViewById(R.id.constrant_layout_change_Layout);
        this.textViewSeekBarPadding = findViewById(R.id.seekbarPadding);
        this.textViewSeekBarRadius = findViewById(R.id.seekbarRadius);
        GridAdapter collageAdapter = new GridAdapter();
        this.recycler_view_collage = findViewById(R.id.recycler_view_collage);
        this.recycler_view_collage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_collage.setAdapter(collageAdapter);
        collageAdapter.refreshData(CollageUtils.getCollageLayouts(this.stringList.size()), null);
        collageAdapter.setOnItemClickListener(this);
        AspectAdapter aspectRatioPreviewAdapter = new AspectAdapter(true);
        aspectRatioPreviewAdapter.setListener(this);
        this.recycler_view_ratio = findViewById(R.id.recycler_view_ratio);
        this.recycler_view_ratio.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_ratio.setAdapter(aspectRatioPreviewAdapter);
        this.linear_layout_wrapper_sticker_list = findViewById(R.id.linear_layout_wrapper_sticker_list);
        this.constraint_layout_sticker = findViewById(R.id.constraint_layout_sticker);
        this.seekbarSticker = findViewById(R.id.seekbarStickerAlpha);
        this.seekbarSticker.setVisibility(View.GONE);
        this.seekbarSticker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Sticker currentSticker = MKMCollageActivity.this.queShotGridView.getCurrentSticker();
                if (currentSticker != null) {
                    currentSticker.setAlpha(i);
                }
            }
        });

        this.relativeLayoutAddText.setOnClickListener(view -> {
            MKMCollageActivity.this.queShotGridView.setHandlingSticker(null);
            MKMCollageActivity.this.openTextFragment();
        });

        this.text_view_save = findViewById(R.id.text_view_save);
        this.text_view_save.setOnClickListener(view -> {
            //       SaveProject();
            SaveView();
        });
        this.imageViewAddSticker = findViewById(R.id.imageViewAddSticker);
        this.imageViewAddSticker.setVisibility(View.GONE);
        this.imageViewAddSticker.setOnClickListener(view -> {
            imageViewAddSticker.setVisibility(View.GONE);
            linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
        });

        MKMStickerIcons quShotStickerIconClose = new MKMStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_close), 0, MKMStickerIcons.DELETE);
        quShotStickerIconClose.setIconEvent(new DeleteIconEvent());
        MKMStickerIcons quShotStickerIconScale = new MKMStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_scale), 3, MKMStickerIcons.SCALE);
        quShotStickerIconScale.setIconEvent(new ZoomIconEvent());
        MKMStickerIcons quShotStickerIconFlip = new MKMStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_flip), 1, MKMStickerIcons.FLIP);
        quShotStickerIconFlip.setIconEvent(new FlipHorizontallyEvent());
        MKMStickerIcons quShotStickerIconCenter = new MKMStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_center), 2, MKMStickerIcons.ALIGN);
        quShotStickerIconCenter.setIconEvent(new AlignHorizontallyEvent());
        MKMStickerIcons quShotStickerIconRotate = new MKMStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_rotate), 3, MKMStickerIcons.ROTATE);
        quShotStickerIconRotate.setIconEvent(new ZoomIconEvent());
        MKMStickerIcons quShotStickerIconEdit = new MKMStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_edit), 1, MKMStickerIcons.EDIT);
        quShotStickerIconEdit.setIconEvent(new EditTextIconEvent());
        this.queShotGridView.setIcons(Arrays.asList(new MKMStickerIcons[]{quShotStickerIconClose, quShotStickerIconScale, quShotStickerIconFlip, quShotStickerIconEdit, quShotStickerIconRotate, quShotStickerIconCenter}));
        this.queShotGridView.setConstrained(true);
        this.queShotGridView.setOnStickerOperationListener(this.onStickerOperationListener);

        Preference.setKeyboard(getApplicationContext(), 0);
        this.keyboardHeightProvider = new KeyboardHeightProvider(this);
        this.keyboardHeightProvider.addKeyboardListener(i -> {
            if (i <= 0) {
                Preference.setHeightOfNotch(getApplicationContext(), -i);
            } else if (addTextFragment != null) {
                addTextFragment.updateAddTextBottomToolbarHeight(Preference.getHeightOfNotch(getApplicationContext()) + i);
                Preference.setKeyboard(getApplicationContext(), i + Preference.getHeightOfNotch(getApplicationContext()));
            }
        });

        setLoading(false);
        this.constraint_layout_change_background = findViewById(R.id.constrant_layout_change_background);
        this.constraint_layout_collage_layout = findViewById(R.id.constraint_layout_collage_layout);
        this.currentBackgroundState = new CollageBackgroundAdapter.SquareView(Color.parseColor("#ffffff"), "", true);
        this.recycler_view_color = findViewById(R.id.recycler_view_color);
        this.recycler_view_color.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_color.setHasFixedSize(true);
        this.recycler_view_color.setAdapter(new CollageColorAdapter(getApplicationContext(), this));
        this.recycler_view_gradient = findViewById(R.id.recycler_view_gradient);
        this.recycler_view_gradient.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_gradient.setHasFixedSize(true);
        this.recycler_view_gradient.setAdapter(new CollageBackgroundAdapter(getApplicationContext(), (CollageBackgroundAdapter.BackgroundGridListener) this, true));
        this.recycler_view_blur = findViewById(R.id.recycler_view_blur);
        this.recycler_view_blur.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_blur.setHasFixedSize(true);
        this.recycler_view_blur.setAdapter(new CollageBackgroundAdapter(getApplicationContext(), (CollageBackgroundAdapter.BackgroundGridListener) this, true));
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.queShotGridView.getLayoutParams();
        layoutParams.height = point.x;
        layoutParams.width = point.x;
        this.queShotGridView.setLayoutParams(layoutParams);
        this.aspectRatio = new AspectRatio(1, 1);
        this.queShotGridView.setAspectRatio(new AspectRatio(1, 1));
        QueShotGridActivityCollage = this;
        this.moduleToolsId = Module.NONE;
        CGENativeLibrary.setLoadImageCallback(this.loadImageCallback, (Object) null);
        QueShotGridActivityInstance = this;

        this.recyclerViewToolsCollage.setAlpha(0.0f);
        this.constraint_layout_collage_layout.post(() -> {
            slideDown(recyclerViewToolsCollage);
        });
        new Handler().postDelayed(() -> {
            recyclerViewToolsCollage.setAlpha(1.0f);
        }, 1000);

        if (numberfilter > 0 || numberlayout > 0 || numbercolor > 0 ||
                numberratio > 0 || numberradius > 0 || numbergradient > 0 ||
                numberborder > 0 || number_tabsticker > -1 || number_sticker > -1
                || text_add != null) {

            Log.d("ewfef", "\n numberfilter:  " + numberfilter +
                    "\n numberlayout: " + numberlayout
                    + "\n numbercolor: " + numbercolor + "\n numberratio: "
                    + numberratio + " \n numberradius: " + numberradius + " \n numbergradient: " +
                    numbergradient +"\n  numberborder: "+ numberborder + "\n number_tabsticker"
                    + "\n number_tabsticker: " + number_tabsticker + " \n number_sticker: " + number_sticker +
                    "\n text: " + text_add);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSave();
                }
            },1000);
        }
    }

    private void seekBarRadius_change() {
        seekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MKMCollageActivity.this.queShotGridView.setCollageRadian((float) progress);
                String valueRadius = String.valueOf(progress);
                textViewSeekBarRadius.setText(valueRadius);
                MKM.number_radius = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void seekbar_border_Change() {
        seekbar_border.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MKMCollageActivity.this.queShotGridView.setCollagePadding((float) progress);
                String valuePadding = String.valueOf(progress);
                textViewSeekBarPadding.setText(valuePadding);
                MKM.number_border = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void Click_image_view_close_sticker() {
        image_view_close_sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MKMCollageActivity.this.setVisibleSave();
                MKMCollageActivity.this.onBackPressed();
            }
        });
    }

    private void Click_imageViewSaveFilter() {
        imageViewSaveFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGuideLineTools();
                recyclerViewTools.setVisibility(View.VISIBLE);
                constraint_layout_filter_layout.setVisibility(View.GONE);
                recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                MKMCollageActivity.this.moduleToolsId = Module.NONE;
            }
        });

    }

    private void Click_imageViewCloseFilter() {
        imageViewCloseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MKMCollageActivity.this.setVisibleSave();
                MKMCollageActivity.this.onBackPressed();
            }
        });

    }

    private void Click_imageViewClosebackground() {
        imageViewClosebackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MKMCollageActivity.this.setVisibleSave();
                MKMCollageActivity.this.onBackPressed();
            }
        });

    }

    private void Click_image_view_save_sticker() {
        image_view_save_sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGuideLineTools();
                recyclerViewTools.setVisibility(View.VISIBLE);
                constraint_layout_sticker.setVisibility(View.GONE);
                constraintLayoutSaveSticker.setVisibility(View.GONE);
                MKMCollageActivity.this.queShotGridView.setHandlingSticker(null);
                MKMCollageActivity.this.seekbarSticker.setVisibility(View.GONE);
                MKMCollageActivity.this.imageViewAddSticker.setVisibility(View.GONE);
                MKMCollageActivity.this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                MKMCollageActivity.this.setVisibleSave();
                MKMCollageActivity.this.queShotGridView.setLocked(true);
                MKMCollageActivity.this.queShotGridView.setTouchEnable(true);
                MKMCollageActivity.this.moduleToolsId = Module.NONE;

                linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                setVisibleSave();
            }
        });
    }

    private void Click_imageViewSavebackground() {
        imageViewSavebackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGuideLineTools();
                recyclerViewTools.setVisibility(View.VISIBLE);
                constraint_layout_change_background.setVisibility(View.GONE);
                recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                MKMCollageActivity.this.setVisibleSave();
                MKMCollageActivity.this.queShotGridView.setLocked(true);
                MKMCollageActivity.this.queShotGridView.setTouchEnable(true);
                if (MKMCollageActivity.this.queShotGridView.getBackgroundResourceMode() == 0) {
                    MKMCollageActivity.this.currentBackgroundState.isColor = true;
                    MKMCollageActivity.this.currentBackgroundState.isBitmap = false;
                    MKMCollageActivity.this.currentBackgroundState.drawableId = ((ColorDrawable) MKMCollageActivity.this.queShotGridView.getBackground()).getColor();
                    MKMCollageActivity.this.currentBackgroundState.drawable = null;
                } else if (MKMCollageActivity.this.queShotGridView.getBackgroundResourceMode() == 1) {
                    MKMCollageActivity.this.currentBackgroundState.isColor = false;
                    MKMCollageActivity.this.currentBackgroundState.isBitmap = false;
                    MKMCollageActivity.this.currentBackgroundState.drawable = MKMCollageActivity.this.queShotGridView.getBackground();
                } else {
                    MKMCollageActivity.this.currentBackgroundState.isColor = false;
                    MKMCollageActivity.this.currentBackgroundState.isBitmap = true;
                    MKMCollageActivity.this.currentBackgroundState.drawable = MKMCollageActivity.this.queShotGridView.getBackground();
                }
                MKMCollageActivity.this.moduleToolsId = Module.NONE;
            }
        });
    }

    private void Click_imageViewCloseText() {
        imageViewCloseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MKMCollageActivity.this.setVisibleSave();
                MKMCollageActivity.this.onBackPressed();
            }
        });
    }

    private void Click_imageViewSaveText() {
        imageViewSaveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGuideLineTools();
                recyclerViewTools.setVisibility(View.VISIBLE);
                constraintLayoutAddText.setVisibility(View.GONE);
                constraintLayoutSaveText.setVisibility(View.GONE);
                queShotGridView.setHandlingSticker(null);
                queShotGridView.setLocked(true);
                relativeLayoutAddText.setVisibility(View.GONE);
                setVisibleSave();
                moduleToolsId = Module.NONE;
            }
        });
    }

    private void Click_imageViewCloseLayer() {
        imageViewCloseLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MKMCollageActivity.this.setVisibleSave();
                MKMCollageActivity.this.onBackPressed();
            }
        });
    }

    private void Click_imageViewSaveLayer() {
        imageViewSaveLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGuideLineTools();
                recyclerViewTools.setVisibility(View.VISIBLE);
                constrant_layout_change_Layout.setVisibility(View.GONE);
                MKMCollageActivity.this.setVisibleSave();
                recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                MKMCollageActivity.this.queShotLayout = MKMCollageActivity.this.queShotGridView.getQueShotLayout();
                MKMCollageActivity.this.BorderRadius = MKMCollageActivity.this.queShotGridView.getCollageRadian();
                MKMCollageActivity.this.Padding = MKMCollageActivity.this.queShotGridView.getCollagePadding();
                MKMCollageActivity.this.queShotGridView.setLocked(true);
                MKMCollageActivity.this.queShotGridView.setTouchEnable(true);
                MKMCollageActivity.this.aspectRatio = MKMCollageActivity.this.queShotGridView.getAspectRatio();
                MKMCollageActivity.this.moduleToolsId = Module.NONE;
            }
        });
    }

    private void getSave() {

        if (this.drawableList.isEmpty()) {
            for (PolishGrid drawable : this.queShotGridView.getQueShotGrids()) {
                this.drawableList.add(drawable.getDrawable());
            }
        }

        try {
            if (text_add.length() > 0) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSaveText();
                    }
                },300);

            }
        }catch (Exception e){
            Log.d("feefw", "error text: " + e);
        }

        try {
            if (numberratio > 0 ) {
                this.moduleToolsId = Module.valueOf("RATIO");
                setRatio();
                setGuideLine();
                this.constrant_layout_change_Layout.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SaveGetRatio();
                    }
                },100);
            }
        }catch (Exception e) {

        }

        try {

            if (number_tabsticker > -1 || number_sticker > -1 ) {

                Log.d("wfew", "\n 1: " + number_tabsticker + " \n 2: " + number_sticker);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SaveGetSticker();
                    }
                },200);

                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SaveStickerGet();
                    }
                },500);
            }

        }catch (Exception e) {

        }

        if (numbercolor > 0) {
            this.queShotGridView.setBackgroundColor(numbercolor);
            this.queShotGridView.setBackgroundResourceMode(0);
            Log.d("fewfew", "dd: " + numbercolor);
        }else if (numbergradient > 0) {
            Log.d("fewfew", "dd: " + numbergradient);
            this.queShotGridView.setBackgroundResource(numbergradient);
            this.queShotGridView.setBackgroundResourceMode(1);
        }

        MKMCollageActivity.this.queShotGridView.setCollageRadian((float) numberradius);
        String valueRadius = String.valueOf(numberradius);
        textViewSeekBarRadius.setText(valueRadius);

        MKMCollageActivity.this.queShotGridView.setCollagePadding((float) numberborder);
        String valuePadding = String.valueOf(numberborder);
        textViewSeekBarPadding.setText(valuePadding);

        new getFilter().execute();

    }

    private void getSaveText() {
        this.queShotGridView.setTouchEnable(false);
        setGoneSave();
        setGuideLine();
        this.queShotGridView.setLocked(false);
        openTextFragment();
        this.constraintLayoutAddText.setVisibility(View.VISIBLE);
        this.recyclerViewTools.setVisibility(View.GONE);
        this.constraintLayoutSaveText.setVisibility(View.VISIBLE);
        this.relativeLayoutAddText.setVisibility(View.VISIBLE);

        SaveText();

    }

    private void SaveText() {
        setGuideLineTools();
        this.recyclerViewTools.setVisibility(View.VISIBLE);
        this.constraintLayoutAddText.setVisibility(View.GONE);
        this.constraintLayoutSaveText.setVisibility(View.GONE);
        this.queShotGridView.setHandlingSticker(null);
        this.queShotGridView.setLocked(true);
        this.relativeLayoutAddText.setVisibility(View.GONE);
        setVisibleSave();
        this.moduleToolsId = Module.NONE;
    }

    private void SaveStickerGet() {
        setGuideLineTools();
        this.recyclerViewTools.setVisibility(View.VISIBLE);
        this.constraint_layout_sticker.setVisibility(View.GONE);
        this.constraintLayoutSaveSticker.setVisibility(View.GONE);
        MKMCollageActivity.this.queShotGridView.setHandlingSticker(null);
        MKMCollageActivity.this.seekbarSticker.setVisibility(View.GONE);
        MKMCollageActivity.this.imageViewAddSticker.setVisibility(View.GONE);
        MKMCollageActivity.this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
        MKMCollageActivity.this.setVisibleSave();
        MKMCollageActivity.this.queShotGridView.setLocked(true);
        MKMCollageActivity.this.queShotGridView.setTouchEnable(true);
        MKMCollageActivity.this.moduleToolsId = Module.NONE;

        this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
        setVisibleSave();
    }

    private void SaveGetSticker() {
        setGuideLine();
        this.constraint_layout_sticker.setVisibility(View.VISIBLE);
        this.recyclerViewTools.setVisibility(View.GONE);
        this.constraintLayoutSaveSticker.setVisibility(View.VISIBLE);
        this.recyclerViewToolsCollage.setVisibility(View.GONE);
        this.queShotGridView.updateLayout(this.queShotLayout);
        this.queShotGridView.setCollagePadding(this.Padding);
        this.queShotGridView.setCollageRadian(this.BorderRadius);
        getWindowManager().getDefaultDisplay().getSize(new Point());
        onNewAspectRatioSelected(this.aspectRatio);
        this.queShotGridView.setAspectRatio(this.aspectRatio);
        for (int i = 0; i < this.drawableList.size(); i++) {
            this.queShotGridView.getQueShotGrids().get(i).setDrawable(this.drawableList.get(i));
        }
        this.queShotGridView.invalidate();
        if (this.currentBackgroundState.isColor) {
            this.queShotGridView.setBackgroundResourceMode(0);
            this.queShotGridView.setBackgroundColor(this.currentBackgroundState.drawableId);
        } else {
            this.queShotGridView.setBackgroundResourceMode(1);
            if (this.currentBackgroundState.drawable != null) {
                this.queShotGridView.setBackground(this.currentBackgroundState.drawable);
            } else {
                this.queShotGridView.setBackgroundResource(this.currentBackgroundState.drawableId);
            }
        }
        setGoneSave();
        this.queShotGridView.setLocked(false);
        this.queShotGridView.setTouchEnable(false);
        this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
    }

    private void SaveGetRatio() {
        setGuideLineTools();
        this.recyclerViewTools.setVisibility(View.VISIBLE);
        this.constrant_layout_change_Layout.setVisibility(View.GONE);
        MKMCollageActivity.this.setVisibleSave();
        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
        MKMCollageActivity.this.queShotLayout = MKMCollageActivity.this.queShotGridView.getQueShotLayout();
        MKMCollageActivity.this.BorderRadius = MKMCollageActivity.this.queShotGridView.getCollageRadian();
        MKMCollageActivity.this.Padding = MKMCollageActivity.this.queShotGridView.getCollagePadding();
        MKMCollageActivity.this.queShotGridView.setLocked(true);
        MKMCollageActivity.this.queShotGridView.setTouchEnable(true);
        MKMCollageActivity.this.aspectRatio = MKMCollageActivity.this.queShotGridView.getAspectRatio();
        MKMCollageActivity.this.moduleToolsId = Module.NONE;
    }

    class getFilter extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            MKMCollageActivity.this.setLoading(true);

            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            MKMCollageActivity.this.listFilterAll.clear();
            MKMCollageActivity.this.listFilterAll.addAll(FilterFileAsset.getListBitmapFilter(ThumbnailUtils.extractThumbnail(((BitmapDrawable) MKMCollageActivity.this.queShotGridView.getQueShotGrids().get(0).getDrawable()).getBitmap(), 100, 100)));

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            MKMCollageActivity.this.recyclerViewFilter.setAdapter(new FilterAdapter(MKMCollageActivity.this.listFilterAll, MKMCollageActivity.this, MKMCollageActivity.this.getApplicationContext(), Arrays.asList(FilterFileAsset.FILTERS)));
            MKMCollageActivity.this.setLoading(false);

            FilterAdapter.filterListener.onFilterSelected(0,((FilterFileAsset.FiltersCode) FilterAdapter.filterBeanList.get(numberfilter)).getCode());

            super.onPostExecute(unused);
        }
    }

//    private void SaveProject() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.do_you_want_to_save_the_project));
//        builder.setCancelable(false);
//
//        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                SaveProject = false;
//                SaveView();
//            }
//        });
//
//        builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                SaveProject = true;
//                SaveView();
//            }
//        });
//
//        builder.show();
//    }

    private void getTabSticker() {
        listSticker = new ArrayList<>();
        list_tab_sticker = new ArrayList<>();
        getPackSticker();

    }

    private void getPackSticker() {

        dir = new File(Environment.getExternalStorageDirectory().toString()
                + "/" + StoreSticker.PathStore);

        new getPack().execute();
    }

    public class getPack extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            namePack = "";
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            File[] FileList = dir.listFiles();
            if (FileList != null) {
                for (File value : FileList) {
                    if (value.isDirectory() && !value.getName().contains(".zip")) {
                        dir = value;
                        namePack = value.getName();
                        new getPack().doInBackground();
                    }else {
                        if (!value.getPath().contains(".zip") && !value.getPath().contains("__MACOSX")
                                && !value.getPath().contains("DS_Store")) {
                            listSticker.add(new Model_Sticker(value.getPath(), namePack, false));
                        }
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            AddPackTab();
            super.onPostExecute(s);
        }
    }

    private void AddPackTab() {
        boolean match;

        for (int i = 0; i < listSticker.size(); i++) {

            match = false;

            for (int j = 0; j < list_tab_sticker.size(); j++) {
                if (listSticker.get(i).getStype().equals(list_tab_sticker.get(j).getStype())) {
                    match = true;
                }
            }

            if (match == false) {
                list_tab_sticker.add(listSticker.get(i));
                Log.d("efw", "false");
            }
        }

        list_tab_sticker.add(new Model_Sticker(R.drawable.bubble+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.rainbow+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.cartoon+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.child+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.flower+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.amoji+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.delicious+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.hand+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.popular+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.valentine+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.emoj+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.rage+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.christmas+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.unicorn+"", "", true));
        list_tab_sticker.add(new Model_Sticker(R.drawable.sticker+"", "", true));

        setAdapterSticker();
    }

    private void setAdapterSticker() {

        ViewPager stickerViewPager = findViewById(R.id.stickerViewpaper);

        stickerViewPager.setAdapter(new PagerAdapter() {
            public int getCount() {
                return list_tab_sticker.size();
            }

            public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
                return view.equals(obj);
            }

            @Override
            public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
                (container).removeView((View) object);
            }

            @NonNull
            public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
                View inflate = LayoutInflater.from(MKMCollageActivity.this.getBaseContext()).inflate(R.layout.list_sticker, null, false);
                RecyclerView recycler_view_sticker = inflate.findViewById(R.id.recyclerViewSticker);
                recycler_view_sticker.setHasFixedSize(true);
                recycler_view_sticker.setLayoutManager(new GridLayoutManager(MKMCollageActivity.this.getApplicationContext(), 7));
//                switch (i) {
//                    case 1:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.bubbleList(), i, PolishEditorActivity.this));
//                        break;
//                    case 2:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.rainbowList(), i, PolishEditorActivity.this));
//                        break;
//                    case 3:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.cartoonList(), i, PolishEditorActivity.this));
//                        break;
//                    case 4:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.childList(), i, PolishEditorActivity.this));
//                        break;
//                    case 5:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.flowerList(), i, PolishEditorActivity.this));
//                        break;
//                    case 6:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.amojiList(), i, PolishEditorActivity.this));
//                        break;
//                    case 7:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.deliciousList(), i, PolishEditorActivity.this));
//                        break;
//                    case 8:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.handList(), i, PolishEditorActivity.this));
//                        break;
//                    case 9:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.popularList(), i, PolishEditorActivity.this));
//                        break;
//                    case 10:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.valentineList(), i, PolishEditorActivity.this));
//                        break;
//                    case 11:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.emojList(), i, PolishEditorActivity.this));
//                        break;
//                    case 12:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.rageList(), i, PolishEditorActivity.this));
//                        break;
//                    case 13:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.christmasList(), i, PolishEditorActivity.this));
//                        break;
//                    case 14:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.unicornList(), i, PolishEditorActivity.this));
//                        break;
//                    case 15:
//                        recycler_view_sticker.setAdapter(new StickerAdapter(PolishEditorActivity.this.getApplicationContext(), StickerFileAsset.stickerList(), i, PolishEditorActivity.this));
//                        break;
//
//                }

                if (i == list_tab_sticker.size()-1) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.stickerList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() -2) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.unicornList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 3) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.christmasList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 4) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.rageList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 5) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.emojList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 6) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.valentineList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 7) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.popularList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 8) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.handList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 9) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.deliciousList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 10) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.amojiList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 11) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.flowerList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 12) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.childList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 13) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.cartoonList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 14) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.rainbowList(), i, MKMCollageActivity.this));
                }else if (i == list_tab_sticker.size() - 15) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), StickerFileAsset.bubbleList(), i, MKMCollageActivity.this));
                }

                int sizePack = list_tab_sticker.size() - 15;

                List<String> listStickerAdd = new ArrayList<>();
                String style;
                for (int k = 0; k < listSticker.size(); k++) {
                    style = listSticker.get(i).getStype();
                    if (listSticker.get(k).getStype().equals(style)) {
                        listStickerAdd.add(listSticker.get(k).getPath());
                    }
                }

                if (i == sizePack-1) {
                    recycler_view_sticker.setAdapter(new StickerAdapter(MKMCollageActivity.this.getApplicationContext(), listStickerAdd, i, MKMCollageActivity.this));
                }

                viewGroup.addView(inflate);
                return inflate;
            }
        });

        RecyclerTabLayout recycler_tab_layout = findViewById(R.id.recycler_tab_layout);
        recycler_tab_layout.setUpWithAdapter(new StickersTabAdapter(stickerViewPager, getApplicationContext(), list_tab_sticker));
        recycler_tab_layout.setPositionThreshold(0.5f);
        recycler_tab_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.TabColor));
    }

    ActivityResultLauncher<Intent> paymentResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    /*if(recyclerViewFilter!=null)recyclerViewFilter.getAdapter().notifyDataSetChanged();
                    if(recycler_view_color!=null)recycler_view_color.getAdapter().notifyDataSetChanged();
                    if(recycler_view_collage!=null)recycler_view_collage.getAdapter().notifyDataSetChanged();*/

                    recyclerViewTools.setVisibility(View.VISIBLE);

                }
            });

    private void SaveView() {
        if (PermissionsUtils.checkWriteStoragePermission(MKMCollageActivity.this)) {
            PlGridview = MKMCollageActivity.this.queShotGridView;

            Bitmap createBitmap = SaveFileUtils.createBitmap(MKMCollageActivity.this.queShotGridView, 1920);
            Bitmap createBitmap2 = MKMCollageActivity.this.queShotGridView.createBitmap();
            new SaveCollageAsFile().execute(new Bitmap[]{createBitmap, createBitmap2});

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            this.queShotGridView.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPause() {
        super.onPause();
        this.keyboardHeightProvider.onPause();
    }

    public void onResume() {
        super.onResume();
        this.keyboardHeightProvider.onResume();
    }

    public void slideDown(View view) {
        ObjectAnimator.ofFloat(view, "translationY", 0.0f, (float) view.getHeight()).start();
    }

    public void slideUp(View view) {
        ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) view.getHeight(), 0.0f}).start();
    }

    private void openTextFragment() {
        this.addTextFragment = TextFragment.show(this);
        this.textEditor = new TextFragment.TextEditor() {
            public void onDone(MKMText addTextProperties) {
                MKMCollageActivity.this.queShotGridView.addSticker(new MKMTextView(MKMCollageActivity.this.getApplicationContext(), addTextProperties));
            }

            public void onBackButton() {
                if (MKMCollageActivity.this.queShotGridView.getStickers().isEmpty()) {
                    MKMCollageActivity.this.onBackPressed();
                }
            }
        };
        this.addTextFragment.setOnTextEditorListener(this.textEditor);
    }


    @SuppressLint("NonConstantResourceId")
    public View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
//            case R.id.imageViewClosebackground:
//            case R.id.imageViewCloseFilter:
//            case R.id.imageViewCloseLayer:
//            case R.id.image_view_close_sticker:
////            case R.id.imageViewCloseText:
//                MKMCollageActivity.this.setVisibleSave();
//                MKMCollageActivity.this.onBackPressed();
//                return;
//            case R.id.imageViewSavebackground:
//                setGuideLineTools();
//                this.recyclerViewTools.setVisibility(View.VISIBLE);
//                this.constraint_layout_change_background.setVisibility(View.GONE);
//                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
//                MKMCollageActivity.this.setVisibleSave();
//                MKMCollageActivity.this.queShotGridView.setLocked(true);
//                MKMCollageActivity.this.queShotGridView.setTouchEnable(true);
//                if (MKMCollageActivity.this.queShotGridView.getBackgroundResourceMode() == 0) {
//                    MKMCollageActivity.this.currentBackgroundState.isColor = true;
//                    MKMCollageActivity.this.currentBackgroundState.isBitmap = false;
//                    MKMCollageActivity.this.currentBackgroundState.drawableId = ((ColorDrawable) MKMCollageActivity.this.queShotGridView.getBackground()).getColor();
//                    MKMCollageActivity.this.currentBackgroundState.drawable = null;
//                } else if (MKMCollageActivity.this.queShotGridView.getBackgroundResourceMode() == 1) {
//                    MKMCollageActivity.this.currentBackgroundState.isColor = false;
//                    MKMCollageActivity.this.currentBackgroundState.isBitmap = false;
//                    MKMCollageActivity.this.currentBackgroundState.drawable = MKMCollageActivity.this.queShotGridView.getBackground();
//                } else {
//                    MKMCollageActivity.this.currentBackgroundState.isColor = false;
//                    MKMCollageActivity.this.currentBackgroundState.isBitmap = true;
//                    MKMCollageActivity.this.currentBackgroundState.drawable = MKMCollageActivity.this.queShotGridView.getBackground();
//                }
//                MKMCollageActivity.this.moduleToolsId = Module.NONE;
//                return;
//            case R.id.imageViewSaveFilter:
//                setGuideLineTools();
//                this.recyclerViewTools.setVisibility(View.VISIBLE);
//                this.constraint_layout_filter_layout.setVisibility(View.GONE);
//                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
//                MKMCollageActivity.this.moduleToolsId = Module.NONE;
//                return;
//            case R.id.imageViewSaveText:
//                setGuideLineTools();
//                this.recyclerViewTools.setVisibility(View.VISIBLE);
//                this.constraintLayoutAddText.setVisibility(View.GONE);
//                this.constraintLayoutSaveText.setVisibility(View.GONE);
//                this.queShotGridView.setHandlingSticker(null);
//                this.queShotGridView.setLocked(true);
//                this.relativeLayoutAddText.setVisibility(View.GONE);
//                setVisibleSave();
//                this.moduleToolsId = Module.NONE;
//                return;
//            case R.id.imageViewSaveLayer:
////                setGuideLineTools();
////                this.recyclerViewTools.setVisibility(View.VISIBLE);
////                this.constrant_layout_change_Layout.setVisibility(View.GONE);
////                MKMCollageActivity.this.setVisibleSave();
////                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
////                MKMCollageActivity.this.queShotLayout = MKMCollageActivity.this.queShotGridView.getQueShotLayout();
////                MKMCollageActivity.this.BorderRadius = MKMCollageActivity.this.queShotGridView.getCollageRadian();
////                MKMCollageActivity.this.Padding = MKMCollageActivity.this.queShotGridView.getCollagePadding();
////                MKMCollageActivity.this.queShotGridView.setLocked(true);
////                MKMCollageActivity.this.queShotGridView.setTouchEnable(true);
////                MKMCollageActivity.this.aspectRatio = MKMCollageActivity.this.queShotGridView.getAspectRatio();
////                MKMCollageActivity.this.moduleToolsId = Module.NONE;
//                return;
//            case R.id.image_view_save_sticker:
//                setGuideLineTools();
//                this.recyclerViewTools.setVisibility(View.VISIBLE);
//                this.constraint_layout_sticker.setVisibility(View.GONE);
//                this.constraintLayoutSaveSticker.setVisibility(View.GONE);
//                MKMCollageActivity.this.queShotGridView.setHandlingSticker(null);
//                MKMCollageActivity.this.seekbarSticker.setVisibility(View.GONE);
//                MKMCollageActivity.this.imageViewAddSticker.setVisibility(View.GONE);
//                MKMCollageActivity.this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
//                MKMCollageActivity.this.setVisibleSave();
//                MKMCollageActivity.this.queShotGridView.setLocked(true);
//                MKMCollageActivity.this.queShotGridView.setTouchEnable(true);
//                MKMCollageActivity.this.moduleToolsId = Module.NONE;
//
//                this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
//                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
//                setVisibleSave();
//                return;
            default:
        }
    };


    public SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
//            switch (seekBar.getId()) {
////                case R.id.seekbar_border:
////                    MKMCollageActivity.this.queShotGridView.setCollagePadding((float) i);
////                    String valuePadding = String.valueOf(i);
////                    textViewSeekBarPadding.setText(valuePadding);
////                    MKM.number_border = i;
////                    break;
//                case R.id.seekbar_radius:
//                    MKMCollageActivity.this.queShotGridView.setCollageRadian((float) i);
//                    String valueRadius = String.valueOf(i);
//                    textViewSeekBarRadius.setText(valueRadius);
//                    MKM.number_radius = i;
//                    break;
//            }
            MKMCollageActivity.this.queShotGridView.invalidate();
        }
    };
    MKMStickerView.OnStickerOperationListener onStickerOperationListener = new MKMStickerView.OnStickerOperationListener() {
        public void onStickerDrag(@NonNull Sticker sticker) {
        }

        public void onStickerFlip(@NonNull Sticker sticker) {
        }

        public void onStickerTouchedDown(@NonNull Sticker sticker) {
        }

        public void onStickerZoom(@NonNull Sticker sticker) {
        }

        public void onTouchDownBeauty(float f, float f2) {
        }

        public void onTouchDragBeauty(float f, float f2) {
        }

        public void onTouchUpBeauty(float f, float f2) {
        }

        public void onAddSticker(@NonNull Sticker sticker) {
            MKMCollageActivity.this.seekbarSticker.setVisibility(View.VISIBLE);
            MKMCollageActivity.this.seekbarSticker.setProgress(sticker.getAlpha());
        }

        public void onStickerSelected(@NonNull Sticker sticker) {
            MKMCollageActivity.this.seekbarSticker.setVisibility(View.VISIBLE);
            MKMCollageActivity.this.seekbarSticker.setProgress(sticker.getAlpha());
        }

        public void onStickerDeleted(@NonNull Sticker sticker) {
            MKMCollageActivity.this.seekbarSticker.setVisibility(View.GONE);
        }

        public void onStickerTouchOutside() {
            MKMCollageActivity.this.seekbarSticker.setVisibility(View.GONE);
        }

        public void onStickerDoubleTap(@NonNull Sticker sticker) {
            if (sticker instanceof MKMTextView) {
                sticker.setShow(false);
                MKMCollageActivity.this.queShotGridView.setHandlingSticker(null);
                MKMCollageActivity.this.addTextFragment = TextFragment.show(MKMCollageActivity.this, ((MKMTextView) sticker).getPolishText());
                MKMCollageActivity.this.textEditor = new TextFragment.TextEditor() {
                    public void onDone(MKMText addTextProperties) {
                        MKMCollageActivity.this.queShotGridView.getStickers().remove(MKMCollageActivity.this.queShotGridView.getLastHandlingSticker());
                        MKMCollageActivity.this.queShotGridView.addSticker(new MKMTextView(MKMCollageActivity.this, addTextProperties));
                    }

                    public void onBackButton() {
                        MKMCollageActivity.this.queShotGridView.showLastHandlingSticker();
                    }
                };
                MKMCollageActivity.this.addTextFragment.setOnTextEditorListener(MKMCollageActivity.this.textEditor);
            }
        }
    };


    public static MKMCollageActivity getQueShotGridActivityInstance() {
        return QueShotGridActivityInstance;
    }

    public void isPermissionGranted(boolean z, String str) {
        if (z) {
            Bitmap createBitmap = SaveFileUtils.createBitmap(this.queShotGridView, 1920);
            Bitmap createBitmap2 = this.queShotGridView.createBitmap();
            new SaveCollageAsFile().execute(createBitmap, createBitmap2);
        }
    }

    public CGENativeLibrary.LoadImageCallback loadImageCallback = new CGENativeLibrary.LoadImageCallback() {
        public Bitmap loadImage(String string, Object object) {
            try {
                return BitmapFactory.decodeStream(MKMCollageActivity.this.getAssets().open(string));
            } catch (IOException ioException) {
                return null;
            }
        }

        public void loadImageOK(Bitmap bitmap, Object object) {
            bitmap.recycle();
        }
    };

    public void setBackgroundColor() {
        this.recycler_view_color.scrollToPosition(0);
        ((CollageColorAdapter) this.recycler_view_color.getAdapter()).setSelectedIndex(-1);
        this.recycler_view_color.getAdapter().notifyDataSetChanged();
        this.recycler_view_color.setVisibility(View.VISIBLE);
        this.recycler_view_gradient.setVisibility(View.GONE);
        this.recycler_view_blur.setVisibility(View.GONE);
        this.textViewListColor.setTextColor(ContextCompat.getColor(this, R.color.Pink));
        this.textViewListGradient.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.textViewListBlur.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.viewColor.setVisibility(View.VISIBLE);
        this.viewGradient.setVisibility(View.INVISIBLE);
        this.viewBlur.setVisibility(View.INVISIBLE);
    }

    public void setBackgroundGradient() {
        this.recycler_view_gradient.scrollToPosition(0);
        ((CollageBackgroundAdapter) this.recycler_view_gradient.getAdapter()).setSelectedIndex(-1);
        this.recycler_view_gradient.getAdapter().notifyDataSetChanged();
        this.recycler_view_color.setVisibility(View.GONE);
        this.recycler_view_gradient.setVisibility(View.VISIBLE);
        this.recycler_view_blur.setVisibility(View.GONE);
        this.textViewListColor.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.textViewListGradient.setTextColor(ContextCompat.getColor(this, R.color.Pink));
        this.textViewListBlur.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.viewColor.setVisibility(View.INVISIBLE);
        this.viewGradient.setVisibility(View.VISIBLE);
        this.viewBlur.setVisibility(View.INVISIBLE);
    }

    public void selectBackgroundBlur() {
        ArrayList arrayList = new ArrayList();
        for (PolishGrid drawable : this.queShotGridView.getQueShotGrids()) {
            arrayList.add(drawable.getDrawable());
        }
        CollageBackgroundAdapter backgroundGridAdapter = new CollageBackgroundAdapter(getApplicationContext(), this, (List<Drawable>) arrayList);
        backgroundGridAdapter.setSelectedIndex(-1);
        this.recycler_view_blur.setAdapter(backgroundGridAdapter);
        this.recycler_view_color.setVisibility(View.GONE);
        this.recycler_view_gradient.setVisibility(View.GONE);
        this.recycler_view_blur.setVisibility(View.VISIBLE);
        this.textViewListColor.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.textViewListGradient.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.textViewListBlur.setTextColor(ContextCompat.getColor(this, R.color.Pink));
        this.viewColor.setVisibility(View.INVISIBLE);
        this.viewGradient.setVisibility(View.INVISIBLE);
        this.viewBlur.setVisibility(View.VISIBLE);
    }

    public void setLayer() {
        this.recycler_view_collage.setVisibility(View.VISIBLE);
        this.recycler_view_ratio.setVisibility(View.GONE);
        this.linearLayoutBorder.setVisibility(View.GONE);
        this.textViewListLayer.setTextColor(ContextCompat.getColor(this, R.color.Pink));
        this.textViewListBorder.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.textViewListRatio.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.viewCollage.setVisibility(View.VISIBLE);
        this.viewBorder.setVisibility(View.INVISIBLE);
        this.viewRatio.setVisibility(View.INVISIBLE);
    }

    public void setBorder() {
        this.recycler_view_collage.setVisibility(View.GONE);
        this.recycler_view_ratio.setVisibility(View.GONE);
        this.linearLayoutBorder.setVisibility(View.VISIBLE);
        this.textViewListLayer.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.textViewListBorder.setTextColor(ContextCompat.getColor(this, R.color.Pink));
        this.textViewListRatio.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.viewCollage.setVisibility(View.INVISIBLE);
        this.viewBorder.setVisibility(View.VISIBLE);
        this.viewRatio.setVisibility(View.INVISIBLE);
        this.seekBarPadding.setProgress((int) this.queShotGridView.getCollagePadding());
        this.seekBarRadius.setProgress((int) this.queShotGridView.getCollageRadian());

    }

    public void setRatio() {
        this.recycler_view_collage.setVisibility(View.GONE);
        this.recycler_view_ratio.setVisibility(View.VISIBLE);
        this.linearLayoutBorder.setVisibility(View.GONE);
        this.textViewListLayer.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.textViewListBorder.setTextColor(ContextCompat.getColor(this, R.color.black));
        this.textViewListRatio.setTextColor(ContextCompat.getColor(this, R.color.Pink));
        this.viewCollage.setVisibility(View.INVISIBLE);
        this.viewBorder.setVisibility(View.INVISIBLE);
        this.viewRatio.setVisibility(View.VISIBLE);

    }

    public void onToolSelected(Module module) {
        this.moduleToolsId = module;
        switch (module) {
            case LAYER:
                //               CheckRotate();
                setLayer();
                setGuideLine();
                this.constrant_layout_change_Layout.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotLayout = this.queShotGridView.getQueShotLayout();
                this.aspectRatio = this.queShotGridView.getAspectRatio();
                this.BorderRadius = this.queShotGridView.getCollageRadian();
                this.Padding = this.queShotGridView.getCollagePadding();
                this.recycler_view_collage.scrollToPosition(0);
                ((GridAdapter) this.recycler_view_collage.getAdapter()).setSelectedIndex(-1);
                this.recycler_view_collage.getAdapter().notifyDataSetChanged();
                this.recycler_view_ratio.scrollToPosition(0);
                ((AspectAdapter) this.recycler_view_ratio.getAdapter()).setLastSelectedView(-1);
                this.recycler_view_ratio.getAdapter().notifyDataSetChanged();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                return;
            case PADDING:
                setBorder();
                setGuideLine();
                this.constrant_layout_change_Layout.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotLayout = this.queShotGridView.getQueShotLayout();
                this.aspectRatio = this.queShotGridView.getAspectRatio();
                this.BorderRadius = this.queShotGridView.getCollageRadian();
                this.Padding = this.queShotGridView.getCollagePadding();
                this.recycler_view_collage.scrollToPosition(0);
                ((GridAdapter) this.recycler_view_collage.getAdapter()).setSelectedIndex(-1);
                this.recycler_view_collage.getAdapter().notifyDataSetChanged();
                this.recycler_view_ratio.scrollToPosition(0);
                ((AspectAdapter) this.recycler_view_ratio.getAdapter()).setLastSelectedView(-1);
                this.recycler_view_ratio.getAdapter().notifyDataSetChanged();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                return;
            case RATIO:
                setRatio();
                setGuideLine();
                this.constrant_layout_change_Layout.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotLayout = this.queShotGridView.getQueShotLayout();
                this.aspectRatio = this.queShotGridView.getAspectRatio();
                this.BorderRadius = this.queShotGridView.getCollageRadian();
                this.Padding = this.queShotGridView.getCollagePadding();
                this.recycler_view_collage.scrollToPosition(0);
                ((GridAdapter) this.recycler_view_collage.getAdapter()).setSelectedIndex(-1);
                this.recycler_view_collage.getAdapter().notifyDataSetChanged();
                this.recycler_view_ratio.scrollToPosition(0);
                ((AspectAdapter) this.recycler_view_ratio.getAdapter()).setLastSelectedView(-1);
                this.recycler_view_ratio.getAdapter().notifyDataSetChanged();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                return;
            case FILTER:
                if (this.drawableList.isEmpty()) {
                    for (PolishGrid drawable : this.queShotGridView.getQueShotGrids()) {
                        this.drawableList.add(drawable.getDrawable());
                    }
                }
                new allFilters().execute();
                //     setGoneSave();
                return;
            case TEXT:
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                setGuideLine();
                this.queShotGridView.setLocked(false);
                openTextFragment();
                this.constraintLayoutAddText.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.constraintLayoutSaveText.setVisibility(View.VISIBLE);
                this.relativeLayoutAddText.setVisibility(View.VISIBLE);
                return;
            case STICKER:
                setGuideLine();
                this.constraint_layout_sticker.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.constraintLayoutSaveSticker.setVisibility(View.VISIBLE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotGridView.updateLayout(this.queShotLayout);
                this.queShotGridView.setCollagePadding(this.Padding);
                this.queShotGridView.setCollageRadian(this.BorderRadius);
                getWindowManager().getDefaultDisplay().getSize(new Point());
                onNewAspectRatioSelected(this.aspectRatio);
                this.queShotGridView.setAspectRatio(this.aspectRatio);
                for (int i = 0; i < this.drawableList.size(); i++) {
                    this.queShotGridView.getQueShotGrids().get(i).setDrawable(this.drawableList.get(i));
                }
                this.queShotGridView.invalidate();
                if (this.currentBackgroundState.isColor) {
                    this.queShotGridView.setBackgroundResourceMode(0);
                    this.queShotGridView.setBackgroundColor(this.currentBackgroundState.drawableId);
                } else {
                    this.queShotGridView.setBackgroundResourceMode(1);
                    if (this.currentBackgroundState.drawable != null) {
                        this.queShotGridView.setBackground(this.currentBackgroundState.drawable);
                    } else {
                        this.queShotGridView.setBackgroundResource(this.currentBackgroundState.drawableId);
                    }
                }
                setGoneSave();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);

                return;
            case GRADIENT:
                setGuideLine();
                this.constraint_layout_change_background.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                setBackgroundColor();
                if (this.queShotGridView.getBackgroundResourceMode() == 0) {
                    this.currentBackgroundState.isColor = true;
                    this.currentBackgroundState.isBitmap = false;
                    this.currentBackgroundState.drawableId = ((ColorDrawable) this.queShotGridView.getBackground()).getColor();
                    return;
                } else if (this.queShotGridView.getBackgroundResourceMode() == 2 || (this.queShotGridView.getBackground() instanceof ColorDrawable)) {
                    this.currentBackgroundState.isBitmap = true;
                    this.currentBackgroundState.isColor = false;
                    this.currentBackgroundState.drawable = this.queShotGridView.getBackground();
                    return;
                } else if (this.queShotGridView.getBackground() instanceof GradientDrawable) {
                    this.currentBackgroundState.isBitmap = false;
                    this.currentBackgroundState.isColor = false;
                    this.currentBackgroundState.drawable = this.queShotGridView.getBackground();
                    return;
                } else {
                    return;
                }
            default:
        }
    }

    private void CheckRotate() {

        new CheckRotate().execute();
    }

    public class CheckRotate extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            newList = new ArrayList<>();
            loadingframe = findViewById(R.id.loadingframe);
            loadingframe.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            for (int i = 0; i < stringList.size(); i++) {

                File file = new File(stringList.get(i));

                Bitmap bitmap = BitmapFactory.decodeFile(stringList.get(i));

                ExifInterface ei = null;
                try {
                    ei = new ExifInterface(stringList.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap rotatedBitmap = null;
                switch(orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(bitmap, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(bitmap, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(bitmap, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = bitmap;
                }

                // save new

                SaveBitmapToFile(rotatedBitmap, file.getName());

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            loadingframe.setVisibility(View.GONE);

            for (int i = 0; i < newList.size(); i ++) {
                Log.d("efweefw", "\n path: " + stringList.get(i) + "\n new path: " + newList.get(i));
            }

            Intent intent = new Intent(MKMCollageActivity.this, CollageActivity.class);
            intent.putExtra("imageCount", stringList.size());
            intent.putExtra("selectedImages", stringList +"");
            intent.putExtra("imagesinTemplate", stringList.size());
            startActivityForResult(intent, 111);
            super.onPostExecute(s);
        }
    }

    private void SaveBitmapToFile(Bitmap rotatedBitmap, String name) {
        String path = getExternalFilesDir(Environment.DIRECTORY_DCIM).toString();
        OutputStream fOut = null;
        Integer counter = 0;
        File file = new File(path, name+""); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("wefwe", "error: " + e);
        }
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        try {
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        newList.add(path + "/" +name+"");
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public void loadPhoto() {
        final int i;
        final ArrayList arrayList = new ArrayList();
        if (this.stringList.size() > this.queShotLayout.getAreaCount()) {
            i = this.queShotLayout.getAreaCount();
        } else {
            i = this.stringList.size();
        }
        for (int i2 = 0; i2 < i; i2++) {
            Target r4 = new Target() {
                public void onBitmapFailed(Exception exc, Drawable drawable) {
                }

                public void onPrepareLoad(Drawable drawable) {
                }

                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    int width = bitmap.getWidth();
                    float f = (float) width;
                    float height = (float) bitmap.getHeight();
                    float max = Math.max(f / f, height / f);
                    if (max > 1.0f) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (f / max), (int) (height / max), false);
                    }
                    arrayList.add(bitmap);
                    if (arrayList.size() == i) {
                        if (MKMCollageActivity.this.stringList.size() < MKMCollageActivity.this.queShotLayout.getAreaCount()) {
                            for (int i = 0; i < MKMCollageActivity.this.queShotLayout.getAreaCount(); i++) {
                                MKMCollageActivity.this.queShotGridView.addQuShotCollage((Bitmap) arrayList.get(i % i));
                            }
                        } else {
                            MKMCollageActivity.this.queShotGridView.addPieces(arrayList);
                        }
                    }
                    MKMCollageActivity.this.targets.remove(this);
                }
            };
            Picasso picasso = Picasso.get();
            picasso.load("file:///" + this.stringList.get(i2)).resize(this.deviceWidth, this.deviceWidth).centerInside().config(Bitmap.Config.RGB_565).into((Target) r4);
            this.targets.add(r4);
        }
    }

    private void setOnBackPressDialog() {
        final Dialog dialogOnBackPressed = new Dialog(MKMCollageActivity.this, R.style.UploadDialog);
        dialogOnBackPressed.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOnBackPressed.setContentView(R.layout.dialog_exit);
        dialogOnBackPressed.setCancelable(true);
        dialogOnBackPressed.show();
        this.textViewCancel = dialogOnBackPressed.findViewById(R.id.textViewCancel);
        this.textViewDiscard = dialogOnBackPressed.findViewById(R.id.textViewDiscard);

        this.textViewDiscard.setOnClickListener(view -> {
            dialogOnBackPressed.dismiss();
            MKMCollageActivity.this.moduleToolsId = null;
            MKMCollageActivity.this.finish();
            finish();
        });

        this.textViewCancel.setOnClickListener(view -> {
            dialogOnBackPressed.dismiss();
        });
    }

    public void setGuideLineTools() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.constraint_layout_collage_layout);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 1, this.constraint_layout_collage_layout.getId(), 1, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 4, this.guidelineTools.getId(), 3, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 2, this.constraint_layout_collage_layout.getId(), 2, 0);
        constraintSet.applyTo(this.constraint_layout_collage_layout);
    }

    public void setGuideLine() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.constraint_layout_collage_layout);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 1, this.constraint_layout_collage_layout.getId(), 1, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 4, this.guideline.getId(), 3, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 2, this.constraint_layout_collage_layout.getId(), 2, 0);
        constraintSet.applyTo(this.constraint_layout_collage_layout);
    }

    public void setGoneSave() {
        this.constraint_save_control.setVisibility(View.GONE);
    }

    public void setVisibleSave() {
        this.constraint_save_control.setVisibility(View.VISIBLE);
    }

    public void onBackPressed() {
        if (this.moduleToolsId == null) {
            super.onBackPressed();
            return;
        }
        try {
            switch (this.moduleToolsId) {
                case PADDING:
                case RATIO:
                case LAYER:
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constrant_layout_change_Layout.setVisibility(View.GONE);
                    this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                    setVisibleSave();
                    this.queShotGridView.updateLayout(this.queShotLayout);
                    this.queShotGridView.setCollagePadding(this.Padding);
                    this.queShotGridView.setCollageRadian(this.BorderRadius);
                    this.moduleToolsId = Module.NONE;
                    getWindowManager().getDefaultDisplay().getSize(new Point());
                    onNewAspectRatioSelected(this.aspectRatio);
                    this.queShotGridView.setAspectRatio(this.aspectRatio);
                    this.queShotGridView.setLocked(true);
                    this.queShotGridView.setTouchEnable(true);
                    return;
                case TEXT:
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraintLayoutAddText.setVisibility(View.GONE);
                    this.constraintLayoutSaveText.setVisibility(View.GONE);
                    if (!this.queShotGridView.getStickers().isEmpty()) {
                        this.queShotGridView.getStickers().clear();
                        this.queShotGridView.setHandlingSticker(null);
                    }
                    this.moduleToolsId = Module.NONE;
                    this.relativeLayoutAddText.setVisibility(View.GONE);
                    this.queShotGridView.setHandlingSticker(null);
                    setVisibleSave();
                    this.queShotGridView.setLocked(true);
                    this.queShotGridView.setTouchEnable(true);
                    return;
                case FILTER:
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraint_layout_filter_layout.setVisibility(View.GONE);
                    this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                    this.queShotGridView.setLocked(true);
                    this.queShotGridView.setTouchEnable(true);
                    for (int i = 0; i < this.drawableList.size(); i++) {
                        this.queShotGridView.getQueShotGrids().get(i).setDrawable(this.drawableList.get(i));
                    }
                    this.queShotGridView.invalidate();
                    setVisibleSave();
                    this.moduleToolsId = Module.NONE;
                    return;
                case STICKER:
                    setGuideLineTools();
                    this.constraintLayoutSaveSticker.setVisibility(View.GONE);
                    if (this.queShotGridView.getStickers().size() <= 0) {
                        this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                        this.imageViewAddSticker.setVisibility(View.GONE);
                        this.queShotGridView.setHandlingSticker((Sticker) null);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        this.constraint_layout_sticker.setVisibility(View.GONE);
                        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                        this.queShotGridView.setLocked(true);
                        this.moduleToolsId = Module.NONE;
                    } else if (this.imageViewAddSticker.getVisibility() == View.VISIBLE) {
                        this.queShotGridView.getStickers().clear();
                        this.imageViewAddSticker.setVisibility(View.GONE);
                        this.queShotGridView.setHandlingSticker(null);
                        this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        this.constraint_layout_sticker.setVisibility(View.GONE);
                        this.queShotGridView.setLocked(true);
                        this.queShotGridView.setTouchEnable(true);
                        this.moduleToolsId = Module.NONE;
                    } else {
                        this.linear_layout_wrapper_sticker_list.setVisibility(View.GONE);
                        this.imageViewAddSticker.setVisibility(View.VISIBLE);
                        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                    }
                    this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraint_layout_sticker.setVisibility(View.GONE);
                    setVisibleSave();
                    return;
                case GRADIENT:
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraint_layout_change_background.setVisibility(View.GONE);
                    this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                    this.queShotGridView.setLocked(true);
                    this.queShotGridView.setTouchEnable(true);
                    if (this.currentBackgroundState.isColor) {
                        this.queShotGridView.setBackgroundResourceMode(0);
                        this.queShotGridView.setBackgroundColor(this.currentBackgroundState.drawableId);
                    } else if (this.currentBackgroundState.isBitmap) {
                        this.queShotGridView.setBackgroundResourceMode(2);
                        this.queShotGridView.setBackground(this.currentBackgroundState.drawable);
                    } else {
                        this.queShotGridView.setBackgroundResourceMode(1);
                        if (this.currentBackgroundState.drawable != null) {
                            this.queShotGridView.setBackground(this.currentBackgroundState.drawable);
                        } else {
                            this.queShotGridView.setBackgroundResource(this.currentBackgroundState.drawableId);
                        }
                    }
                    setVisibleSave();
                    this.moduleToolsId = Module.NONE;
                    return;
                case COLLAGE:
                    setVisibleSave();
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.recyclerViewToolsCollage.setVisibility(View.GONE);
                    this.moduleToolsId = Module.NONE;
                    this.queShotGridView.setQueShotGrid(null);
                    this.queShotGridView.setPrevHandlingQueShotGrid(null);
                    this.queShotGridView.invalidate();
                    this.moduleToolsId = Module.NONE;
                    return;
                case NONE:
                    setOnBackPressDialog();
                    return;
                default:
                    super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onItemClick(PolishLayout puzzleLayout2, int i) {
        PolishLayout parse = PolishLayoutParser.parse(puzzleLayout2.generateInfo());
        puzzleLayout2.setRadian(this.queShotGridView.getCollageRadian());
        puzzleLayout2.setPadding(this.queShotGridView.getCollagePadding());
        this.queShotGridView.updateLayout(parse);
    }

    public void onNewAspectRatioSelected(AspectRatio aspectRatio) {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int[] calculateWidthAndHeight = calculateWidthAndHeight(aspectRatio, point);
        this.queShotGridView.setLayoutParams(new ConstraintLayout.LayoutParams(calculateWidthAndHeight[0], calculateWidthAndHeight[1]));
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.constraint_layout_wrapper_collage_view);
        constraintSet.connect(this.queShotGridView.getId(), 3, this.constraint_layout_wrapper_collage_view.getId(), 3, 0);
        constraintSet.connect(this.queShotGridView.getId(), 1, this.constraint_layout_wrapper_collage_view.getId(), 1, 0);
        constraintSet.connect(this.queShotGridView.getId(), 4, this.constraint_layout_wrapper_collage_view.getId(), 4, 0);
        constraintSet.connect(this.queShotGridView.getId(), 2, this.constraint_layout_wrapper_collage_view.getId(), 2, 0);
        constraintSet.applyTo(this.constraint_layout_wrapper_collage_view);
        this.queShotGridView.setAspectRatio(aspectRatio);
    }

    public void replaceCurrentPiece(String str) {
        new OnLoadBitmapFromUri().execute(str);
    }

    private int[] calculateWidthAndHeight(AspectRatio aspectRatio, Point point) {
        int height = this.constraint_layout_wrapper_collage_view.getHeight();
        if (aspectRatio.getHeight() > aspectRatio.getWidth()) {
            int ratio = (int) (aspectRatio.getRatio() * ((float) height));
            if (ratio < point.x) {
                return new int[]{ratio, height};
            }
            return new int[]{point.x, (int) (((float) point.x) / aspectRatio.getRatio())};
        }
        int ratio2 = (int) (((float) point.x) / aspectRatio.getRatio());
        if (ratio2 > height) {
            return new int[]{(int) (((float) height) * aspectRatio.getRatio()), height};
        }
        return new int[]{point.x, ratio2};
    }

    public void addSticker(int item, Bitmap bitmap) {
        this.queShotGridView.addSticker(new DrawableSticker(new BitmapDrawable(getResources(), bitmap)));
        this.linear_layout_wrapper_sticker_list.setVisibility(View.GONE);
        this.imageViewAddSticker.setVisibility(View.VISIBLE);
    }

    public void onBackgroundSelected(int item, final CollageBackgroundAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.queShotGridView.setBackgroundColor(squareView.drawableId);
            this.queShotGridView.setBackgroundResourceMode(0);
        } else if (squareView.drawable != null) {
            this.queShotGridView.setBackgroundResourceMode(2);
            new AsyncTask<Void, Bitmap, Bitmap>() {

                public Bitmap doInBackground(Void... voidArr) {
                    return FilterUtils.getBlurImageFromBitmap(((BitmapDrawable) squareView.drawable).getBitmap(), 5.0f);
                }


                public void onPostExecute(Bitmap bitmap) {
                    MKMCollageActivity.this.queShotGridView.setBackground(new BitmapDrawable(MKMCollageActivity.this.getResources(), bitmap));
                }
            }.execute();
        } else {
            this.queShotGridView.setBackgroundResource(squareView.drawableId);
            this.queShotGridView.setBackgroundResourceMode(1);
            MKM.number_gradient = squareView.drawableId;
            MKM.number_color = 0;
            Log.d("fewefw", "id1: " + MKM.number_gradient);
        }
    }

    public void onBackgroundColorSelected(int item, CollageColorAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.queShotGridView.setBackgroundColor(squareView.drawableId);
            this.queShotGridView.setBackgroundResourceMode(0);
            MKM.number_color = squareView.drawableId;
            MKM.number_gradient = 0;
            Log.d("fewefw", "id2: " + MKM.number_color);
        }
    }

    public void onFilterSelected(int item, String str) {
        new LoadBitmapWithFilter().execute(new String[]{str});

    }

    public void finishCrop(Bitmap bitmap) {
        this.queShotGridView.replace(bitmap, "");
    }

    public void onSaveFilter(Bitmap bitmap) {
        this.queShotGridView.replace(bitmap, "");
    }

    @Override
    public void onPieceFuncSelected(Module toolType) {
        switch (toolType) {
            case REPLACE:
                MKMPickerView.builder().setPhotoCount(1).setPreviewEnabled(false).setShowCamera(false).setForwardMain(true).start(this);
                return;
            case H_FLIP:
                this.queShotGridView.flipHorizontally();
                return;
            case V_FLIP:
                this.queShotGridView.flipVertically();
                return;
            case ROTATE:
                this.queShotGridView.rotate(90.0f);
                return;
            case CROP:
                CropFragment.show(this, this, ((BitmapDrawable) this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap());
                return;
            case FILTER:
                new LoadFilterBitmapForCurrentPiece().execute();
                return;
        }
    }

    class allFilters extends AsyncTask<Void, Void, Void> {
        allFilters() {
        }

        public void onPreExecute() {
            MKMCollageActivity.this.setLoading(true);
        }

        @SuppressLint("WrongThread")
        public Void doInBackground(Void... voidArr) {
            MKMCollageActivity.this.listFilterAll.clear();
            MKMCollageActivity.this.listFilterAll.addAll(FilterFileAsset.getListBitmapFilter(ThumbnailUtils.extractThumbnail(((BitmapDrawable) MKMCollageActivity.this.queShotGridView.getQueShotGrids().get(0).getDrawable()).getBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voidR) {
            MKMCollageActivity.this.recyclerViewFilter.setAdapter(new FilterAdapter(MKMCollageActivity.this.listFilterAll, MKMCollageActivity.this, MKMCollageActivity.this.getApplicationContext(), Arrays.asList(FilterFileAsset.FILTERS)));
            MKMCollageActivity.this.recyclerViewToolsCollage.setVisibility(View.GONE);
            MKMCollageActivity.this.queShotGridView.setLocked(false);
            MKMCollageActivity.this.queShotGridView.setTouchEnable(false);
            setGuideLine();
            MKMCollageActivity.this.constraint_layout_filter_layout.setVisibility(View.VISIBLE);
            MKMCollageActivity.this.recyclerViewTools.setVisibility(View.GONE);
            MKMCollageActivity.this.setLoading(false);

            FilterAdapter.filterListener.onFilterSelected(0,((FilterFileAsset.FiltersCode) FilterAdapter.filterBeanList.get(0)).getCode());
        }
    }

    class LoadFilterBitmapForCurrentPiece extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        LoadFilterBitmapForCurrentPiece() {
        }

        public void onPreExecute() {
            MKMCollageActivity.this.setLoading(true);
        }

        @SuppressLint("WrongThread")
        public List<Bitmap> doInBackground(Void... voidArr) {
            return FilterFileAsset.getListBitmapFilter(ThumbnailUtils.extractThumbnail(((BitmapDrawable) MKMCollageActivity.this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), 100, 100));
        }

        public void onPostExecute(List<Bitmap> list) {
            MKMCollageActivity.this.setLoading(false);
            if (MKMCollageActivity.this.queShotGridView.getQueShotGrid() != null) {
                FilterFragment.show(MKMCollageActivity.this, MKMCollageActivity.this, ((BitmapDrawable) MKMCollageActivity.this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), list);
            }
        }
    }

    class LoadBitmapWithFilter extends AsyncTask<String, List<Bitmap>, List<Bitmap>> {
        LoadBitmapWithFilter() {
        }

        public void onPreExecute() {
            MKMCollageActivity.this.setLoading(true);
        }

        public List<Bitmap> doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            for (Drawable drawable : MKMCollageActivity.this.drawableList) {
                arrayList.add(FilterUtils.getBitmapWithFilter(((BitmapDrawable) drawable).getBitmap(), strArr[0]));
            }
            return arrayList;
        }


        public void onPostExecute(List<Bitmap> list) {
            for (int i = 0; i < list.size(); i++) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(MKMCollageActivity.this.getResources(), list.get(i));
                bitmapDrawable.setAntiAlias(true);
                bitmapDrawable.setFilterBitmap(true);
                MKMCollageActivity.this.queShotGridView.getQueShotGrids().get(i).setDrawable(bitmapDrawable);
            }
            MKMCollageActivity.this.queShotGridView.invalidate();
            MKMCollageActivity.this.setLoading(false);
        }
    }

    class OnLoadBitmapFromUri extends AsyncTask<String, Bitmap, Bitmap> {
        OnLoadBitmapFromUri() {
        }

        public void onPreExecute() {
            MKMCollageActivity.this.setLoading(true);
        }

        public Bitmap doInBackground(String... strArr) {
            try {
                Uri fromFile = Uri.fromFile(new File(strArr[0]));

                Bitmap rotateBitmap = SystemUtil.rotateBitmap(MediaStore.Images.Media.getBitmap(MKMCollageActivity.this.getContentResolver(), fromFile), new ExifInterface(MKMCollageActivity.this.getContentResolver().openInputStream(fromFile)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1));

                float width = (float) rotateBitmap.getWidth();
                float height = (float) rotateBitmap.getHeight();
                float max = Math.max(width / 1280.0f, height / 1280.0f);
                return max > 1.0f ? Bitmap.createScaledBitmap(rotateBitmap, (int) (width / max), (int) (height / max), false) : rotateBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(Bitmap bitmap) {
            MKMCollageActivity.this.setLoading(false);
            MKMCollageActivity.this.queShotGridView.replace(bitmap, "");
        }
    }


    class SaveCollageAsFile extends AsyncTask<Bitmap, String, String> {
        SaveCollageAsFile() {
        }

        public void onPreExecute() {
            MKMCollageActivity.this.setLoading(true);
        }

        public String doInBackground(Bitmap... bitmapArr) {
            Bitmap bitmap = bitmapArr[0];
            Bitmap bitmap2 = bitmapArr[1];
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = null;
            canvas.drawBitmap(bitmap, (Rect) null, new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), paint);
            canvas.drawBitmap(bitmap2, (Rect) null, new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), paint);
            bitmap.recycle();
            bitmap2.recycle();
            try {
                File image = SaveFileUtils.saveBitmapFileCollage(MKMCollageActivity.this, createBitmap, new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date()),null);
                createBitmap.recycle();
                return image.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(String str) {

            DataRecent.getInstance(getApplicationContext()).daoSql().insert(new Model_recent(
                    str, System.currentTimeMillis()
            ));

            MKMCollageActivity.this.setLoading(false);
            Intent intent = new Intent(MKMCollageActivity.this, PhotoShareActivity.class);
            intent.putExtra("intent", "collage");
            intent.putExtra("path", str);
            intent.putExtra("number", MKM.number_layout);
            MKMCollageActivity.this.startActivity(intent);
        }
    }

    public void setLoading(boolean z) {
        if (z) {
            getWindow().setFlags(16, 16);
            this.relativeLayoutLoading.setVisibility(View.VISIBLE);
            return;
        }
        getWindow().clearFlags(16);
        this.relativeLayoutLoading.setVisibility(View.GONE);
    }
}