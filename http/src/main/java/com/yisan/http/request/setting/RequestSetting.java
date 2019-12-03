package com.yisan.http.request.setting;


import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.Map;

/**
 * 描述：网络请求设置
 *
 * @author Cuizhen
 * @date 2018/10/12
 */
public interface RequestSetting {

    /**
     * 设置默认BaseUrl
     */
    @NonNull
    String getBaseUrl();


    int getSuccessCode();


    /**
     * 获取默认超时时长，单位为毫秒数
     */
    @IntRange(from = 1)
    long getTimeout();

    /**
     * 获取Connect超时时长，单位为毫秒数
     * 返回0则取getTimeout
     */
    @IntRange(from = 0)
    long getConnectTimeout();

    /**
     * 获取Read超时时长，单位为毫秒数
     * 返回0则取getTimeout
     */
    @IntRange(from = 0)
    long getReadTimeout();

    /**
     * 获取Write超时时长，单位为毫秒数
     * 返回0则取getTimeout
     */
    @IntRange(from = 0)
    long getWriteTimeout();


    /**
     * 获取静态公共请求参数
     */
    @Nullable
    Map<String, String> getStaticPublicQueryParameter();

    /**
     * 获取动态的请求参数
     *
     * @return
     */
    @Nullable
    Map<String, ParameterGetter> getDynamicPublicQueryParameter();

    /**
     * 静态的头部参数
     */
    @Nullable
    Map<String, String> getStaticHeaderParameter();

    /**
     * 动态的头部参数
     */
    @Nullable
    Map<String, ParameterGetter> getDynamicHeaderParameter();


    /**
     * 忽略HTTPS的证书验证
     * 仅在后台未正确配置且着急调试时可临时置为true
     *
     * @return 建议为false
     */

    boolean ignoreSslForHttps();


    /**
     * 是否为Https请求
     *
     * @return boolean
     */
    @Nullable
    boolean isHttpsRequest();

    /**
     * android4.4及以下版本默认未开启Tls1.2
     * 返回true则强制开启
     */
    boolean enableTls12BelowAndroidKitkat();

    /**
     * 是否设置缓存
     * 默认false 不使用缓存
     */
    @Nullable
    boolean useCache();


    @Nullable
    int[] getMultiSuccessCode();

    /**
     * 在创建OkHttpClient之前调用，及框架完成所有配置后
     */
    @Nullable
    Gson getGson();


    /**
     * 获取全局的Context
     */
    @Nullable
    Context getContext();

    /**
     * 是否打开调试模式
     */
    boolean isDebug();

    /**
     * 有网情况下的本地缓存时间默认60秒
     */
    int getCookieNetWorkTime();

    /**
     * 无网络的情况下本地缓存时间默认30天
     */
    int getCookieNoNetWorkTime();

}
