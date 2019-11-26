package com.yisan.aop.aspectj;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author：wzh
 * @description: 为所有点击事件埋点
 * @packageName: com.yisan.sample.aop.aspect
 * @date：2019/11/5 0005 下午 3:26
 */
@Aspect
public class BuriedPoint {

    @Around("execution(@com.yisan.aop.annotation.BuriedPoint void *(..))")
    public void callMethod(ProceedingJoinPoint joinPoint) throws Throwable {


        // do your things
        Log.e("wzh", "埋点");

        joinPoint.proceed();

    }

}
