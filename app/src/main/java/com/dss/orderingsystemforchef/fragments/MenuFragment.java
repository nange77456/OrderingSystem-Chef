package com.dss.orderingsystemforchef.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.dss.orderingsystemforchef.entity.GroupItem;
import com.dss.orderingsystemforchef.network.simplify.MCallback;
import com.dss.orderingsystemforchef.network.results.GroupListResult;
import com.dss.orderingsystemforchef.network.results.Result;
import com.dss.orderingsystemforchef.network.simplify.ServiceCreator;
import com.dss.orderingsystemforchef.util.phone.Phone1;
import com.google.gson.Gson;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.InputDialog;
import com.kongzue.dialog.v3.MessageDialog;

import java.util.LinkedList;
import java.util.List;

public class MenuFragment extends Fragment {
    private final String TAG = "MenuFragment";
    private Context context;
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
     * 当前显示的分组的在列表中的位置
     */
    private int curGroupPosition = 0;

    /**
     * 网络请求接口的实例
     */
    DishService dishService = ServiceCreator.createService(DishService.class);
    /**
     * 网络请求结束前显示的进度条
     */
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_menu, container, false);
        // 视图
        RecyclerView sidebarRecyclerView = root.findViewById(R.id.sideBar);
        RecyclerView dishesRecyclerView = root.findViewById(R.id.dishesList);
        TextView addGroupBtn = root.findViewById(R.id.addGroup);
        TextView addItemBtn = root.findViewById(R.id.addItem);
        progressBar = root.findViewById(R.id.progressBar);
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


        // 各种点击监听的定义

        // 菜单栏子项的点击回调方法的定义，刷新右侧菜品列表
        sidebarAdapter.setSidebarClickPhone(new Phone1<Integer>() {
            @Override
            public void onPhone(Integer position) {
                refreshDishesData(sidebarData.get(position).getId());
                curGroupPosition = position;
            }
        });
        // 菜单栏子项的长按回调方法的定义，删除分组
        sidebarAdapter.setSidebarLongClickPhone(new Phone1<Integer>() {
            @Override
            public void onPhone(Integer position) {
                MessageDialog.show((AppCompatActivity) getActivity(), "删除分组", "确定要删除 “" + sidebarData.get(position).getName()
                        + "” 分组和分组下的所有菜品吗？", "确定","取消")
                        .setOnOkButtonClickListener(new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                // 发送删除的网络请求
                                dishService.deleteGroup(sidebarData.get(position).getId())
                                        .enqueue(new MCallback<Result>() {
                                            @Override
                                            public void onSuccess(Result result) {
                                                // 更新内存
                                                sidebarData.remove((int) position);
                                                sidebarAdapter.notifyItemRemoved(position);
                                                // 提示完成
                                                Toast.makeText(context , "iOrder: 删除分组成功！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                return false;
                            }
                        });
            }
        });

        // 菜品子项的单击回调接口的定义，跳转 DishesEditingActivity 页面
        dishesAdapter.setDishClickPhone(new Phone1<Integer>() {
            @Override
            public void onPhone(Integer position) {
//                Log.i(TAG, "onPhone: 点击的这一项是："+dishesData.get(position).getName() );
                Intent jumpIntent = new Intent(context, DishesEditingActivity.class);
                jumpIntent.putExtra("function", "modify");
                jumpIntent.putExtra("curDish", dishesData.get(position));

                jumpIntent.putExtra("groupItemList", new Gson().toJson(sidebarData));
                jumpIntent.putExtra("curGroupPosition", curGroupPosition);

                startActivity(jumpIntent);
            }
        });

        // 菜品下架按钮的点击回调接口的定义，删除菜品
        dishesAdapter.setRemoveClickPhone(new Phone1<Integer>() {
            @Override
            public void onPhone(Integer position) {
                MessageDialog.show((AppCompatActivity) getActivity(), "删除菜品", "确定要删除”"
                        + dishesData.get(position).getName() + "“吗？","确定","取消")
                        .setOkButton("确定",new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                // 发送删除的网络请求
                                dishService.deleteDish(dishesData.get(position).getId())
                                        .enqueue(new MCallback<Result>() {
                                            @Override
                                            public void onSuccess(Result result) {
                                                // 更新内存
                                                dishesData.remove((int)position);
                                                dishesAdapter.notifyItemRemoved(position);
                                                // 提示完成
                                                Toast.makeText(context, "删除菜品成功！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                return false;
                            }
                        });

            }
        });
        // 添加分组按钮的点击事件
        addGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用第三方输入对话框
                InputDialog.show((AppCompatActivity)getActivity(), "新建分组", "给新的分组取个名字吧！"
                        ,"确定","取消")
                        .setOnOkButtonClickListener(new OnInputDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v, String inputText) {
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
                                return false;
                            }
                        });
            }
        });
        // 添加菜品按钮的点击事件
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDialog.show((AppCompatActivity) getActivity(), "新增菜品", "请输入新增菜品的名字",
                        "确定","取消")
                        .setOnOkButtonClickListener(new OnInputDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v, String inputText) {
                                if (!inputText.equals("")) {
                                    // 跳转菜品编辑页
                                    Intent jumpIntent = new Intent(context, DishesEditingActivity.class);
                                    jumpIntent.putExtra("dishName", inputText);
                                    jumpIntent.putExtra("function", "add");

                                    jumpIntent.putExtra("groupItemList", new Gson().toJson(sidebarData));
                                    jumpIntent.putExtra("curGroupPosition", curGroupPosition);

                                    startActivity(jumpIntent);
                                }
                                return false;
                            }
                        });

            }
        });

        return root;
    }



    @Override
    public void onStart() {
        super.onStart();

        // 菜单栏数据初始化、添加后返回刷新
        dishService.getAllGroup().enqueue(new MCallback<GroupListResult>() {
            @Override
            public void onSuccess(GroupListResult result) {
                sidebarData.clear();
                sidebarData.addAll(result.getGroupList());
                // 刷新数据
                sidebarAdapter.notifyDataSetChanged();

                // 菜品列表初始化
                if (sidebarData.size() > 0) {
                    refreshDishesData(sidebarData.get(curGroupPosition).getId());
                }
            }
        });
    }

    /**
     * 电子侧边栏的一项，更新右侧菜品数据
     *
     * @param groupId 侧边栏分组序号
     */
    private void refreshDishesData(String groupId) {
        // 显示progressbar
        progressBar.setVisibility(View.VISIBLE);

        dishesData.clear();
        dishService.getDishesInGroup(groupId)
                .enqueue(new MCallback<DishListResult>() {
                    @Override
                    public void onSuccess(DishListResult result) {
                        dishesData.addAll(result.getDishList());
                        dishesAdapter.notifyDataSetChanged();

                        // 隐藏progressbar
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }


}
