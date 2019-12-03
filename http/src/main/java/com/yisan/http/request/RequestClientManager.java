package com.yisan.http.request;

import android.content.Context;

import com.google.gson.Gson;
import com.yisan.http.RxHttp;
import com.yisan.http.https.SslConnectUtils;
import com.yisan.http.request.interceptor.CookieInterceptor;
import com.yisan.http.request.interceptor.PublicHeadersInterceptor;
import com.yisan.http.request.interceptor.PublicQueryParameterInterceptor;
import com.yisan.http.utils.BaseUrlUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author：wzh
 * @description: OkHttpClient Retrofit管理类
 * @packageName: com.yisan.http
 * @date：2019/11/30 0030 下午 6:37
 */
public class RequestClientManager {

    private Retrofit mRetrofit;
    private Context context;

    private RequestClientManager() {
        mRetrofit = create();
    }

    private static RequestClientManager getInstance() {
        return SingletonHolder.requestClientManager;
    }

    /**
     * 获取api接口实现
     *
     * @param tClass Api.class
     * @param <T>    Api
     * @return Api
     */
    public static <T> T getService(Class<T> tClass) {
        return getInstance().getRetrofit().create(tClass);
    }

    private Retrofit create() {

        if (context == null) {
            context = RxHttp.getRequestSetting().getContext();
        }

        return create(RxHttp.getRequestSetting().getBaseUrl());
    }


    /**
     * 创建Retrofit
     *
     * @param baseUrl String
     * @return Retrofit
     */
    private Retrofit create(String baseUrl) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BaseUrlUtils.checkBaseUrl(baseUrl))
                .client(createOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Gson gson = RxHttp.getRequestSetting().getGson();
        if (gson == null) {
            gson = new Gson();
        }
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        return builder.build();
    }

    /**
     * 创建OkHttpClient
     *
     * @return OkHttpClient
     */
    private OkHttpClient createOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 设置调试模式打印日志
        if (RxHttp.getRequestSetting().isDebug()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        // 设置3个超时时长
        long timeout = RxHttp.getRequestSetting().getTimeout();
        long connectTimeout = RxHttp.getRequestSetting().getConnectTimeout();
        long readTimeout = RxHttp.getRequestSetting().getReadTimeout();
        long writeTimeout = RxHttp.getRequestSetting().getWriteTimeout();
        boolean isHttpsRequest = RxHttp.getRequestSetting().isHttpsRequest();
        boolean cache = RxHttp.getRequestSetting().useCache();

        builder.connectTimeout(connectTimeout > 0 ? connectTimeout : timeout, TimeUnit.MILLISECONDS);
        builder.readTimeout(readTimeout > 0 ? readTimeout : timeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(writeTimeout > 0 ? writeTimeout : timeout, TimeUnit.MILLISECONDS);

        builder.retryOnConnectionFailure(true);

        //添加header头部公共参数拦截器
        PublicHeadersInterceptor.addTo(builder);
        //添加body公共请求参数拦截器
        PublicQueryParameterInterceptor.addTo(builder);
        //数据缓存拦截器
        CookieInterceptor.addTo(builder, context, cache);

        if (isHttpsRequest) {
            builder.sslSocketFactory(SslConnectUtils.createSSLSocketFactory(), SslConnectUtils.getTrustManager());
            builder.hostnameVerifier(SslConnectUtils.getHostnameVerifier());
        }

        return builder.build();
    }


    private Retrofit getRetrofit() {
        return mRetrofit;
    }

    private static class SingletonHolder {
        private static RequestClientManager requestClientManager = new RequestClientManager();
    }
}
