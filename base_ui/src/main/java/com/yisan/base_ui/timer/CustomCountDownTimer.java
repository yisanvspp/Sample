package com.yisan.base_ui.timer;

import android.os.Handler;

/**
 * @author：wzh
 * @description: 自定义倒计时
 * @packageName: com.yisan.base_ui.timer
 * @date：2019/11/24 0024 下午 8:02
 */
public class CustomCountDownTimer implements Runnable {

    private long mCountDownTime;
    private ICountDownTimer mListener;
    private Handler handler;
    private boolean isRun;

    /**
     * @param countDownTime 单位秒
     * @param listener      回调监听
     */
    public CustomCountDownTimer(long countDownTime, ICountDownTimer listener) {
        this.mCountDownTime = countDownTime;
        this.mListener = listener;
        handler = new Handler();
        if (listener == null) {
            throw new RuntimeException("ICountDownTimer不能为空");
        }
    }


    public void start() {
        isRun = true;
        handler.post(this);

    }

    public void cancle() {
        isRun = false;
        handler.removeCallbacks(this);
        if (mListener != null) {
            mListener.onCancle();
        }
    }

    @Override
    public void run() {

        if (isRun) {

            if (mListener != null) {
                mListener.onTick(mCountDownTime);
            }

            if (mCountDownTime == 0) {
                cancle();
                if (mListener != null) {
                    mListener.onFinish();
                }
            } else {
                mCountDownTime--;
                handler.postDelayed(this, 1000);
            }

        }
    }

}
