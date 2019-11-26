package com.yisan.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author：wzh
 * @description: 被UiThread注解修饰的方法、会加入uiThreadAspect()方法
 * @packageName: com.yisan.aop.aspectj
 * @date：2019/11/26 0026 下午 2:25
 */

@Aspect
public class UiThreadAspect {


    @Around("execution(@com.yisan.aop.annotation.AndroidMainThread void *(..))")
    public void uiThreadAspect(final ProceedingJoinPoint joinPoint) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
    }

}
