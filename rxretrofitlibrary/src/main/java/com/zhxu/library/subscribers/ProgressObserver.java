package com.zhxu.library.subscribers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.yisan.mvp.RxLife;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.exception.HttpTimeException;
import com.zhxu.library.http.cookie.CookieResulte;
import com.zhxu.library.listener.HttpOnNextListener;
import com.zhxu.library.utils.AppUtil;
import com.zhxu.library.utils.ContextUtils;
import com.zhxu.library.utils.CookieDbUtil;

import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressObserver<T> implements Observer<T> {

    /**
     * 是否弹框
     */
    private boolean showPorgress = true;
    /**
     * 软引用回调接口
     */
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;
    /**
     * 软引用反正内存泄露
     */
    private SoftReference<RxLife> mRxLifeSoftReference;
    /**
     * 加载框可自己定义
     */
    private ProgressDialog pd;
    /**
     * 请求数据
     */
    private BaseApi api;

    /**
     * 订阅
     */
    private Disposable disposable;

    /**
     * 构造
     *
     * @param api
     */
    public ProgressObserver(BaseApi api) {
        this.api = api;
        this.mSubscriberOnNextListener = api.getListener();
        this.mRxLifeSoftReference = new SoftReference<>(api.getRxLife());
        setShowPorgress(api.isShowProgress());
        if (api.isShowProgress()) {
            initProgressDialog(api.isCancel());
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

        mRxLifeSoftReference.get().addDisposable(d);

        showProgressDialog();
        /*缓存并且有网*/
        if (api.isCache() && AppUtil.isNetworkAvailable(ContextUtils.getAppContext())) {
            /*获取缓存数据*/
            CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(api.getUrl());
            if (cookieResulte != null) {
                long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                if (time < api.getCookieNetWorkTime()) {
                    if (mSubscriberOnNextListener.get() != null) {
                        mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte());
                    }
                    onComplete();
                    if (disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        /*需要緩存并且本地有缓存才返回*/
        if (api.isCache()) {
            Observable.just(api.getUrl()).subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    /**获取缓存数据*/
                    CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(s);
                    if (cookieResulte == null) {
                        throw new HttpTimeException("网络错误");
                    }
                    long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                    if (time < api.getCookieNoNetWorkTime()) {
                        if (mSubscriberOnNextListener.get() != null) {
                            mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte());
                        }
                    } else {
                        CookieDbUtil.getInstance().deleteCookie(cookieResulte);
                        throw new HttpTimeException("网络错误");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    errorDo(e);
                }

                @Override
                public void onComplete() {

                }
            });

        } else {
            errorDo(e);
        }
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }


    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        Context context = mRxLifeSoftReference.get().getActivityContext();
        if (pd == null && context != null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (mSubscriberOnNextListener.get() != null) {
                            mSubscriberOnNextListener.get().onCancel();
                        }
                        onCancelProgress();
                    }
                });
            }
        }
    }


    /**
     * 错误统一处理
     */
    private void errorDo(Throwable e) {
        Context context = mRxLifeSoftReference.get().getActivityContext();
        if (context == null) {
            return;
        }
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(e);
        }
    }


    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!isShowPorgress()) {
            return;
        }
        Context context = mRxLifeSoftReference.get().getActivityContext();
        if (pd == null || context == null) {
            return;
        }
        if (!pd.isShowing()) {
            pd.show();
        }
    }


    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (!isShowPorgress()) {
            return;
        }
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }


    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    public boolean isShowPorgress() {
        return showPorgress;
    }

    /**
     * 是否需要弹框设置
     *
     * @param showPorgress
     */
    public void setShowPorgress(boolean showPorgress) {
        this.showPorgress = showPorgress;
    }


}