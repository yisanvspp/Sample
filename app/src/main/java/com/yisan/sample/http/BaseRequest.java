package com.yisan.sample.http;

import androidx.annotation.NonNull;

import com.yisan.base.receiver.LoginReceiver;
import com.yisan.sample.api.WanApi;
import com.yisan.sample.api.WanResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import per.goweii.rxhttp.core.RxLife;
import per.goweii.rxhttp.request.RxRequest;

/**
 * 网络请求封装
 *
 * @author wzh
 * @packageName com.yisan.sample.http
 * @fileName BaseRequest.java
 * @date 2019-12-04  上午 11:11
 */
public class BaseRequest {


    /**
     * 该请求对未登录的返回做统一处理
     *
     * @param rxLife     生命周期管理
     * @param observable Observable
     * @param listener   RequestListener
     * @param <T>        T
     */
    protected static <T> void request(
            @NonNull RxLife rxLife,
            @NonNull Observable<WanResponse<T>> observable,
            @NonNull RequestListener<T> listener) {

        rxLife.add(RxRequest.create(observable).request(new RxRequest.ResultCallback<T>() {
            @Override
            public void onStart(Disposable d) {
                listener.onStart(d);
            }

            @Override
            public void onSuccess(int code, T data) {
                listener.onSuccess(code, data);
            }

            @Override
            public void onError(int code, String msg) {
                //未登录的统一处理
                if (code == WanApi.ApiCode.FAILED_NOT_LOGIN) {
                    // TODO: 2019/12/4  退出登录根据自己的业务处理进行处理
                    // UserUtils.getInstance().logout();
                    LoginReceiver.sendNotLogin();
                }
                listener.onError(code, msg);
            }

            @Override
            public void onFinish() {
                listener.onFinish();
            }

        }));

    }

    /**
     * 与上面方法多了ResponseToCache参数：返回true时候、数据进行缓存、false不缓存
     *
     * @param rxLife          生命周期管理
     * @param observable      Observable
     * @param listener        RequestListener
     * @param responseToCache ResponseToCache
     * @param <T>             T
     */
    protected static <T> void request(
            @NonNull RxLife rxLife,
            @NonNull Observable<WanResponse<T>> observable,
            @NonNull RequestListener<T> listener,
            ResponseToCache<T> responseToCache) {


        rxLife.add(RxRequest.create(observable).request(new RxRequest.ResultCallback<T>() {
            @Override
            public void onStart(Disposable d) {
                listener.onStart(d);
            }

            @Override
            public void onSuccess(int code, T data) {

                if (responseToCache.onResponse(data)) {
                    //改请求保存到本地缓存中
                    listener.onSuccess(code, data);
                }
            }

            @Override
            public void onError(int code, String msg) {
                if (code == WanApi.ApiCode.FAILED_NOT_LOGIN) {
//                            UserUtils.getInstance().logout();
                    LoginReceiver.sendNotLogin();
                }
                listener.onError(code, msg);
            }

            @Override
            public void onFinish() {
                listener.onFinish();
            }
        }));
    }


    /**
     * 获取缓存的bean对象 、不进行网络请求
     *
     * @param rxLife   生命周期管理
     * @param key      get或者post请求，后面带着的地址
     * @param clazz    需要的bean对象
     * @param listener RequestListener
     * @param <T>      T
     */
    protected static <T> void cacheBean(
            RxLife rxLife,
            String key,
            Class<T> clazz,
            RequestListener<T> listener) {

        rxLife.add(WanCache.getInstance().getBean(key, clazz, new CacheListener<T>() {
            @Override
            public void onSuccess(int code, T data) {
                listener.onSuccess(code, data);
            }

            @Override
            public void onFailed() {
                listener.onError(WanApi.ApiCode.FAILED_NO_CACHE, "");
            }
        }));


    }

    /**
     * 获取缓存的集合对象 - 不进行网络请求
     *
     * @param rxLife   生命周期管理
     * @param key      get或者post请求，后面带着的地址
     * @param clazz    需要的bean对象
     * @param listener RequestListener
     * @param <T>      T
     */
    protected static <T> void cacheList(
            RxLife rxLife,
            String key,
            Class<T> clazz,
            RequestListener<List<T>> listener) {

        rxLife.add(WanCache.getInstance().getList(key, clazz, new CacheListener<List<T>>() {
            @Override
            public void onSuccess(int code, final List<T> data) {
                listener.onSuccess(code, data);
            }

            @Override
            public void onFailed() {
                listener.onError(WanApi.ApiCode.FAILED_NO_CACHE, "");
            }
        }));
    }


