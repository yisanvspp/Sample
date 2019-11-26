package com.yisan.aop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yisan.aop.constants.PermissionConstants;
import com.yisan.aop.interfaces.IPermissionCallback;
import com.yisan.aop.utils.PermissionUtil;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.aop.activity
 * @date：2019/11/26 0026 下午 4:35
 */
public class PermissionActivity extends AppCompatActivity {


    private static IPermissionCallback mCallBack;


    public static void startActivity(Context context, String[] permissions, int requestCode,
                                     IPermissionCallback callback) {

        if (context == null) {
            return;
        }

        mCallBack = callback;

        Intent intent = new Intent(context, PermissionActivity.class);

        //开启新的任务栈并且清除栈顶
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(PermissionConstants.NEED_PERMISSIONS, permissions);
        intent.putExtra(PermissionConstants.REQUEST_CODE, requestCode);
        //利用context启动activity
        context.startActivity(intent);


        //并且，如果是activity启动的，那么还要屏蔽掉activity切换动画
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(0, 0);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取传递过来的参数

        Intent intent = getIntent();

        String[] permissions = intent.getStringArrayExtra(PermissionConstants.NEED_PERMISSIONS);

        int requestCode = intent.getIntExtra(PermissionConstants.REQUEST_CODE, 0);

        if (permissions != null) {

            if (PermissionUtil.hasSelfPermissions(this, permissions)) {
                //获取了权限
                mCallBack.granted(requestCode);
                finish();
                overridePendingTransition(0, 0);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //请求权限
                requestPermissions(permissions, requestCode);
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //现在拿到了权限的申请结果，那么如何处理，我这个Activity只是为了申请，然后把结果告诉外界，所以结果的处理只能是外界传进来
        boolean granted = PermissionUtil.verifyPermissions(grantResults);
        //如果用户给了权限
        if (granted) {
            mCallBack.granted(requestCode);
        } else {
            if (PermissionUtil.shouldShowRequestPermissionRationale(this, permissions)) {
                mCallBack.denied(requestCode);
            } else {
                mCallBack.deniedForever(requestCode);
            }
        }
        finish();

        overridePendingTransition(0, 0);

    }
}
