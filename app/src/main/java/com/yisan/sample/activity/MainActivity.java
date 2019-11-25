package com.yisan.sample.activity;

import android.content.Context;
import android.content.Intent;

import com.yisan.base.annotation.ViewLayoutInject;
import com.yisan.base.base.BaseActivity;
import com.yisan.sample.R;

/**
 * 主页
 *
 * @author wzh
 * @packageName com.yisan.sample
 * @fileName MainActivity.java
 * @date 2019-11-24  下午 9:57
 */
@ViewLayoutInject(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    public static void show(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


    @Override
    protected void afterBindView() {

    }
}
