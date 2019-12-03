package com.yisan.sample.api;


import com.yisan.sample.main.home.model.ArticleListBean;
import com.yisan.sample.main.knowledge.model.ChapterBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Observable<WanResponse<ArticleListBean>> getArticleList(@Path("page") int page);


    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<WanResponse<List<ChapterBean>>> getKnowledgeList();


    /**
     * 获取公众号列表
     * 方法： GET
     */
    @GET("wxarticle/chapters/json")
    Observable<WanResponse<List<ChapterBean>>> getWxArticleChapters();


    /**
     * 查看某个公众号历史数据
     * 方法：GET
     * 参数：
     * 公众号 ID：拼接在 url 中，eg:405
     * 公众号页码：拼接在 url 中，eg:1
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<WanResponse<ArticleListBean>> getWxArticleList(@Path("id") int id,
                                                              @Path("page") int page);


    /**
     * 项目列表数据
     * 方法：GET
     * 参数：
     * cid 分类的id，上面项目分类接口
     * 页码：拼接在链接中，从1开始。
     */
    @GET("project/list/{page}/json")
    Observable<WanResponse<ArticleListBean>> getProjectArticleList(@Path("page") int page,
                                                                   @Query("cid") int id);

}
