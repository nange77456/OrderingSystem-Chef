package com.dss.orderingsystemforchef.util.phone;

/**
 * 2个参数的回调接口
 */
public interface Phone2<T,S> {
    void onPhone(T t, S s);
}
