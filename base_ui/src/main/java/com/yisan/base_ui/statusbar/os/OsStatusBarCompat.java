package com.yisan.base_ui.statusbar.os;

import android.app.Activity;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.base_ui.statusbar
 * @date：2019/11/1 0001 下午 2:12
 */
public interface OsStatusBarCompat {
    void setDarkIconMode(@NonNull Activity activity, boolean darkIconMode);

    void setDarkIconMode(@NonNull Fragment fragment, boolean darkIconMode);

    void setDarkIconMode(@NonNull Window window, boolean darkIconMode);
}
