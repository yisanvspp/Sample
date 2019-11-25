package com.yisan.base.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yisan.base.mvp.IMvpView;
import com.yisan.base.mvp.presenter.AbstractLifeCircleMvpPresenter;

/**
 * @author：wzh
 * @description: P层的中间层- 子类集成不必要重写那么多方法
 * @packageName: com.yisan.base.mvp.base
 * @date：2019/11/25 0025 上午 10:49
 */
public abstract class AbstractBaseMvpPresenter<T extends IMvpView> extends AbstractLifeCircleMvpPresenter<T> {


    public AbstractBaseMvpPresenter(T view) {
        super(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, Intent intent, Bundle bundle) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState, Intent intent, Bundle bundle) {

    }

    @Override
    public void onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
