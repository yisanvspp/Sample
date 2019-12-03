package com.yisan.sample.api;

import com.yisan.http.request.bean.BaseResponse;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.sample.api
 * @date：2019/12/1 0001 上午 12:17
 */
public class WanResponse<T> implements BaseResponse<T> {

    private int errorCode;
    private String errorMsg;
    private T data;

    @Override
    public int getCode() {
        return errorCode;
    }

    @Override
    public void setCode(int code) {
        this.errorCode = code;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String getMsg() {
        return errorMsg;
    }

    @Override
    public void setMsg(String msg) {
        this.errorMsg = msg;
    }
}
