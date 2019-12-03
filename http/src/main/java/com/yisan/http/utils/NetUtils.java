package com.yisan.http.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yisan.http.RxHttp;

/**
 * 描述：判断网络的辅助类
 *
 * @author Cuizhen
 * @date 2018/7/20-下午2:21
 */
public class NetUtils {

    /**
     * 判断是否有网络
     */
    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) RxHttp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
