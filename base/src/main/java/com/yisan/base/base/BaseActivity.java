package com.yisan.base.base;

import android.os.Bundle;

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

}
