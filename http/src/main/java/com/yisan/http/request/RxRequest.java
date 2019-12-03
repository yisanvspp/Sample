package com.yisan.http.request;

import android.content.Context;

import com.yisan.http.RxHttp;
import com.yisan.http.request.bean.BaseResponse;
import com.yisan.http.request.cookie.CookieResult;
import com.yisan.http.request.exception.ApiException;
import com.yisan.http.request.exception.ExceptionHandle;
import com.yisan.http.utils.CookieDbUtil;
import com.yisan.http.utils.NetUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author：wzh
 * @description: 发起请求
 * @packageName: com.yisan.http
 * @date：2019/11/30 0030 下午 9:15
 */
public class RxRequest<T, R extends BaseResponse<T>> {


    private static final String TAG = "RxRequest";

    private final Observable<R> mObservable;
    /**
     * 跟地址
     */
    private final String baseUrl;
    private Disposable disposable;
    private boolean useCache;
    private Context mContext;
    private String mRequestUrl;
    /**
     * 联网时缓存保存的时间
     */
    private int cookieNetWorkTime;


    /**
     * @param observable 请求接口的Observable
     * @param requestUrl OkHttp中的 Request请求对象
     */
    private RxRequest(Observable<R> observable, String requestUrl) {

        this.useCache = RxHttp.getRequestSetting().useCache();

        this.mObservable = observable;

        this.mRequestUrl = requestUrl;

        this.cookieNetWorkTime = RxHttp.getRequestSetting().getCookieNetWorkTime();

        this.mContext = RxHttp.getRequestSetting().getContext();

        this.baseUrl = RxHttp.getRequestSetting().getBaseUrl();

    }

    public static <T, R extends BaseResponse<T>> RxRequest<T, R> create(Observable<R> observable,
                                                                        String requestUrl) {
        return new RxRequest<>(observable, requestUrl);
    }


    /**
     * 请求数据
     *
     * @param callback ResultCallback
     */
    public void request(final ResultCallback<T> callback) {


        mObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        disposable = d;

                        callback.onStart(d);
                        //使用缓存并且联网情况下
                        if (useCache && NetUtils.isConnected()) {
                            //获取缓存数据
                            String url = baseUrl + mRequestUrl;

                            CookieResult cookieResult = CookieDbUtil.getInstance(mContext).queryCookieBy(url);

                            if (cookieResult != null) {
                                //时间差
                                long time = (System.currentTimeMillis() - cookieResult.getTime()) / 1000;

                                if (time < cookieNetWorkTime) {

                                    //回调结果
                                    callback.onCacheSuccess(cookieResult.getResult());
                                    //订阅完成
                                    onComplete();
                                    //取消订阅
                                    disposable.dispose();
                                }
                            }
                        }

                    }

                    @Override
                    public void onNext(R r) {
                        //服务器返回数据后的处理
                        if (!isSuccess(r.getCode())) {
                            //抛给onError统一处理-api错误
                            onError(new ApiException(r.getCode(), r.getMsg()));
                            onComplete();
                        }

                        callback.onSuccess(r.getCode(), r.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof ApiException) {
                            //Api错误的处理
                            ApiException apiException = (ApiException) e;
                            callback.onFailed(apiException.getCode(), apiException.getMsg());
                        } else {
                            //Http异常处理
                            ExceptionHandle handle = new ExceptionHandle();
                            handle.handle(e);
                            callback.onFailed(handle.getCode(), handle.getMsg());
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        if (disposable.isDisposed()) {
                            disposable.dispose();
                        }

                        callback.onFinish();
                    }
                });
    }


    /**
     * 校验返回的code
     *
     * @param code 返回码
     * @return boolean
     */
    private boolean isSuccess(int code) {

        if (code == RxHttp.getRequestSetting().getSuccessCode()) {
            return true;
        }
        int[] codes = RxHttp.getRequestSetting().getMultiSuccessCode();
        if (codes == null || codes.length == 0) {
            return false;
        }
        for (int i : codes) {
            if (code == i) {
                return true;
            }
        }
        return false;
    }

    /**
     * 请求回调接口
     *
     * @param <T>
     */
    public interface ResultCallback<T> {
        /**
         * 开始
         *
         * @param d 也可以在外部取消订阅、但是要先判断是否是订阅的状态
         */
        void onStart(Disposable d);

        /**
         * 请求成功
         *
         * @param code 返回code
         * @param data 数据
         */
        void onSuccess(int code, T data);

        /**
         * 请求失败
         *
         * @param code 错误码
         * @param msg  错误信息
         */
        void onFailed(int code, String msg);


        /**
         * 获取缓存数据成功
         *
         * @param data 数据
         */
        void onCacheSuccess(String data);

        /**
         * 请求结束
         */
        void onFinish();
    }

}
