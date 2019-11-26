package com.yisan.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author：wzh
 * @description: 按钮点击间隔500毫秒
 * @packageName: com.yisan.sample.aop.aspect
 * @date：2019/11/5 0005 下午 2:11
 */
@Aspect
public class OnClickGap {

    /**
     * 1000ms内不响应
     */
    private static final int CLICK_GAP_RESPONSE = 1000;
    private static long clickGapTime = 0;

    /**
     * 判断是否应该执行，true执行，false不执行
     */
    private boolean clickGapFilter() {
        long currentTimeMillis = System.currentTimeMillis();
        long durationTime = currentTimeMillis - clickGapTime;
        if (durationTime < CLICK_GAP_RESPONSE) {
            return false;
        }
        clickGapTime = currentTimeMillis;
        return true;
    }

    @Around("execution(@com.yisan.aop.annotation.OnClickGap void *(..))")
    public void clickGapAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        if (clickGapFilter()) {
            joinPoint.proceed();
        }
    }

}
