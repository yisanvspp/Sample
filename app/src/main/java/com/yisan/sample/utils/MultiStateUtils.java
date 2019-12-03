package com.yisan.sample.utils;

import android.view.View;

import com.kennyc.view.MultiStateView;
import com.yisan.aop.annotation.OnClickGap;

/**
 * @author CuiZhen
 * @date 2019/5/25
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class MultiStateUtils {

    public static void toLoading(MultiStateView view) {
        view.setViewState(MultiStateView.VIEW_STATE_LOADING);
    }

    public static void toEmpty(MultiStateView view) {
        view.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    public static void toError(MultiStateView view) {
        view.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    public static void toContent(MultiStateView view) {
        view.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    public static void setEmptyAndErrorClick(MultiStateView view, SimpleListener listener) {
        setEmptyClick(view, listener);
        setErrorClick(view, listener);
    }

    public static void setEmptyClick(MultiStateView view, SimpleListener listener) {
        View empty = view.getView(MultiStateView.VIEW_STATE_EMPTY);
        if (empty != null) {
            onClick(empty, listener);
//            empty.setOnClickListener(new OnClickListener2() {
//                @Override
//                public void onClick2(View v) {
//                    listener.onResult();
//                }
//            });
        }
    }

    public static void setErrorClick(MultiStateView view, SimpleListener listener) {
        View error = view.getView(MultiStateView.VIEW_STATE_ERROR);
        if (error != null) {
            onClick(error, listener);
//            error.setOnClickListener(new OnClickListener2() {
//                @Override
//                public void onClick2(View v) {
//                    listener.onResult();
//                }
//            });
        }
    }

    @OnClickGap
    private static void onClick(View view, SimpleListener listener) {
        view.setOnClickListener(v -> {
            listener.onResult();
        });
    }


    public interface SimpleListener {
        void onResult();
    }
}
