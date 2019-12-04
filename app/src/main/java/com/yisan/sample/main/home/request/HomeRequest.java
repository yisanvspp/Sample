package com.yisan.sample.main.home.request;

import com.yisan.sample.api.WanApi;
import com.yisan.sample.http.BaseRequest;
import com.yisan.sample.http.RequestListener;
import com.yisan.sample.http.WanCache;
import com.yisan.sample.main.home.model.ArticleListBean;

import per.goweii.rxhttp.core.RxLife;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample.main.home.request
 * @date：2019/12/4 0004 下午 4:28
 */
public class HomeRequest extends BaseRequest {


    /**
     * 获取首页文字列表
     *
     * @param rxLife
     * @param page
     * @param noUseCacheData
     * @param listener
     */
    public static void getArticleList(
            RxLife rxLife,
            int page,
            boolean noUseCacheData,
            RequestListener<ArticleListBean> listener) {

        if (page == 0) {

            cacheAndNetBean(
                    rxLife,
                    WanApi.api().getArticleList(page), noUseCacheData,
                    WanCache.CacheKey.ARTICLE_LIST(page),
                    ArticleListBean.class, listener);
        } else {

            request(rxLife, WanApi.api().getArticleList(page), listener);

        }

    }


}
