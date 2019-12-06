package com.yisan.sample;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Looper;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.yisan.base.utils.Utils;
import com.yisan.crash.config.CaocConfig;
import com.yisan.sample.http.RxHttpRequestSetting;
import com.yisan.sample.http.WanCache;
import com.yisan.sample.utils.AppUtils;

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


        if (isMainProcess()) {

            Utils.init(this);

            mAppContext = this.getApplicationContext();

            RxHttp.init(this);

            RxHttp.initRequest(new RxHttpRequestSetting(getCookieJar()));

            WanCache.init();

            //CrashProtectManager.getInstance(mAppContext).init();

            CaocConfig.Builder.create().apply();

            initBugly();
        }

    }

    /**
     * 是否在主进程
     *
     * @return boolean
     */
    public boolean isMainProcess() {
        return getApplicationContext().getPackageName().equals(getCurrentProcessName());
    }


    /**
     * 是否在主线程
     *
     * @return boolean
     */
    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }


    /**
     * 获取当前进程名
     */
    public String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService
                (Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }


    /**
     * 初始化腾讯bug管理平台
     */
    private void initBugly() {
        /* Bugly SDK初始化
         * 参数1：上下文对象
         * 参数2：APPID，平台注册时得到,注意替换成你的appId
         * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
         * 注意：如果您之前使用过Bugly SDK，请将以下这句注释掉。
         */
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());

        strategy.setAppVersion(AppUtils.getAppVersionName());
        strategy.setAppPackageName(AppUtils.getAppPackageName());
        //Bugly会在启动20s后联网同步数据
        strategy.setAppReportDelay(20000);

        /*  第三个参数为SDK调试模式开关，调试模式的行为特性如下：
            输出详细的Bugly SDK的Log；
            每一条Crash都会被立即上报；
            自定义日志将会在Logcat中输出。
            建议在测试阶段建议设置成true，发布时设置为false。*/

        CrashReport.initCrashReport(getApplicationContext(), "a7493efefb", true, strategy);

    }
}
