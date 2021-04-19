package com.dss.orderingsystemforchef.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.entity.Dish;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    // 数据集
    private List<Dish.Ingredient> dataList;
    // 构造函数
    public IngredientsAdapter(List<Dish.Ingredient> dataList){
        this.dataList = dataList;
    }
    // 子项 ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView positionView;
        TextView nameView;
        TextView unitView;
        TextView needView;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            positionView = itemView.findViewById(R.id.position);
            nameView = itemView.findViewById(R.id.name);
            unitView = itemView.findViewById(R.id.unit);
            needView = itemView.findViewById(R.id.need);
            view = itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredients, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dish.Ingredient item = dataList.get(position);
        holder.positionView.setText("【"+(position+1)+"】");
        holder.nameView.setText(item.getInName());
        holder.unitView.setText(item.getInUnit());
        holder.needView.setText(item.getInNeed()+"");

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
