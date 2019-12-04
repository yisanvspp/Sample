package com.yisan.sample.http;

import io.reactivex.disposables.Disposable;

/**
 * 请求监听
 *
 * @author wzh
 * @packageName com.yisan.sample.http
 * @fileName RequestListener.java
 * @date 2019-12-04  上午 11:12
 */
public interface RequestListener<E> {
    void onStart(Disposable d);

    void onSuccess(int code, E data);

    void onError(int code, String msg);

    void onFinish();
}
