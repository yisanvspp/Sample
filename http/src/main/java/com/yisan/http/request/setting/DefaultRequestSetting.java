package com.yisan.http.request.setting;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.Map;

/**
 * 描述：网络请求设置（默认）
 *
 * @author wzh
 * @packageName com.yisan.http
 * @fileName DefaultRequestSetting.java
 * @date 2019-11-30  下午 10:11
 */
public abstract class DefaultRequestSetting implements RequestSetting {


    @Override
    public long getTimeout() {
        return 5000;
    }

    @Override
    public long getConnectTimeout() {
        return 0;
    }

    @Override
    public long getReadTimeout() {
        return 0;
    }

    @Override
    public long getWriteTimeout() {
        return 0;
    }

    @Nullable
    @Override
    public Map<String, String> getStaticPublicQueryParameter() {
        return null;
    }

    @Nullable
    @Override
    public Map<String, ParameterGetter> getDynamicPublicQueryParameter() {
        return null;
    }

    @Nullable
    @Override
    public Map<String, String> getStaticHeaderParameter() {
        return null;
    }

    @Nullable
    @Override
    public Map<String, ParameterGetter> getDynamicHeaderParameter() {
        return null;
    }

    @Override
    public boolean ignoreSslForHttps() {
        return false;
    }

    @Override
    public boolean enableTls12BelowAndroidKitkat() {
        return true;
    }

    @Override
    public int[] getMultiSuccessCode() {
        return new int[0];
    }

    @Nullable
    @Override
    public Gson getGson() {
        return null;
    }

    @Override
    public int getCookieNetWorkTime() {
        return 60;
    }

    @Override
    public int getCookieNoNetWorkTime() {
        return 24 * 60 * 60 * 30;
    }
}
