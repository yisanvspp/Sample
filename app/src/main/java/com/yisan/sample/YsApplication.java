package com.yisan.sample;

import android.app.Application;

import com.zhxu.library.utils.ContextUtils;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample
 * @date：2019/11/28 0028 下午 9:39
 */
public class YsApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        ContextUtils.init(this);

    }
}
