package com.yisan.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：wzh
 * @description: 埋点
 * @packageName: com.yisan.sample.aop
 * @date：2019/11/5 0005 下午 3:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BuriedPoint {

}
