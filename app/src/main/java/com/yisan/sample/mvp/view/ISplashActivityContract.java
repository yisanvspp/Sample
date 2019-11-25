package com.yisan.sample.mvp.view;

import com.yisan.base.mvp.ILifeCircle;
import com.yisan.base.mvp.IMvpView;

/**
 * @author：wzh
 * @description: SplashActivity的View接口
 * @packageName: com.yisan.sample.mvp.view
 * @date：2019/11/25 0025 下午 2:33
 */
public interface ISplashActivityContract {

    interface IView extends IMvpView {
        void setTvTimer(String str);
    }

    interface IPresenter extends ILifeCircle {
        void initTimer();
    }

}
