package com.dss.orderingsystemforchef.adapter;

import android.provider.ContactsContract;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.entity.Order;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单页适配器
 */
public class OrderAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {
    // 定义时间格式
    private SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

    private OrderDishDetailAdapter detailAdapter = new OrderDishDetailAdapter();

    public OrderAdapter() {
        super(R.layout.item_order);
        // 添加按钮点击事件
        addChildClickViewIds(R.id.finishBtn);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Order order) {
        baseViewHolder.setText(R.id.id, order.getTakeFoodCode());
        baseViewHolder.setText(R.id.status, order.getStatus() == 0 ? "已完成" : "进行中");

        baseViewHolder.setText(R.id.createTime, sdf1.format(new Date(order.getCreateTime())));
        if (order.getFinishTime() == 0) {
            baseViewHolder.getView(R.id.finishTimeTip).setVisibility(View.INVISIBLE);
        }else{
            baseViewHolder.getView(R.id.finishTimeTip).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.finishTimeTip, sdf2.format(new Date(order.getFinishTime()))+" 完成出餐，用时 "
                    +sdf2.format(new Date(order.getFinishTime() - order.getCreateTime())));
        }

        baseViewHolder.setText(R.id.dishNum, order.getDishData().size()+" 道菜品");
        baseViewHolder.setText(R.id.price, "￥"+order.getTotalPrice());

        // TODO BUG 全一样
        RecyclerView detailRecycler = ((RecyclerView)baseViewHolder.getView(R.id.dishRecycler));
        detailRecycler.setAdapter(detailAdapter);
        detailRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        detailAdapter.setNewInstance(order.getDishData());

    }
}


// TODO 登录后不应该返回登陆页面