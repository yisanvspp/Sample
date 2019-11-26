package com.yisan.aop.aspectj;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author：wzh
 * @description: 检测登录状态
 * @packageName: com.yisan.sample.aop.aspect
 * @date：2019/11/5 0005 下午 5:02
 */
@Aspect
public class CheckLoginAspect {
    /**
     * 切点
     */
    @Around("execution(@com.yisan.aop.annotation.CheckLogin void *(..))")
    public void checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {

        //判断本地的登录状态
        boolean isLogin = false;
        if (isLogin) {
            joinPoint.proceed();
        } else {
            Log.e("wzh", "checkLogin: 未登录、去登录界面");
        }
    }


}
