package com.dss.orderingsystemforchef.network;

import com.dss.orderingsystemforchef.network.results.OrderResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OrderService {
    /**
     * 获取店铺所有订单
     * @return
     */
    @GET("/order/restaurant/get")
    Call<OrderResult> getOrders();
}
