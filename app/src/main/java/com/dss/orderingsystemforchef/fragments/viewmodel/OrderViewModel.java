package com.dss.orderingsystemforchef.fragments.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dss.orderingsystemforchef.entity.Order;
import com.dss.orderingsystemforchef.network.results.Result;
import com.dss.orderingsystemforchef.network.simplify.MCallback;
import com.dss.orderingsystemforchef.network.OrderService;
import com.dss.orderingsystemforchef.network.simplify.ServiceCreator;
import com.dss.orderingsystemforchef.network.results.OrderResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import java.util.List;

/**
 * MVVM 架构里的数据模型，用来处理业务逻辑，连接视图层和数据层
 */
public class OrderViewModel extends ViewModel {
    /**
     * 可观察变化的订单数据类，公有
     */
    public MutableLiveData<List<Order>> orderData = new MutableLiveData();
    /**
     * 订单的网络请求实例
     */
    private OrderService service = ServiceCreator.createService(OrderService.class);

    // 初始化块中发送网络请求
    {
        requestOrderData();
    }

    /**
     * 发送网络请求——获取商家的所有订单
     */
    public void requestOrderData() {
        service.getOrders().enqueue(new MCallback<OrderResult>() {
            @Override
            public void onSuccess(OrderResult result) {
                // 填充数据
                orderData.setValue(result.getData());
            }
        });
    }

    /**
     * 发送网络请求——完成当前订单
     *
     * @param index
     */
    public void requestFinishOrder(int index) {
        String orderID = orderData.getValue().get(index).get_id();
        service.finishOrder(orderID).enqueue(new MCallback<Result>() {
            @Override
            public void onSuccess(Result result) {
                List<Order> data = orderData.getValue();
                Order o = data.get(index);

                o.setStatus(0);
                o.setFinishTime(System.currentTimeMillis());

                orderData.setValue(data);

                sendTakeFoodMsg(o.getTakeFoodCode(),o.getCustomerID());
            }
        });
    }

    public void sendTakeFoodMsg(String code,String customID){
        String msg = "takeFood:{\"code\":\""+code+"\"}";
        V2TIMManager.getInstance().sendC2CCustomMessage(msg.getBytes(), customID,
                new V2TIMValueCallback<V2TIMMessage>() {
                    @Override
                    public void onError(int code, String desc) {

                    }

                    @Override
                    public void onSuccess(V2TIMMessage v2TIMMessage) {

                    }
                });
    }

}
