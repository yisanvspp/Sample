package com.yisan.mvp.view;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.yisan.mvp.RxLife;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * @author：wzh
 * @description: rx生命周期管理
 * @packageName: com.yisan.mvp.view
 * @date：2019/11/28 0028 下午 5:44
 */
public class RxActivity extends AppCompatActivity implements RxLife {


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
        return this;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose();
    }
}
