package com.yisan.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.aop.annotation
 * @date：2019/11/26 0026 下午 2:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AndroidMainThread {
}
