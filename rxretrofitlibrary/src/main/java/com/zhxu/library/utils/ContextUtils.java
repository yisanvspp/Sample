package com.zhxu.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cuizhen
 */
public class ContextUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context context = null;
    private static List<OnInit> sOnInits = null;

    public static void init(Context context) {
        ContextUtils.context = context;
        if (sOnInits != null) {
            for (OnInit onInit : sOnInits) {
                onInit.onInit(context);
            }
        }
    }

    public static Context getAppContext() {
        if (context == null) {
            throw new RuntimeException("ContextUtils未在Application中初始化");
        }
        return context;
    }

    public static void onInit(OnInit onInit) {
        if (sOnInits == null) {
            sOnInits = new ArrayList<>();
        }
        sOnInits.add(onInit);
    }

    public interface OnInit {
        void onInit(Context context);
    }
}
