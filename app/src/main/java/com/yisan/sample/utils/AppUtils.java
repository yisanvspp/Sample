package com.yisan.sample.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.yisan.sample.YsApplication;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample.utils
 * @date：2019/12/5 0005 下午 2:36
 */
public class AppUtils {


    public static String getAppVersionName() {

        Context context = YsApplication.getAppContext();

        PackageManager packageManager = context.getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);

            return packageInfo.versionName;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getAppPackageName() {

        return YsApplication.getAppContext().getPackageName();
    }
}
