package com.yisan.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：wzh
 * @description: 检测登录状态
 * @packageName: com.yisan.sample.aop.aspect
 * @date：2019/11/5 0005 下午 5:01
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface CheckLogin {
}
