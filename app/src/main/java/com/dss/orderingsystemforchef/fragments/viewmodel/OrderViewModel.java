package com.dss.orderingsystemforchef.fragments.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dss.orderingsystemforchef.entity.Order;
import com.dss.orderingsystemforchef.network.MCallback;
import com.dss.orderingsystemforchef.network.OrderService;
import com.dss.orderingsystemforchef.network.ServiceCreator;
import com.dss.orderingsystemforchef.network.results.OrderResult;

import java.util.List;

/**
 * MVVM 架构里的数据模型，用来处理业务逻辑，连接视图层和数据层
 */
public class OrderViewModel extends ViewModel {
    /**
     * 可观察变化的订单数据类
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
     * 封装的网络请求——获取商家的所有订单
     */
    public void requestOrderData(){
        service.getOrders().enqueue(new MCallback<OrderResult>() {
            @Override
            public void onSuccess(OrderResult result) {
                // 填充数据
                orderData.setValue(result.getData());
            }
        });
    }

}
