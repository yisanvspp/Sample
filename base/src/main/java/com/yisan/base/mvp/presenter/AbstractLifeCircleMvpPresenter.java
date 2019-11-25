package com.yisan.base.mvp.presenter;

import com.yisan.base.mvp.ILifeCircle;
import com.yisan.base.mvp.IMvpView;
import com.yisan.base.mvp.MvpControler;

import java.lang.ref.WeakReference;

/**
 * @author：wzh
 * @description: P层持有V层的引用
 * @packageName: com.yisan.sample.mvp.presenter.impl
 * @date：2019/11/25 0025 上午 10:01
 */
public abstract class AbstractLifeCircleMvpPresenter<T extends IMvpView> implements ILifeCircle {

    private static final String TAG = "wzh_AbstractLifeCircl";

    private WeakReference<T> weakReference;
    private MvpControler mvpControler;


    public AbstractLifeCircleMvpPresenter(T view) {
        super();
        onAttach(view);
        mvpControler = view.getMvpControler();
        mvpControler.savePresenter(this);
    }


    @Override
    public void onAttach(IMvpView view) {

        if (weakReference == null) {
            weakReference = new WeakReference(view);
        } else {
            T t = weakReference.get();
            if (t != view) {
                weakReference = new WeakReference(view);
            }
        }
    }

    @Override
    public void onDestroy() {
        weakReference = null;
    }

    protected T getView() {
        T view = weakReference != null ? weakReference.get() : null;
        if (view == null) {
            return getEmptyView();
        }
        return view;
    }

    protected abstract T getEmptyView();

}
