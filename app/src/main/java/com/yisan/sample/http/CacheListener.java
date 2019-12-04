package com.yisan.sample.http;

/**
 * 缓存监听
 *
 * @author wzh
 * @packageName com.yisan.sample.http
 * @fileName CacheListener.java
 * @date 2019-12-04  上午 11:11
 */
public interface CacheListener<E> {
    void onSuccess(int code, E data);

    void onFailed();
}
