package com.yisan.base.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample.mvp
 * @date：2019/11/25 0025 上午 11:46
 */
public class MvpControler implements ILifeCircle {

    /**
     * 存放的是P层的实例
     */
    private Set<ILifeCircle> lifeCircles = new HashSet<>();

    private MvpControler() {
    }

    public static MvpControler newInstance() {
        return new MvpControler();
    }


    /**
     * 保存P层的实例
     *
     * @param iLifeCircle Class Presenter implement ILifeCircle
     */
    public void savePresenter(ILifeCircle iLifeCircle) {
        lifeCircles.add(iLifeCircle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, Intent intent, Bundle bundle) {

        for (ILifeCircle lifeCircle : lifeCircles) {
            if (intent == null) {
                intent = new Intent();
            }

            if (bundle == null) {
                bundle = new Bundle();
            }

            lifeCircle.onCreate(savedInstanceState, intent, bundle);
        }
    }

    @Override
    public void onStart() {
        for (ILifeCircle lifeCircle : lifeCircles) {
            lifeCircle.onStart();
        }
    }

    @Override
    public void onResume() {
        for (ILifeCircle lifeCircle : lifeCircles) {
            lifeCircle.onResume();
        }
    }

    @Override
    public void onPause() {
        for (ILifeCircle lifeCircle : lifeCircles) {
            lifeCircle.onPause();
        }
    }

    @Override
    public void onStop() {
        for (ILifeCircle lifeCircle : lifeCircles) {
            lifeCircle.onStop();
        }
    }

    @Override
    public void onDestroy() {
        for (ILifeCircle lifeCircle : lifeCircles) {
            lifeCircle.onDestroy();
        }
    }

    @Override
    public void onAttach(IMvpView view) {
        for (ILifeCircle lifeCircle : lifeCircles) {
            lifeCircle.onAttach(view);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState, Intent intent, Bundle bundle) {
        for (ILifeCircle lifeCircle : lifeCircles) {
            if (intent == null) {
                intent = new Intent();
            }
            if (bundle == null) {
                bundle = new Bundle();
            }
            lifeCircle.onActivityCreated(savedInstanceState, intent, bundle);
        }
    }

    @Override
    public void onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        for (ILifeCircle lifeCircle : lifeCircles) {
            if (savedInstanceState == null) {
                savedInstanceState = new Bundle();
            }
            lifeCircle.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onDestroyView() {
        for (ILifeCircle lifeCircle : lifeCircles) {
            lifeCircle.onDestroyView();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        for (ILifeCircle lifeCircle : lifeCircles) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            lifeCircle.onSaveInstanceState(bundle);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        for (ILifeCircle lifeCircle : lifeCircles) {
            if (data == null) {
                data = new Intent();
            }
            lifeCircle.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        for (ILifeCircle lifeCircle : lifeCircles) {
            lifeCircle.onNewIntent(intent);
        }
    }


}
