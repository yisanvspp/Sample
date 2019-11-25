package com.yisan.base.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @author：wzh
 * @description: P层与V的生命周期绑定
 * @packageName: com.yisan.sample.mvp.presenter
 * @date：2019/11/25 0025 上午 9:33
 */
public interface ILifeCircle {

    //-----------Activity生命周期

    void onCreate(Bundle savedInstanceState, Intent intent, Bundle bundle);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();


    //-----------Fragment生命周期

    void onAttach(IMvpView view);

    void onActivityCreated(@Nullable Bundle savedInstanceState, Intent intent, Bundle bundle);

    void onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    void onDestroyView();


    //------------其他

    void onSaveInstanceState(Bundle bundle);

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

    void onNewIntent(Intent intent);

}
