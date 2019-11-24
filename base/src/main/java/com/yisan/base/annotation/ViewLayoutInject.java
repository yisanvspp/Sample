package com.yisan.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：wzh
 * @description: 界面布局注入
 * @packageName: com.yisan.base.annotation
 * @date：2019/11/24 0024 下午 9:49
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ViewLayoutInject {
    int value() default -1;
}
