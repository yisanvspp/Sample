package com.yisan.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yisan.base.BaseActivity;
import com.yisan.base.annotation.ViewLayoutInject;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
