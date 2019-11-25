package com.yisan.base.mvp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yisan.base.mvp.IMvpView;
import com.yisan.base.mvp.MvpControler;


/**
 * @author：wzh
 * @description: View层的基类 :关联V层和P层的生命周期
 * @packageName: com.yisan.sample.mvp.view
 * @date：2019/11/25 0025 上午 11:43
 */
public class LifeCircleMvpActivity extends AppCompatActivity implements IMvpView {


    /**
     * 静态代理类 (目标对象：Activity 、 Presenter)
     */
    private MvpControler mvpControler;

    @Override
    public MvpControler getMvpControler() {
        if (mvpControler == null) {
            mvpControler = MvpControler.newInstance();
        }
        return mvpControler;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
            intent = new Intent();
        }
        MvpControler mvpControler = getMvpControler();
        if (mvpControler != null) {
            mvpControler.onCreate(savedInstanceState, intent, null);
            mvpControler.onActivityCreated(savedInstanceState, intent, null);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        MvpControler mvpControler = getMvpControler();
        if (mvpControler != null) {
            mvpControler.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MvpControler mvpControler = getMvpControler();
        if (mvpControler != null) {
            mvpControler.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MvpControler mvpControler = getMvpControler();
        if (mvpControler != null) {
            mvpControler.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        MvpControler mvpControler = getMvpControler();
        if (mvpControler != null) {
            mvpControler.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MvpControler mvpControler = getMvpControler();
        if (mvpControler != null) {
            mvpControler.onDestroyView();
            mvpControler.onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MvpControler mvpControler = getMvpControler();
        if (mvpControler != null) {
            mvpControler.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        MvpControler mvpControler = getMvpControler();
        if (mvpControler != null) {
            mvpControler.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MvpControler mvpControler = getMvpControler();
        if (mvpControler != null) {
            mvpControler.onNewIntent(intent);
        }
    }
}
