package com.yisan.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * @author：wzh
 * @description: 被注解IoThread修饰的方法, 加入执行ioSspect()里面的方法。在子线程中执行、
 * @packageName: com.yisan.aop.aspectj
 * @date：2019/11/26 0026 上午 11:57
 */
@Aspect
public class IoThreadAspect {

    /**
     * 执行的切面方法
     */
    @Around("execution(@com.yisan.aop.annotation.IoThread void *(..))")
    public void ioAspect(final ProceedingJoinPoint joinPoint) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    joinPoint.proceed();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

}
