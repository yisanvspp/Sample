package com.yisan.base_ui.statusbar.os;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.base_ui.statusbar
 * @date：2019/11/1 0001 下午 2:14
 */
public class OsStatusBarCompatDef implements OsStatusBarCompat {

    @Override
    public void setDarkIconMode(@NonNull Activity activity, boolean darkIconMode) {
        DefStatusBarUtils.setDarkIconMode(activity.getWindow(), darkIconMode);
    }

    @Override
    public void setDarkIconMode(@NonNull Fragment fragment, boolean darkIconMode) {
        if (fragment.getActivity() != null) {
            DefStatusBarUtils.setDarkIconMode(fragment.getActivity().getWindow(), darkIconMode);
        }
    }

    @Override
    public void setDarkIconMode(@NonNull Window window, boolean darkIconMode) {
        DefStatusBarUtils.setDarkIconMode(window, darkIconMode);
    }

    private static class DefStatusBarUtils {
        private static void setDarkIconMode(Window window, boolean darkIconMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int flags = window.getDecorView().getSystemUiVisibility();
                if (darkIconMode) {
                    window.getDecorView().setSystemUiVisibility(flags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    window.getDecorView().setSystemUiVisibility(flags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        }
    }
}
