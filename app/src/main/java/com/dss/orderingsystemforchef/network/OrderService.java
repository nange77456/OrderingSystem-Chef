package com.dss.orderingsystemforchef.network;

import com.dss.orderingsystemforchef.network.results.OrderResult;
import com.dss.orderingsystemforchef.network.results.Result;
import com.dss.orderingsystemforchef.network.results.StatisticsResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderService {
    /**
     * 获取店铺所有订单
     * @return
     */
    @GET("/order/restaurant/get")
    Call<OrderResult> getOrders();

    /**
     * 菜品制作完成时调用，网络请求将订单的状态变为“已完成”
     * @param orderID
     * @return
     */
    @POST("/order/done")
    Call<Result> finishOrder(@Query("orderID") String orderID);


    /**
     * 某一天的营收统计
     * @param startTime 开始时间，0点
     * @return
     */
    @FormUrlEncoded
    @POST("/order/statistics/day")
    Call<StatisticsResult> getStatistics(@Field("startTime")Long startTime);

}
