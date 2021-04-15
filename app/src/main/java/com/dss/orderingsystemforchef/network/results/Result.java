package com.dss.orderingsystemforchef.network.results;

/**
 * 通用的网络请求返回结果
 */
public class Result {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
