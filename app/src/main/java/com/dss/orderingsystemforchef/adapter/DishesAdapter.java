package com.dss.orderingsystemforchef.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.entity.Dish;

import java.util.List;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {
    private List<Dish> dataList;

    public DishesAdapter(List<Dish> dataList) {
        this.dataList = dataList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView imageView;
        TextView nameView;
        TextView ingredientsView;
        TextView priceView;
        TextView positiveRateView;
        Button editBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pic);
            nameView = itemView.findViewById(R.id.name);
            ingredientsView = itemView.findViewById(R.id.ingredients);
            priceView = itemView.findViewById(R.id.price);
            positiveRateView = itemView.findViewById(R.id.positiveRate);
            editBtn = itemView.findViewById(R.id.edit);
            view = itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dishes,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dish item = dataList.get(position);
        holder.nameView.setText(item.getName());
        holder.priceView.setText("￥"+item.getPrice());
        holder.positiveRateView.setText("好评度"+item.getPositiveRate());
        if (item.getIngredients() != null) {
            holder.ingredientsView.setText(item.getIngredients().toString());
        }

        // 用 Glide 加载图片
        if(item.getPicUrl() != null){
            Glide.with(holder.view)
                    .load(item.getPicUrl())
                    .into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
