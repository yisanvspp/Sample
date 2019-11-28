package com.yisan.sample.main.home.api;


import com.yisan.mvp.RxLife;
import com.yisan.sample.api.HttpApiService;
import com.zhxu.library.api.BaseApi;
import com.zhxu.library.listener.HttpOnNextListener;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample.main.home.api
 * @date：2019/11/28 0028 下午 5:25
 */
public class ArticleListApi<T> extends BaseApi<T> {

    public ArticleListApi(HttpOnNextListener listener, RxLife rxLife) {
        super(listener, rxLife);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return retrofit.create(HttpApiService.class).getArticleList(0);
    }

}
