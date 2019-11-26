package com.yisan.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：wzh
 * @description: 被改注解标记的方法运行在子线程中、改注解运行被反射获取
 * @packageName: com.yisan.aop.annotation
 * @date：2019/11/26 0026 上午 11:54
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IoThread {
}
