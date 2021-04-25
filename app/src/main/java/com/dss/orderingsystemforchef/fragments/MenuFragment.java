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
import com.dss.orderingsystemforchef.network.DishService;
import com.dss.orderingsystemforchef.network.results.DishListResult;
import com.dss.orderingsystemforchef.network.results.GroupIDResult;
import com.dss.orderingsystemforchef.network.results.GroupItem;
import com.dss.orderingsystemforchef.network.MCallback;
import com.dss.orderingsystemforchef.network.results.GroupListResult;
import com.dss.orderingsystemforchef.network.results.Result;
import com.dss.orderingsystemforchef.network.ServiceCreator;
import com.dss.orderingsystemforchef.util.phone.Phone1;
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.v2.InputDialog;
import com.kongzue.dialog.v2.SelectDialog;

import java.util.LinkedList;
import java.util.List;

public class MenuFragment extends Fragment {
    private final String TAG = "MenuFragment";
    private Context context;
    /**
     * fragment 绑定的布局
     */
    private View root;

    /**
     * 侧边分组列表的 adapter
     */
    private SidebarAdapter sidebarAdapter;
    /**
     * 分组下的菜品列表 adapter
     */
    private DishesAdapter dishesAdapter;
    /**
     * 分组列表的数据集
     */
    private List<GroupItem> sidebarData = new LinkedList<>();    // 在这new列表避免空指针问题，之后的操作都用clear和add
    /**
     * 菜品列表的数据集
     */
    private List<Dish> dishesData = new LinkedList<>();
    /**
     * 网络请求接口的实例
     */
    DishService dishService = ServiceCreator.createService(DishService.class);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_menu, container, false);
        // 视图
        RecyclerView sidebarRecyclerView = root.findViewById(R.id.sideBar);
        RecyclerView dishesRecyclerView = root.findViewById(R.id.dishesList);
        TextView addGroupBtn = root.findViewById(R.id.addGroup);
        TextView addItemBtn = root.findViewById(R.id.addItem);
        // Context
        context = getContext();

        // 分组adapter
        sidebarAdapter = new SidebarAdapter(sidebarData);
        sidebarRecyclerView.setAdapter(sidebarAdapter);
        sidebarRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        // 菜品adapter
        dishesAdapter = new DishesAdapter(dishesData);
        dishesRecyclerView.setAdapter(dishesAdapter);
        dishesRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        // 菜单栏数据初始化
        dishService.getAllGroup().enqueue(new MCallback<GroupListResult>() {
            @Override
            public void onSuccess(GroupListResult result) {
                sidebarData.addAll(result.getGroupList());
                // 刷新数据
                sidebarAdapter.notifyDataSetChanged();

                // 菜品列表初始化
                if (sidebarData.size() > 0) {
                    refreshDishesData(sidebarData.get(0).getId());
                }
            }
        });


        // 菜单栏的点击回调方法的定义，刷新右侧菜品列表
        sidebarAdapter.setSidebarClickPhone(new Phone1<Integer>() {
            @Override
            public void onPhone(Integer position) {
                refreshDishesData(sidebarData.get(position).getId());
            }
        });

        // 菜单栏的长按回调方法的定义，删除分组
        sidebarAdapter.setSidebarLongClickPhone(new Phone1<Integer>() {
            @Override
            public void onPhone(Integer position) {
                SelectDialog.show(context, "删除分组", "确定要删除 \"" + sidebarData.get(position).getName() + "\" 分组和分组下的所有菜品吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sidebarData.remove((int) position);
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
                InputDialog.show(context, "新建分组", "给新的分组取个名字吧！", new InputDialogOkButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog, String inputText) {
                        if (!inputText.equals("")) {
                            // 添加分组
                            // 1. 网络请求
                            dishService.addGroup(inputText)
                                    .enqueue(new MCallback<GroupIDResult>() {
                                        @Override
                                        public void onSuccess(GroupIDResult result) {
                                            String groupID = result.getGroupID();

                                            // 2. 更新内存
                                            GroupItem item = new GroupItem();
                                            item.setId(groupID);
                                            item.setName(inputText);
                                            sidebarData.add(item);
                                            sidebarAdapter.notifyItemInserted(sidebarData.size() - 1);
                                        }
                                    });

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
                        if (!inputText.equals("")) {
                            // 跳转菜品编辑页
                            Intent jumpIntent = new Intent(context, DishesEditingActivity.class);
                            jumpIntent.putExtra("dishName", inputText);
                            jumpIntent.putExtra("function", "add");
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
     *
     * @param groupId 侧边栏分组序号
     */
    private void refreshDishesData(String groupId) {
        dishesData.clear();
        dishService.getDishesInGroup(groupId)
                .enqueue(new MCallback<DishListResult>() {
                    @Override
                    public void onSuccess(DishListResult result) {
                        dishesData.addAll(result.getDishList());
                        dishesAdapter.notifyDataSetChanged();
                    }
                });
    }


}
