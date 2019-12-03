package com.yisan.sample;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yisan.http.RxHttp;
import com.yisan.http.request.setting.DefaultRequestSetting;
import com.zhxu.library.utils.ContextUtils;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample
 * @date：2019/11/28 0028 下午 9:39
 */
public class YsApplication extends Application {

    private static Context mAppContext;


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


    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = this.getApplicationContext();

        ContextUtils.init(this);

        RxHttp.init(this);

        RxHttp.initRequestSetting(new DefaultRequestSetting() {
            @NonNull
            @Override
            public String getBaseUrl() {
                return Constants.BASE_URL;
            }

            @Override
            public int getSuccessCode() {
                return 0;
            }

            @Override
            public boolean isHttpsRequest() {
                return true;
            }

            @Override
            public boolean useCache() {
                return true;
            }

            @Nullable
            @Override
            public Context getContext() {
                return mAppContext;
            }

            @Override
            public boolean isDebug() {
                return true;
            }
        });

    }


    public Context getAppContext() {
        return mAppContext;
    }
}
