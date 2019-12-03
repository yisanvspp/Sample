package com.yisan.sample.main.home.model;

import android.text.TextUtils;

import com.yisan.http.request.bean.BaseBean;

import java.util.List;

/**
 * @author CuiZhen
 * @date 2019/5/15
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class ArticleBean extends BaseBean {
    /**
     * apkLink :
     * author : 玉刚说
     * chapterId : 410
     * chapterName : 玉刚说
     * collect : false
     * courseId : 13
     * desc :
     * envelopePic :
     * fresh : false
     * id : 8367
     * link : https://mp.weixin.qq.com/s/uI7Fej1_qSJOJnzQ6offpw
     * niceDate : 2019-05-06
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1557072000000
     * superChapterId : 408
     * superChapterName : 公众号
     * tags : [{"name":"公众号","url":"/wxarticle/list/410/1"}]
     * title : 深扒 EventBus：register
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
     */

    public String apkLink;
    public String author;
    public String shareUser;
    public int chapterId;
    public String chapterName;
    public boolean collect;
    public int courseId;
    public String desc;
    public String envelopePic;
    public boolean top;
    public boolean fresh;
    public int id;
    public String link;
    public String niceDate;
    public String origin;
    public String prefix;
    public String projectLink;
    public long publishTime;
    public int superChapterId;
    public String superChapterName;
    public String title;
    public int type;
    public int userId;
    public int visible;
    public int zan;
    public List<TagsBean> tags;
    public int originId;


    public boolean isFresh() {
        return fresh;
    }

    public boolean isCollect() {
        return collect;
    }

    public String getAuthor() {
        if (!TextUtils.isEmpty(author)) {
            return author;
        }
        if (!TextUtils.isEmpty(shareUser)) {
            return shareUser;
        }
        return "匿名";
    }

    @Override
    public String toString() {
        return "ArticleBean{" +
                "apkLink='" + apkLink + '\'' +
                ", author='" + author + '\'' +
                ", shareUser='" + shareUser + '\'' +
                ", chapterId=" + chapterId +
                ", chapterName='" + chapterName + '\'' +
                ", collect=" + collect +
                ", courseId=" + courseId +
                ", desc='" + desc + '\'' +
                ", envelopePic='" + envelopePic + '\'' +
                ", top=" + top +
                ", fresh=" + fresh +
                ", id=" + id +
                ", link='" + link + '\'' +
                ", niceDate='" + niceDate + '\'' +
                ", origin='" + origin + '\'' +
                ", prefix='" + prefix + '\'' +
                ", projectLink='" + projectLink + '\'' +
                ", publishTime=" + publishTime +
                ", superChapterId=" + superChapterId +
                ", superChapterName='" + superChapterName + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", userId=" + userId +
                ", visible=" + visible +
                ", zan=" + zan +
                ", tags=" + tags +
                ", originId=" + originId +
                '}';
    }

    public static class TagsBean extends BaseBean {
        /**
         * name : 公众号
         * url : /wxarticle/list/410/1
         */

        public String name;
        public String url;

        @Override
        public String toString() {
            return "TagsBean{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