    /**
     * 获取缓存的集合对象 - 并且进行网络请求获取最新数据
     *
     * @param rxLife     生命周期管理
     * @param observable Observable
     * @param key        get或者post请求，后面带着的地址
     * @param clazz      需要的bean对象
     * @param listener   RequestListener
     * @param <T>        T
     */

    protected static <T> void cacheAndNetList(
            RxLife rxLife,
            Observable<WanResponse<List<T>>> observable,
            String key,
            Class<T> clazz,
            RequestListener<List<T>> listener) {

        cacheAndNetList(rxLife, observable, false, key, clazz, listener);
    }


    /**
     * 核心方法：
     * 如果 refresh等于true 那么请求网络并且更新本地缓存、反之从本地缓存中获取数据
     *
     * @param rxLife         生命周期管理
     * @param observable     Observable
     * @param noUseCacheData 是否使用缓存数据
     * @param key            get或者post请求，后面带着的地址
     * @param clazz          需要的bean对象
     * @param listener       RequestListener
     * @param <T>            T
     */
    protected static <T> void cacheAndNetList(

            RxLife rxLife,
            Observable<WanResponse<List<T>>> observable,
            boolean noUseCacheData,
            String key,
            Class<T> clazz,
            RequestListener<List<T>> listener) {


        if (noUseCacheData) {

            //请求网络
            request(rxLife, observable, listener, resp -> {
                //返回true表示必须保存到缓存、才会执行listener.onSuccess();
                WanCache.getInstance().save(key, resp);

                return true;
            });

            return;
        }


        //如果refresh为false从本地缓存中取数据
        rxLife.add(WanCache.getInstance().getList(key, clazz, new CacheListener<List<T>>() {
            @Override
            public void onSuccess(int code, final List<T> data) {

                listener.onSuccess(code, data);

                //成功获取到缓存数据后，请求网络获取最新的数据
                request(rxLife, observable, listener, new ResponseToCache<List<T>>() {
                    @Override
                    public boolean onResponse(List<T> resp) {
                        //缓存的数据与最后的数据进行对比
                        if (WanCache.getInstance().isSame(data, resp)) {
                            //相同的话、就不保存到本地缓存中
                            return false;
                        }
                        //不同的话更新最新数据到缓存
                        WanCache.getInstance().save(key, resp);

                        return true;
                    }
                });
            }

            @Override
            public void onFailed() {

                //从缓存数据、没有获取到数据、那么就从网络获取
                request(rxLife, observable, listener, new ResponseToCache<List<T>>() {
                    @Override
                    public boolean onResponse(List<T> resp) {
                        //并且进行缓存
                        WanCache.getInstance().save(key, resp);

                        return true;
                    }
                });
            }
        }));


    }

    /**
     * 同上
     *
     * @param rxLife
     * @param observable
     * @param key
     * @param clazz
     * @param listener
     * @param <T>
     */
    protected static <T> void cacheAndNetBean(
            RxLife rxLife,
            Observable<WanResponse<T>> observable,
            String key,
            Class<T> clazz,
            RequestListener<T> listener) {
        cacheAndNetBean(rxLife, observable, false, key, clazz, listener);
    }


    /**
     * 同上
     *
     * @param rxLife
     * @param observable
     * @param noUseCacheData
     * @param key
     * @param clazz
     * @param listener
     * @param <T>
     */
    protected static <T> void cacheAndNetBean(

            RxLife rxLife,
            Observable<WanResponse<T>> observable,
            boolean noUseCacheData,
            String key,
            Class<T> clazz,
            RequestListener<T> listener) {

        if (noUseCacheData) {

            request(rxLife, observable, listener, resp -> {

                WanCache.getInstance().save(key, resp);
                return true;

            });
            return;
        }
        rxLife.add(WanCache.getInstance().getBean(key, clazz, new CacheListener<T>() {
            @Override
            public void onSuccess(int code, T data) {

                listener.onSuccess(code, data);

                request(rxLife, observable, listener, resp -> {

                    if (WanCache.getInstance().isSame(data, resp)) {
                        return false;
                    }
                    WanCache.getInstance().save(key, resp);

                    return true;
                });
            }

            @Override
            public void onFailed() {

                request(rxLife, observable, listener, resp -> {

                    WanCache.getInstance().save(key, resp);

                    return true;
                });
            }
        }));
    }


    /**
     * 判定是否需要缓存
     *
     * @param <T>
     */
    public interface ResponseToCache<T> {
        boolean onResponse(T resp);
    }

}
