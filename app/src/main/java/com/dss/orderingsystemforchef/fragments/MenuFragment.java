package com.dss.orderingsystemforchef.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.activities.DishesEditingActivity;
import com.dss.orderingsystemforchef.adapter.DishesAdapter;
import com.dss.orderingsystemforchef.adapter.SidebarAdapter;
import com.dss.orderingsystemforchef.entity.Dish;
import com.dss.orderingsystemforchef.util.phone.Phone1;
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.v2.InputDialog;
import com.kongzue.dialog.v2.SelectDialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MenuFragment extends Fragment {
    private final String TAG = "MenuFragment";

    private Context context;

    /**
     * fragment 绑定的布局
     */
    private View root;

    // 两个列表的 adapter
    private SidebarAdapter sidebarAdapter;
    private DishesAdapter dishesAdapter;
    // 两个列表的数据集
    private List<String> sidebarData = new LinkedList<>();
    private List<Dish> dishesData = new LinkedList<>();

    // 测试数据
    {
        sidebarData.add("分类一");
        sidebarData.add("分类二");
        sidebarData.add("分类三");

        Dish dish = new Dish();
        dish.setDescription("富含优质动物蛋白，老少咸宜");
        dish.setGroupId(2);
        List<Dish.Ingredient> ingre = new LinkedList<>();
        ingre.add(new Dish.Ingredient("包菜","/把",2));
        ingre.add(new Dish.Ingredient("包菜","/把",3));
        ingre.add(new Dish.Ingredient("包菜","/把",1));
        ingre.add(new Dish.Ingredient("包菜","/把",4));
        dish.setName("包菜烤盘");
        dish.setIngredients(ingre);
        dish.setPicUrl("http://img.inaction.fun/static/ss/25101.jpg");

        dishesData.add(dish);

        Dish dd = new Dish();
        dd.setName("回味鸡拉面");
        dd.setGroupId(0);
        dishesData.add(dd);

        Dish ddd = new Dish();
        ddd.setName("双拼鸡排饭");
        ddd.setGroupId(2);
        dishesData.add(ddd);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_menu, container, false);
        // 视图
        RecyclerView sidebarRecyclerView = root.findViewById(R.id.sideBar);
        RecyclerView dishesRecyclerView = root.findViewById(R.id.dishesList);
        TextView addGroupBtn = root.findViewById(R.id.addGroup);
        TextView addItemBtn = root.findViewById(R.id.addItem);

        //Context
        context = getContext();

        // 菜单栏列表的适配器
        sidebarAdapter = new SidebarAdapter(sidebarData);
        sidebarRecyclerView.setAdapter(sidebarAdapter);
        sidebarRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // 菜品列表的适配器
        dishesAdapter = new DishesAdapter(dishesData);
        dishesRecyclerView.setAdapter(dishesAdapter);
        dishesRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // 菜单栏的点击回调方法的定义，刷新右侧菜品列表
        sidebarAdapter.setSidebarClickPhone(new Phone1<Integer>() {
            @Override
            public void onPhone(Integer position) {
                refreshDishesData(position);
            }
        });
        // 菜单栏的长按回调方法的定义，删除分组
        sidebarAdapter.setSidebarLongClickPhone(new Phone1<Integer>() {
            @Override
            public void onPhone(Integer position) {
                SelectDialog.show(context, "删除分组", "确定要删除\""+sidebarData.get(position)+"\"分组和分组下的所有菜品吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sidebarData.remove((int)position);
                        sidebarAdapter.notifyItemRemoved(position);
                    }
                });
            }
        });

        // 添加分组按钮的点击事件
        addGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用第三方输入对话框
                InputDialog.show(context, "增加分组", "给新的分组取个名字吧", new InputDialogOkButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog, String inputText) {
                        if(!inputText.equals("")){
                            // 增加一个分组
                            sidebarData.add(inputText);
                            sidebarAdapter.notifyItemInserted(sidebarData.size()-1);
                        }
                    }
                });
            }
        });
        // 添加菜品按钮的点击事件
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDialog.show(context, "新增菜品", "请输入新增菜品的名字", new InputDialogOkButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog, String inputText) {
                        if(!inputText.equals("")){
                            // 跳转菜品编辑页
                            Intent jumpIntent = new Intent(context, DishesEditingActivity.class);
                            jumpIntent.putExtra("dishName", inputText);
                            startActivity(jumpIntent);
                        }
                    }
                });

            }
        });

        return root;
    }


    /**
     * 电子侧边栏的一项，更新右侧菜品数据
     * @param groupId 侧边栏分组序号
     */
    private void refreshDishesData(int groupId) {
        dishesData.clear();
        // dishesData = ...
        dishesData.add(new Dish());
        dishesData.add(new Dish());
        dishesData.add(new Dish());

        dishesAdapter.notifyDataSetChanged();
    }



}
