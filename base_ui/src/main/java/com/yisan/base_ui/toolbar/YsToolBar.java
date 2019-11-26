package com.yisan.base_ui.toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.yisan.base_ui.R;
import com.yisan.base_ui.statusbar.StatusBarCompat;

/**
 * 自带状态栏颜色设置的toolbar
 *
 * @author：wzh
 * @description:
 * @packageName: com.yisan.base_ui.toolbar
 * @date：2019/11/1 0001 上午 11:08
 */
public class YsToolBar extends FrameLayout {


    private static final int STATUS_BAR_MODE_LIGHT = 0;
    private static final int STATUS_BAR_MODE_DARK = 1;
    /**
     * window全局的状态栏设置 还是当个activity的状态栏设置
     */
    private boolean autoImmersion;
    /**
     * 背景层布局
     */
    private int backgroundLayerLayout;
    /**
     * 背景图片的id
     */
    private int backgroundLayerImageRes;
    /**
     * 状态栏是否显示
     */
    private boolean statusBarVisible;
    /**
     * 状态栏颜色
     */
    private int statusBarColor;
    /**
     * 状态栏模式
     */
    private boolean statusBarMode;
    /**
     * 点击结束界面的控件id
     */
    private int clickToFinish;
    /**
     * toolbar的布局id
     */
    private int toolBarLayout;
    /**
     * toolbar的高度
     */
    private int toolBarHeight;
    /**
     * 下划线的高度
     */
    private int bottomLineHeight;
    /**
     * 下划线的颜色
     */
    private int bottomLineColor;
    /**
     * 下划线的资源id
     */
    private int bottomLineResId;
    /**
     * 下划线是否显示外部
     */
    private boolean bottomLineOutside;

    /**
     * 判断、手机系统获取状态栏高度
     */
    private int statusBarHeight;
    /**
     * 前景布局资源
     */
    private int foregroundLayerLayout;

    /**
     * 背景View
     */
    private View backgroundLayer;
    /**
     * 内容布局
     */
    private LinearLayout contentView;
    /**
     * 状态栏view
     */
    private View statusBarView;
    /**
     * toolbar容器
     */
    private FrameLayout toolBarContains;
    /**
     * 下划线
     */
    private View bottomLine;
    /**
     * 前景view
     */
    private View foregroundLayer;

    /**
     * toolbar
     */
    private View toolBar;


    private SparseArray<View> views = null;


    public YsToolBar(Context context) {
        this(context, null);
    }

    public YsToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YsToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        statusBarHeight = StatusBarCompat.getHeight(context);

        initAttrs(attrs);

        setImmersion();

