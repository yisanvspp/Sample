package com.yisan.mvp.view;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.yisan.mvp.RxLife;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.mvp.view
 * @date：2019/11/28 0028 下午 10:58
 */
public class RxFragment extends Fragment implements RxLife {

    private CompositeDisposable mCompositeDisposable;

    @Override
    public CompositeDisposable getCompositeDisposable() {

        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }

        return this.mCompositeDisposable;
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(disposable);
    }


    @Override
    public void dispose() {
        if (this.mCompositeDisposable != null) {
            this.mCompositeDisposable.dispose();
        }
    }

    @Override
    public Context getActivityContext() {
        return getContext();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dispose();
    }
}
