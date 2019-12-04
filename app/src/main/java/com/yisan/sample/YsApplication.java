package com.yisan.sample;

import android.app.Application;
import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yisan.base.utils.Utils;
import com.yisan.sample.http.RxHttpRequestSetting;
import com.yisan.sample.http.WanCache;

import per.goweii.rxhttp.core.RxHttp;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample
 * @date：2019/11/28 0028 下午 9:39
 */
public class YsApplication extends Application {

    private static Context mAppContext;

    private static PersistentCookieJar mCookieJar = null;


    //下拉刷新上拉加载更多、全局设定
    static {
//        ClassicsHeader.REFRESH_HEADER_PULLING = "下拉可以刷新";
//        ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新...";
//        ClassicsHeader.REFRESH_HEADER_LOADING = "正在加载...";
//        ClassicsHeader.REFRESH_HEADER_RELEASE = "释放立即刷新";
//        ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";
//        ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";
//        ClassicsHeader.REFRESH_HEADER_SECONDARY = "释放进入二楼";
//        ClassicsHeader.REFRESH_HEADER_UPDATE = "上次更新 M-d HH:mm";
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(context));
//        ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载更多";
//        ClassicsFooter.REFRESH_FOOTER_RELEASE = "释放立即加载";
//        ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在刷新...";
//        ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载...";
//        ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成";
//        ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败";
//        ClassicsFooter.REFRESH_FOOTER_NOTHING = "没有更多数据了";
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context));
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public static PersistentCookieJar getCookieJar() {
        //okhttp cookie的get缓存

        if (mCookieJar == null) {
            mCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getAppContext()));
        }
        return mCookieJar;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        Utils.init(this);

        mAppContext = this.getApplicationContext();

        RxHttp.init(this);

        RxHttp.initRequest(new RxHttpRequestSetting(getCookieJar()));

        WanCache.init();
    }

}
