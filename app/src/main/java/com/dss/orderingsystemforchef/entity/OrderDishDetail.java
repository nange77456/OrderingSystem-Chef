package com.dss.orderingsystemforchef.entity;

/**
 * 订单中每道菜品和其数量
 */
public class OrderDishDetail {
    private Dish dish;
    private int num;

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
