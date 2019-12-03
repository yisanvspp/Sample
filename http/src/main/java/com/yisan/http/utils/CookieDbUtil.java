package com.yisan.http.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yisan.http.request.cookie.CookieResult;
import com.yisan.http.request.cookie.CookieResultDao;
import com.yisan.http.request.cookie.DaoMaster;
import com.yisan.http.request.cookie.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @author：wzh
 * @description: 缓存Cookie Dao操作处理
 * @packageName: com.yisan.http.utils
 * @date：2019/12/2 0002 上午 11:34
 */
public class CookieDbUtil {

    private static final String TAG = "CookieDbUtil";

    private final static String DB_NAME = "cache.db";
    private static CookieDbUtil db;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    private CookieDbUtil(Context context) {
        this.context = context;
    }

    /**
     * 获取单例
     *
     * @return CookieDbUtil
     */
    public static CookieDbUtil getInstance(Context context) {
        if (db == null) {
            synchronized (CookieDbUtil.class) {
                if (db == null) {
                    db = new CookieDbUtil(context);
                }
            }
        }
        return db;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        }
        return openHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        }
        return openHelper.getWritableDatabase();
    }


    /**
     * 保存
     *
     * @param info CookieResult
     */
    public void saveCookie(CookieResult info) {

        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao downInfoDao = daoSession.getCookieResultDao();
        long id = downInfoDao.insert(info);

        Log.e(TAG, "saveCookie: " + id);
    }

    /**
     * 更新
     *
     * @param info CookieResult
     */
    public void updateCookie(CookieResult info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao downInfoDao = daoSession.getCookieResultDao();
        downInfoDao.update(info);

        Log.e(TAG, "updateCookie: ");
    }


    /**
     * 删除
     *
     * @param info CookieResult
     */
    public void deleteCookie(CookieResult info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao downInfoDao = daoSession.getCookieResultDao();
        downInfoDao.delete(info);
    }

    /**
     * 查询其中一条数据
     *
     * @param url 请求的完整url
     * @return CookieResult
     */
    public CookieResult queryCookieBy(String url) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao downInfoDao = daoSession.getCookieResultDao();
        QueryBuilder<CookieResult> qb = downInfoDao.queryBuilder();
        qb.where(CookieResultDao.Properties.Url.eq(url));
        List<CookieResult> list = qb.list();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }


    /**
     * 查询全部的数据
     *
     * @return List<CookieResult>
     */
    public List<CookieResult> queryCookieAll() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao downInfoDao = daoSession.getCookieResultDao();
        QueryBuilder<CookieResult> qb = downInfoDao.queryBuilder();
        return qb.list();
    }


}
