package com.dss.orderingsystemforchef.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 从 retrofit接口到 retrofit 实现类的映射
 */
public class ServiceCreator {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://8.129.24.81:5001")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> T createService(Class<T> type) {

        return retrofit.create(type);
    }
}
