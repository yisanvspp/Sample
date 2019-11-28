package com.yisan.mvp;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.mvp
 * @date：2019/11/28 0028 下午 10:45
 */
public interface RxLife {

    CompositeDisposable getCompositeDisposable();

    void addDisposable(Disposable disposable);

    void dispose();

    Context getActivityContext();
}
