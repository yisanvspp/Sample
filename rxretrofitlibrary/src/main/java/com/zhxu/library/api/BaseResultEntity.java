package com.zhxu.library.api;

/**
 * 回调信息统一封装类
 */
public class BaseResultEntity<T> {

    public int errorCode;
    public String errorMsg;
    public T data;

}
