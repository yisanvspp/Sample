package com.yisan.http.request.interceptor;

import android.content.Context;

import androidx.annotation.NonNull;

import com.yisan.http.request.cookie.CookieResult;
import com.yisan.http.utils.CookieDbUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author：wzh
 * @description: 数据持久化、缓存处理
 * @packageName: com.yisan.http.request.interceptor
 * @date：2019/12/2 0002 上午 11:16
 */
public class CookieInterceptor implements Interceptor {
    private static final String TAG = "wzh_CookieInterceptor";

    /**
     * 缓存标记
     */
    private boolean cache;


    /**
     * 上下文
     */
    private Context context;

    /**
     * dbUtil
     */
    private CookieDbUtil cookieDbUtil;


    private CookieInterceptor(Context context, boolean cache) {


        this.cookieDbUtil = CookieDbUtil.getInstance(context);
        this.context = context;
        this.cache = cache;

    }

    public static void addTo(@NonNull OkHttpClient.Builder builder, Context context, boolean cache) {

        builder.addInterceptor(new CookieInterceptor(context, cache));

    }


    @Override
    public Response intercept(Chain chain) throws IOException {


        Request request = chain.request();
        Response response = chain.proceed(request);

        if (cache) {
            String url = getRequestUrl(request);
            ResponseBody body = response.body();
            if (body != null) {
                BufferedSource source = body.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.buffer();
                Charset charset = Charset.defaultCharset();
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                //获取到返回的数据
                String bodyString = buffer.clone().readString(Objects.requireNonNull(charset));

                //保存到本地的db数据库中
                long time = System.currentTimeMillis();
                //查询Cookie值
                CookieResult cookieResult = cookieDbUtil.queryCookieBy(url);
                if (cookieResult == null) {
                    cookieResult = new CookieResult(url, bodyString, time);
                    cookieDbUtil.saveCookie(cookieResult);
                } else {
                    cookieResult.setResult(bodyString);
                    cookieResult.setTime(time);
                    cookieDbUtil.updateCookie(cookieResult);
                }
            }
        }
        return response;
    }


    /**
     * 获取请求的完整url
     *
     * @param request Request
     * @return String
     */
    private String getRequestUrl(Request request) {

        HttpUrl url = request.url();

        return url.url().toString();
    }
}
