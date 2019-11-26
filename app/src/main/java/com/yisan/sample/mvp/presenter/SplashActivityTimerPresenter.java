package com.yisan.sample.mvp.presenter;


import com.yisan.base_ui.timer.CustomCountDownTimer;
import com.yisan.base_ui.timer.ICountDownTimer;
import com.yisan.mvp.base.BaseMvpPresenter;
import com.yisan.sample.mvp.view.ISplashActivityContract;

/**
 * @author：wzh
 * @description: 定时器倒计时
 * @packageName: com.yisan.sample.mvp.presenter
 * @date：2019/11/24 0024 下午 10:25
 */
public class SplashActivityTimerPresenter extends BaseMvpPresenter<ISplashActivityContract.IView> implements ISplashActivityContract.IPresenter {

    private static final String TAG = "wzh_TimerPrestener";

    /**
     * 倒计时时间
     */
    private static final long COUNT_DOWN_TIME = 5;

    private CustomCountDownTimer timer;


    public SplashActivityTimerPresenter(ISplashActivityContract.IView view) {
        super(view);
    }


    @Override
    protected ISplashActivityContract.IView getEmptyView() {
        return null;
    }

    @Override
    public void initTimer() {

        if (timer == null) {

            timer = new CustomCountDownTimer(COUNT_DOWN_TIME, new ICountDownTimer() {
                @Override
                public void onTick(long time) {

                    getView().setTvTimer(String.valueOf(time));
                }

                @Override
                public void onFinish() {
                    getView().setTvTimer("跳过");
                }

                @Override
                public void onCancle() {

                }
            });

        }
        timer.start();
    }


    private void cancel() {
        if (timer != null) {
            timer.cancle();
            timer = null;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        cancel();
    }

}
