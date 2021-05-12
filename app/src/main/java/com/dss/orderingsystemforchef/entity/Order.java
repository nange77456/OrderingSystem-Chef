package com.dss.orderingsystemforchef.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 订单
 */
public class Order {
    private String _id;
    /**
     * 取餐码
     */
    private String takeFoodCode;

    /**
     * 订单创建时间
     */
    private long createTime;
    /**
     * 订单完成时间
     */
    private long finishTime;

    /**
     * 订单中的菜品及其数量
     */
    private List<OrderDishDetail> dishData;
    /**
     * 订单总价
     */
    @SerializedName("cost")
    private float totalPrice;

    /**
     * 订单状态
     */
    private int status;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTakeFoodCode() {
        return takeFoodCode;
    }

    public void setTakeFoodCode(String takeFoodCode) {
        this.takeFoodCode = takeFoodCode;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public List<OrderDishDetail> getDishData() {
        return dishData;
    }

    public void setDishData(List<OrderDishDetail> dishData) {
        this.dishData = dishData;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
