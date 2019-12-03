package com.yisan.http;

import android.content.Context;

import com.yisan.http.request.RxRequest;
import com.yisan.http.request.bean.BaseResponse;
import com.yisan.http.request.setting.RequestSetting;

import io.reactivex.Observable;

/**
 * @author：wzh
 * @description: 网络库入口
 * @packageName: com.yisan.http
 * @date：2019/11/30 0030 下午 5:31
 */
public class RxHttp {

    private static RxHttp rxHttp;
    private Context mApplicationContext;
    private RequestSetting mRequestSetting = null;

    private RxHttp() {
    }

    public static RxHttp getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 必须调用该方法
     *
     * @param context Context
     */
    public static void init(Context context) {

        getInstance().mApplicationContext = context.getApplicationContext();

        rxHttp = getInstance();

    }

    /**
     * 获取全局的上下文
     *
     * @return Context
     */
    public static Context getAppContext() {
        return getInstance().mApplicationContext;
    }

    /**
     * 初始化请求参数
     *
     * @param requestSetting RequestSetting
     */
    public static void initRequestSetting(RequestSetting requestSetting) {

        getInstance().mRequestSetting = requestSetting;

    }

    public static RequestSetting getRequestSetting() {
        RequestSetting mRequestSetting = getInstance().mRequestSetting;
        if (mRequestSetting == null) {
            throw new RuntimeException("RequestSetting is null");
        }
        return mRequestSetting;
    }

    /**
     * 发起请求
     *
     * @param observable api接口数据的observable
     * @param requestUrl 请求的url - baseUrl 后面跟的那段地址
     * @return RxRequest
     */
    public static <T, R extends BaseResponse<T>> RxRequest<T, R> request(Observable<R> observable, String requestUrl) {
        return RxRequest.create(observable, requestUrl);
    }

    private static class SingletonHolder {
        private static final RxHttp INSTANCE = new RxHttp();
    }

}
