package com.dss.orderingsystemforchef.network;

import com.dss.orderingsystemforchef.util.FileUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加存用户id的cookie的自定义拦截器
 */
public class CookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request newRequest = request.newBuilder()
                .addHeader("cookie", "userID="+FileUtil.getUserID()+";")
                .build();
        return chain.proceed(newRequest);
    }
}
