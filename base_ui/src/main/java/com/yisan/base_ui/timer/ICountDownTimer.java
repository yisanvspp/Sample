package com.yisan.base_ui.timer;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.base_ui.timer
 * @date：2019/11/26 0026 下午 5:52
 */
public interface ICountDownTimer {

    //倒计时
    void onTick(long time);

    //倒计时完成
    void onFinish();

    //取消倒计时
    void onCancle();

}
