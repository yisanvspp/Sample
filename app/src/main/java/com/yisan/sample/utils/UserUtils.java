package com.yisan.sample.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.yisan.sample.login.LoginBean;


public class UserUtils {

    private static final String KEY_LOGIN_BEAN = "KEY_LOGIN_BEAN";

    private LoginBean mLoginBean = null;

    private UserUtils() {
        getLoginBean();
    }

    public static UserUtils getInstance() {
        return Holder.INSTANCE;
    }

    public LoginBean getLoginBean() {
        if (mLoginBean == null) {
            String json = SPUtils.getInstance().get(KEY_LOGIN_BEAN, "");
            try {
                mLoginBean = new Gson().fromJson(json, LoginBean.class);
            } catch (Exception ignore) {
            }
        }
        return mLoginBean;
    }

    public void login(LoginBean loginBean) {
        mLoginBean = loginBean;
        String json = new Gson().toJson(loginBean);
        SPUtils.getInstance().save(KEY_LOGIN_BEAN, json);
    }

    public void logout() {
        mLoginBean = null;
        SPUtils.getInstance().clear();
    }

    public void update(LoginBean loginBean) {
        mLoginBean = loginBean;
        SPUtils.getInstance().save(KEY_LOGIN_BEAN, mLoginBean);
    }

    public boolean isLogin() {
        LoginBean loginBean = getLoginBean();
        if (loginBean == null) {
            return false;
        }
        if (loginBean.getId() > 0) {
            return true;
        }
        return false;
    }

    public boolean doIfLogin(Context context) {
        if (isLogin()) {
            return true;
        } else {
//            LoginActivity.start(context);
            return false;
        }
    }

    private static class Holder {
        private static final UserUtils INSTANCE = new UserUtils();
    }

}