        setView();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //子布局是否能超出父布局界限
        if (bottomLineOutside) {
            ViewParent parent = getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.setClipChildren(false);
            }
        }
    }


    private void setView() {

        //背景设置
        if (backgroundLayerLayout > 0) {
            backgroundLayer = inflate(getContext(), backgroundLayerLayout, null);
            addViewInLayout(backgroundLayer, getChildCount(), makeLayerLayoutParams(), true);
        } else {
            if (backgroundLayerImageRes > 0) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(backgroundLayerImageRes);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                addViewInLayout(imageView, getChildCount(), makeLayerLayoutParams(), true);
            }
        }

        //初始化ToolBar的布局
        contentView = (LinearLayout) inflate(getContext(), R.layout.content_view, null);
        contentView.setLayoutParams(makeLayoutParamsWithHeight(getContentViewHeight()));

        //状态栏
        statusBarView = contentView.findViewById(R.id.status_bar);
        statusBarView.setLayoutParams(makeLayoutParamsWithHeight(statusBarHeight));
        statusBarView.setBackgroundColor(statusBarColor);
        statusBarView.setVisibility(statusBarVisible ? VISIBLE : GONE);

        //toolbar容器 -- framlayout
        toolBarContains = contentView.findViewById(R.id.tool_bar);
        toolBarContains.setClickable(true);
        toolBarContains.setFocusable(true);
        toolBarContains.setFocusableInTouchMode(true);
        toolBarContains.setLayoutParams(makeLayoutParamsWithHeight(toolBarHeight));

        if (toolBarLayout > 0) {
            //自定义的toolbar布局
            toolBar = inflate(getContext(), toolBarLayout, null);
            toolBarContains.addView(toolBar);
        }

        //下划线
        bottomLine = contentView.findViewById(R.id.bottom_line);
        bottomLine.setLayoutParams(makeLayoutParamsWithHeight(bottomLineHeight));
        if (bottomLineResId > 0) {
            bottomLine.setBackgroundResource(bottomLineResId);
        } else {
            bottomLine.setBackgroundColor(bottomLineColor);
        }


        if (bottomLineOutside) {
            //设置子view布局可以超出父布局的边界
            contentView.setClipChildren(false);
            setClipChildren(false);
        }

        //把内容布局添加到容器
        addViewInLayout(contentView, getChildCount(), makeLayoutParamsWithHeight(getContentViewHeight()), true);

        // 3 初始ForegroundLayer
        if (foregroundLayerLayout > 0) {
            foregroundLayer = inflate(getContext(), foregroundLayerLayout, null);
            addViewInLayout(foregroundLayer, getChildCount(), makeLayerLayoutParams(), true);
        }
        performClickToFinish();
    }


    /**
     * 执行finish按钮
     */
    private void performClickToFinish() {
        if (toolBar == null) {
            return;
        }
        View view = getView(clickToFinish);
        if (view == null) {
            return;
        }
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });

    }

    private void finishActivity() {
        Activity activity = getActivity(getContext());
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }


    private LayoutParams makeLayerLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getContentViewHeight());
    }

    private LinearLayout.LayoutParams makeLayoutParamsWithHeight(int height) {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
    }

    public int getContentViewHeight() {
        if (bottomLineOutside) {
            return getStatusBarHeight() + getToolBarHeight();
        } else {
            return getStatusBarHeight() + getToolBarHeight() + getBottomHeight();
        }
    }

    public int getStatusBarHeight() {
        return statusBarVisible ? statusBarHeight : 0;
    }

    public int getToolBarHeight() {
        return toolBarHeight;
    }

    public int getBottomHeight() {
        return bottomLineHeight;
    }

    /**
     * 设置沉浸式状态栏
     */
    private void setImmersion() {
        hideSystemActionBar();
        refreshStatusBar();
    }

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.YsToolBar);

        try {

            int toolBarHeightDef = getContext().getResources().getDimensionPixelOffset(R.dimen.title_bar_height_def);
            int bottomLineHeightDef = getContext().getResources().getDimensionPixelOffset(R.dimen.bottom_line_height_def);

            int bottomLineColorDef = ContextCompat.getColor(getContext(), R.color.bottom_line_color_def);
            int statusBarColorDef = ContextCompat.getColor(getContext(), R.color.status_bar_color_def);


            autoImmersion = a.getBoolean(R.styleable.YsToolBar_ys_autoImmersion, false);
            backgroundLayerLayout = a.getResourceId(R.styleable.YsToolBar_ys_backgroundLayerLayout, 0);
            backgroundLayerImageRes = a.getResourceId(R.styleable.YsToolBar_ys_backgroundLayerImageRes, 0);
            statusBarVisible = a.getBoolean(R.styleable.YsToolBar_ys_statusBarVisible, true);
            statusBarColor = a.getColor(R.styleable.YsToolBar_ys_statusBarColor, 0);
            statusBarMode = a.getInt(R.styleable.YsToolBar_ys_statusBarMode, STATUS_BAR_MODE_LIGHT) == STATUS_BAR_MODE_DARK;
            clickToFinish = a.getResourceId(R.styleable.YsToolBar_ys_clickToFinish, 0);
            toolBarLayout = a.getResourceId(R.styleable.YsToolBar_ys_toolBarLayout, 0);
            toolBarHeight = (int) a.getDimension(R.styleable.YsToolBar_ys_toolBarHeight, toolBarHeightDef);
            bottomLineHeight = (int) a.getDimension(R.styleable.YsToolBar_ys_bottomLineHeight, bottomLineHeightDef);
            bottomLineColor = a.getColor(R.styleable.YsToolBar_ys_bottomLineColor, bottomLineColorDef);
            bottomLineResId = a.getResourceId(R.styleable.YsToolBar_ys_bottomLineResId, 0);
            bottomLineOutside = a.getBoolean(R.styleable.YsToolBar_ys_bottomLineOutside, false);
            foregroundLayerLayout = a.getResourceId(R.styleable.YsToolBar_ys_foregroundLayerLayout, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.recycle();
        }
    }


    /**
     * 隐藏默认的ActionBar
     */
    private void hideSystemActionBar() {
        Activity activity = getActivity(getContext());
        if (activity == null) {
            return;
        }
        if (activity.getActionBar() != null) {
            activity.getActionBar().hide();
        }
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity compatActivity = (AppCompatActivity) activity;
            if (compatActivity.getSupportActionBar() != null) {
                compatActivity.getSupportActionBar().hide();
            }
        }
    }


    /**
     * 根据上下文获取activity
     *
     * @param context Context
     * @return Activity
     */
    private Activity getActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            if (baseContext instanceof Activity) {
                return (Activity) baseContext;
            }
        }
        return null;
    }


    /**
     * 透明状态栏，改变状态栏图标颜色模式
     */
    public void refreshStatusBar() {
        Activity activity = getActivity(getContext());
        if (activity == null) {
            return;
        }
        StatusBarCompat.setIconMode(activity, statusBarMode);
        if (autoImmersion) {
            StatusBarCompat.transparent(activity);
        } else {
            Window window = activity.getWindow();
            StatusBarCompat.setColor(window, statusBarColor);
        }
    }

    /**
     * 获取View并缓存，以便下次获取，避免频繁调用findViewById
     *
     * @param id View的id
     * @return View
     */
    public <V extends View> V getView(@IdRes int id) {
        if (views == null) {
            views = new SparseArray<>();
        }
        View view = views.get(id);
        if (view == null) {
            view = findViewById(id);
            views.put(id, view);
        }
        return (V) view;
    }

    public View getBackgroundLayer() {
        return backgroundLayer;
    }

    public LinearLayout getContentView() {
        return contentView;
    }

    public View getStatusBar() {
        return statusBarView;
    }

    public FrameLayout getToolBarContains() {
        return toolBarContains;
    }

    public View getToolBar() {
        return toolBar;
    }

    public View getBottomLine() {
        return bottomLine;
    }

    public View getForegroundLayer() {
        return foregroundLayer;
    }

}
