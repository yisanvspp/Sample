package com.yisan.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author：wzh
 * @description: 可以hook某个方法、在某个方法前插入代码、
 * @packageName: com.yisan.aop.aspectj
 * @date：2019/11/26 0026 下午 2:46
 */
@Aspect
public class Method {
    /**
     * 切点 ： *表示 匹配任务返回的值 、 包路径 + 类名 + 方法名
     */
    @Pointcut("execution(* com.yisan.sample.aop.model.Method.say(..))")
    public void callMethod() {

    }

    @Before("callMethod()")
    public void beforeMethodCall(ProceedingJoinPoint joinPoint) {
        //callMethod()方法调用前插入代码
    }

    @After("callMethod()")
    public void afterMethodCall(ProceedingJoinPoint joinPoint) {
        //callMethod()方法调用后插入代码
    }

}
