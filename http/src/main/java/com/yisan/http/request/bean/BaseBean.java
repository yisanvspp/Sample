package com.yisan.http.request.bean;

import com.google.gson.Gson;
import com.yisan.http.utils.JsonFormatUtils;

import java.io.Serializable;

/**
 * 描述：网络请求的实体类基类
 */
public class BaseBean implements Serializable {

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String toFormatJson() {
        return JsonFormatUtils.format(toJson());
    }
}