package com.dss.orderingsystemforchef.network.results;

import com.dss.orderingsystemforchef.entity.Dish;

import java.util.List;

/**
 * 返回结果是菜品列表
 */
public class DishListResult extends Result {
    private List<Dish> dishList;

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }
}
