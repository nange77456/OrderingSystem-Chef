package com.dss.orderingsystemforchef.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.orderingsystemforchef.util.phone.Phone1;
import com.dss.orderingsystemforchef.util.UnitConvert;

import java.util.List;

/**
 * 菜品页侧边栏，分类列表
 */
public class SidebarAdapter extends RecyclerView.Adapter<SidebarAdapter.ViewHolder> {

    private final String TAG = "SidebarAdapter";

    private List<String> dataList;

    private int selectedItem = -1;

    public SidebarAdapter(List<String> dataList){
        this.dataList = dataList;
    }

    // 单击回调
    private Phone1<Integer> sidebarClickPhone;
    // 长按回调
    private Phone1<Integer> sidebarLongClickPhone;

    public void setSidebarClickPhone(Phone1<Integer> sidebarClickPhone){
        this.sidebarClickPhone = sidebarClickPhone;
    }
    public void setSidebarLongClickPhone(Phone1<Integer> sidebarLongClickPhone){
        this.sidebarLongClickPhone = sidebarLongClickPhone;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setPadding(UnitConvert.dp2px(10),UnitConvert.dp2px(10)
                ,UnitConvert.dp2px(10),UnitConvert.dp2px(10));
//        textView.setBackgroundResource(R.drawable.ripple_red);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));

        ViewHolder holder = new ViewHolder(textView);
        // 菜单栏子项点击事件
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sidebarClickPhone.onPhone(holder.getAdapterPosition());
                // 改变点击的子项的背景色
                selectedItem = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });
        // 菜单子项长按删除
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sidebarLongClickPhone.onPhone(holder.getAdapterPosition());
                return true;
            }
        });
        return holder;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = dataList.get(position);
        holder.textView.setText(item);
        // 设置背景颜色
        if (position == selectedItem) {
            holder.textView.setBackgroundColor(Color.WHITE);
        }else {
            holder.textView.setBackgroundColor(Color.parseColor("#00000000"));
        }

    }




}
