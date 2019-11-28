package com.zhxu.library.api;


import com.yisan.mvp.RxLife;
import com.zhxu.library.exception.HttpTimeException;
import com.zhxu.library.listener.HttpOnNextListener;

import java.lang.ref.SoftReference;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;


/**
 * 请求统一封装
 *
 * @param <T>
 */
public abstract class BaseApi<T> implements Function<BaseResultEntity<T>, T>/*<ResponseBody, T*/ {
    /**
     * rx生命周期管理
     */
    private SoftReference<RxLife> rxLifeSoftReference;
    /**
     * 回调
     */
    private SoftReference<HttpOnNextListener> listener;
    /**
     * 是否能取消加载框
     */
    private boolean cancel;
    /**
     * 是否显示加载框
     */
    private boolean showProgress;
    /**
     * 是否需要缓存处理
     */
    private boolean cache;
    /**
     * 基础url
     */
//    private String baseUrl = "http://192.168.0.101:8080/";
//    private String baseUrl = "http://112.124.22.238:8081/appstore/";
    private String baseUrl = "https://www.wanandroid.com/";
    /**
     * 方法-如果需要缓存必须设置这个参数；不需要不用設置
     */
    private String mothed;
    /**
     * 超时时间-默认6秒
     */
    private int connectionTime = 6;
    /**
     * 读取超时时间
     */
    private int readTimeOut = 6;
    /**
     * 写入超时时间
     */
    private int writeTimeOut = 6;
    /**
     * 有网情况下的本地缓存时间默认60秒
     */
    private int cookieNetWorkTime = 60;
    /**
     * 无网络的情况下本地缓存时间默认30天
     */
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    /**
     * 失败后retry次数
     */
    private int retryCount = 1;
    /**
     * 失败后retry延迟
     */
    private long retryDelay = 100;
    /**
     * 失败后retry叠加延迟
     */
    private long retryIncreaseDelay = 10;

    public BaseApi(HttpOnNextListener listener, RxLife rxLife) {
        setListener(listener);
        setRxAppCompatActivity(rxLife);
        setShowProgress(false);
        setCache(false);

        setCancel(true);

        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24 * 60 * 60);
    }

    /**
     * 设置参数
     *
     * @param retrofit Retrofit
     * @return Observable
     */
    public abstract Observable getObservable(Retrofit retrofit);


    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public String getMothed() {
        return mothed;
    }

    public void setMothed(String mothed) {
        this.mothed = mothed;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUrl() {
        return baseUrl + mothed;
    }

    public void setRxAppCompatActivity(RxLife rxLife) {
        this.rxLifeSoftReference = new SoftReference(rxLife);
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public SoftReference<HttpOnNextListener> getListener() {
        return listener;
    }

    public void setListener(HttpOnNextListener listener) {
        this.listener = new SoftReference(listener);
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public void setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
    }

    /**
     * 获取当前rx生命周期
     *
     * @return RxActivity
     */
    public RxLife getRxLife() {
        return rxLifeSoftReference.get();
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getWriteTimeOut() {
        return writeTimeOut;
    }

    public void setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }


    @Override
    public T apply(BaseResultEntity<T> httpResult) {
        if (httpResult.errorCode != 0) {
            throw new HttpTimeException(httpResult.errorMsg);
        }
        return httpResult.data;
    }
}