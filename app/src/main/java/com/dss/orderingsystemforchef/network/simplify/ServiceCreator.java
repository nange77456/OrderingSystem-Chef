package com.dss.orderingsystemforchef.network.simplify;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 从 retrofit接口到 retrofit 实现类的映射
 */
public class ServiceCreator {
    /**
     * 写 client 为了加Cookie拦截器
     */
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new CookieInterceptor())
            .build();


    private static Retrofit retrofit = new Retrofit.Builder()
            // 加 Cookie 拦截器
            .client(client)
            .baseUrl(NetworkUtil.BASE_URL)
            // 加 gson 解析器
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * 通用的网络接口转换方法
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T createService(Class<T> type) {

        return retrofit.create(type);
    }
}
