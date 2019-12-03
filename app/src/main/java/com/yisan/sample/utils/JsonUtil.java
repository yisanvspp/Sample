package com.yisan.sample.utils;

import com.yisan.sample.api.WanResponse;
import com.yisan.sample.main.home.model.ArticleBean;
import com.yisan.sample.main.home.model.ArticleListBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：wzh
 * @description: 系统api解析JSON数据、带泛型的数据
 * @packageName: com.yisan.sample.utils
 * @date：2019/12/2 0002 下午 9:20
 */
public class JsonUtil {


    public static WanResponse parserArticleListJson(String json) {

        WanResponse wanResponse = new WanResponse();
        ArticleListBean articleListBean = new ArticleListBean();
        List<ArticleBean> list = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(json);
            //第一层解析
            String errorMsg = jsonObject.optString("errorMsg");
            int errorCode = jsonObject.optInt("errorCode");
            JSONObject data = jsonObject.optJSONObject("data");


            //第二层解析
            int curPage = data.optInt("curPage");
            int offset = data.optInt("offset");
            boolean over = data.optBoolean("over");
            int pageCount = data.optInt("pageCount");
            int size = data.optInt("size");
            int total = data.optInt("total");
            JSONArray datas = data.optJSONArray("datas");

            articleListBean.curPage = curPage;
            articleListBean.offset = offset;
            articleListBean.over = over;
            articleListBean.pageCount = pageCount;
            articleListBean.size = size;
            articleListBean.total = total;

            //第三层解析
            for (int i = 0; i < datas.length(); i++) {
                JSONObject obj = datas.optJSONObject(i);


                int chapterId = obj.optInt("chapterId");
                String chapterName = obj.optString("chapterName");
                String link = obj.optString("link");
                String shareUser = obj.optString("shareUser");
                String title = obj.optString("title");
                String author = obj.optString("author");
                String niceDate = obj.optString("niceDate");
                String envelopePic = obj.optString("envelopePic");


                ArticleBean bean = new ArticleBean();
                bean.chapterId = chapterId;
                bean.chapterName = chapterName;
                bean.link = link;
                bean.shareUser = shareUser;
                bean.title = title;
                bean.author = author;
                bean.niceDate = niceDate;
                bean.envelopePic = envelopePic;
                list.add(bean);
            }

            articleListBean.datas = list;

            wanResponse.setMsg(errorMsg);
            wanResponse.setCode(errorCode);
            wanResponse.setData(articleListBean);

            return wanResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
