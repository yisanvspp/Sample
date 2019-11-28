package com.yisan.sample.api;

import com.yisan.sample.main.home.model.ArticleListBean;
import com.zhxu.library.api.BaseResultEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author：wzh
 * @description: api接口
 * @packageName: com.yisan.sample.api
 * @date：2019/11/28 0028 下午 4:48
 */
public interface HttpApiService {


    /**
     * 获取首页文章列表
     *
     * @param page int
     * @return Observable
     */
    @GET("article/list/{page}/json")
    Observable<BaseResultEntity<ArticleListBean>> getArticleList(@Path("page") int page);


}
