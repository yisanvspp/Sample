package com.yisan.base.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.mvp.view.LifeCircleMvpActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author：wzh
 * @description: activity基类
 * @packageName: com.yisan.base
 * @date：2019/11/24 0024 下午 1:43
 */
public abstract class BaseActivity extends LifeCircleMvpActivity {


    private Unbinder bind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isUseEventBus()) {
            //方法必须public才能进行注册eventbus
            EventBus.getDefault().register(this);
        }

        if (needTransparentStatusBar()) {
            changeStatusBarToTransparent();
        }


        ViewLayoutInject annotation = this.getClass().getAnnotation(ViewLayoutInject.class);
        if (annotation != null) {

            int layoutId = annotation.value();
            if (layoutId > 0) {
                setContentView(layoutId);
                bindView();
                afterBindView();
            } else {
                throw new RuntimeException("layoutId is null ,please check layoutId");
            }

        } else {
            throw new RuntimeException("ViewLayoutInject is null , layoutId is null");
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bind != null) {
            bind.unbind();
        }

        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }


    }

    /**
     * 是否使用EventBus
     *
     * @return boolean
     */
    protected boolean isUseEventBus() {

        return false;
    }


    private void bindView() {
        //butterKnife必须在setContentView方法之后执行-或者重写setContentView方法
        bind = ButterKnife.bind(this);
    }

    /**
     * 绑定控件之后执行
     */
    protected abstract void afterBindView();


    protected Context getContext() {
        return getActivity();
    }

    protected Activity getActivity() {
        return this;
    }


    /**
     * 是否设置透明状态栏
     */
    protected boolean needTransparentStatusBar() {
        return true;
    }

    /**
     * 设置透明状态栏
     */
    private void changeStatusBarToTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
