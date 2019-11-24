package com.yisan.sample.mvp.presenter;

import android.app.Activity;

import com.yisan.base.timer.CustomCountDownTimer;
import com.yisan.sample.activity.SplashActivity;

import java.lang.ref.WeakReference;

/**
 * @author：wzh
 * @description: 定时器倒计时
 * @packageName: com.yisan.sample.mvp.presenter
 * @date：2019/11/24 0024 下午 10:25
 */
public class TimerPrestener {


    /**
     * 倒计时时间
     */
    private long timerTime = 5;

    private CustomCountDownTimer timer;

    private WeakReference<Activity> softReference;

    public TimerPrestener(SplashActivity activity) {
        softReference = new WeakReference<>(activity);
    }

    public void initTimer() {

        SplashActivity splashActivity = (SplashActivity) softReference.get();

        if (timer == null) {

            timer = new CustomCountDownTimer(timerTime, new CustomCountDownTimer.ICountDownTimer() {
                @Override
                public void onTick(long time) {

                    splashActivity.setText(String.valueOf(time));
                }

                @Override
                public void onFinish() {

                    splashActivity.setText("跳过");
                }

                @Override
                public void onCancle() {

                }
            });

        }
        timer.start();
    }

    public void cancle() {
        if (timer != null) {
            timer.cancle();
            timer = null;
        }
    }
}
