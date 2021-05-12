package com.dss.orderingsystemforchef.adapter;

import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.entity.OrderDishDetail;

import org.jetbrains.annotations.NotNull;

public class OrderDishDetailAdapter extends BaseQuickAdapter<OrderDishDetail, BaseViewHolder> {
    public OrderDishDetailAdapter() {
        super(R.layout.item_order_dish_detail);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderDishDetail orderDishDetail) {
        baseViewHolder.setText(R.id.name, orderDishDetail.getDish().getName());
        baseViewHolder.setText(R.id.count, "x"+orderDishDetail.getNum());
        if (orderDishDetail.getNum() > 1) {
            baseViewHolder.setTextColor(R.id.count, Color.RED);
        } else{
            baseViewHolder.setTextColor(R.id.count, ContextCompat.getColor(getContext(), R.color.default_text_color));
        }
        baseViewHolder.setText(R.id.price, "￥"+orderDishDetail.getDish().getPrice()*orderDishDetail.getNum());
    }
}
