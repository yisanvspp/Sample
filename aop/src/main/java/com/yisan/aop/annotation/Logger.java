package com.yisan.aop.annotation;

import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：wzh
 * @description:打印日志
 * @packageName: com.yisan.sample.aop
 * @date：2019/11/5 0005 下午 1:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logger {
    int value() default Log.ERROR;
}
