package com.yisan.aop.aspectj;

import android.content.Context;

import com.yisan.aop.activity.PermissionActivity;
import com.yisan.aop.annotation.PermissionDenied;
import com.yisan.aop.annotation.PermissionDeniedForever;
import com.yisan.aop.annotation.PermissionNeed;
import com.yisan.aop.interfaces.IPermissionCallback;
import com.yisan.aop.utils.ApplicationUtil;
import com.yisan.aop.utils.PermissionUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.aop.aspectj
 * @date：2019/11/26 0026 下午 4:26
 */
@Aspect
public class PermissionAspect {

    @Around("execution(@com.yisan.aop.annotation.PermissionNeed * *(..)) && @annotation(permissionNeed)")
    public void doPermission(final ProceedingJoinPoint joinPoint, PermissionNeed permissionNeed) {


        PermissionActivity.startActivity(getContext(joinPoint), permissionNeed.permissions(),
                permissionNeed.requestCode(), new IPermissionCallback() {
                    @Override
                    public void granted(int requestCode) {
                        // 如果授予，那么执行joinPoint原方法体
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void denied(int requestCode) {
                        PermissionUtil.invokeAnnotation(joinPoint.getThis(), PermissionDenied.class, requestCode);
                    }

                    @Override
                    public void deniedForever(int requestCode) {
                        PermissionUtil.invokeAnnotation(joinPoint.getThis(), PermissionDeniedForever.class, requestCode);
                    }
                });
    }


    private Context getContext(final ProceedingJoinPoint joinPoint) {
        final Object obj = joinPoint.getThis();
        // 如果切入点是一个类？那么这个类的对象是不是context？
        if (obj instanceof Context) {
            return (Context) obj;
        } else {
            // 如果切入点不是Context的子类呢？ //jointPoint.getThis，其实是得到切入点所在类的对象
            Object[] args = joinPoint.getArgs();
            if (args.length > 0) {
                //看看第一个参数是不是context
                if (args[0] instanceof Context) {
                    return (Context) args[0];
                } else {
                    //如果不是，那么就只好hook反射了
                    return ApplicationUtil.getApplication();
                }
            } else {
                //如果不是，那么就只好hook反射了
                return ApplicationUtil.getApplication();
            }
        }
    }
}
