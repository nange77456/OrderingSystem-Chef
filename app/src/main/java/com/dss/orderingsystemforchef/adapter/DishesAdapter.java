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
import com.dss.orderingsystemforchef.network.simplify.NetworkUtil;
import com.dss.orderingsystemforchef.util.phone.Phone1;

import java.util.List;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {
    private final String TAG = "DishesAdapter";
    /**
     * 菜品列表，通过构造函数传入
     */
    private List<Dish> dataList;

    public DishesAdapter(List<Dish> dataList) {
        this.dataList = dataList;
    }

    /**
     * 子项单击回调接口
     */
    private Phone1<Integer> dishClickPhone;

    public void setDishClickPhone(Phone1<Integer> dishClickPhone) {
        this.dishClickPhone = dishClickPhone;
    }

    /**
     * 下加按钮点击回调接口
     */
    private Phone1<Integer> removeClickPhone;

    public void setRemoveClickPhone(Phone1<Integer> removeClickPhone) {
        this.removeClickPhone = removeClickPhone;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView imageView;
        TextView nameView;
        TextView ingredientsView;
        TextView priceView;
        TextView posRateView;
        Button removeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pic);
            nameView = itemView.findViewById(R.id.name);
            ingredientsView = itemView.findViewById(R.id.ingredients);
            priceView = itemView.findViewById(R.id.price);
            posRateView = itemView.findViewById(R.id.positiveRate);
            removeBtn = itemView.findViewById(R.id.removeBtn);
            view = itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dishes,parent,false);
        ViewHolder holder = new ViewHolder(view);

        // 子项的点击事件，传递点击的位置给使用它的地方
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dishClickPhone.onPhone(holder.getAdapterPosition());
            }
        });

        // 下架按钮的点击事件，传递位置
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeClickPhone.onPhone(holder.getAdapterPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dish item = dataList.get(position);
        holder.nameView.setText(item.getName());
        holder.priceView.setText("￥"+item.getPrice());
        holder.posRateView.setText("好评度"+item.getPositiveRate());
        if (item.getIngredients() != null) {
            holder.ingredientsView.setText(item.getIngredients().toString());
        }

        // 用 Glide 加载图片
        if(item.getPicUrl() != null){
            Glide.with(holder.view)
                    .load(NetworkUtil.BASE_URL+item.getPicUrl())
                    .into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
