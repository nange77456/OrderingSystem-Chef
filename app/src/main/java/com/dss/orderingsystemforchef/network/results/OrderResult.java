package com.dss.orderingsystemforchef.network.results;

import com.dss.orderingsystemforchef.entity.Order;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResult extends Result {
    @SerializedName("orders")
    private List<Order> data;

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }
}
