package com.dss.orderingsystemforchef.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.adapter.IngredientsAdapter;
import com.dss.orderingsystemforchef.databinding.ActivityDishesEditingBinding;
import com.dss.orderingsystemforchef.entity.Dish;
import com.dss.orderingsystemforchef.network.DishService;
import com.dss.orderingsystemforchef.network.MCallback;
import com.dss.orderingsystemforchef.network.ServiceCreator;
import com.dss.orderingsystemforchef.network.results.GroupItem;
import com.dss.orderingsystemforchef.network.results.GroupListResult;
import com.dss.orderingsystemforchef.network.results.Result;
import com.kongzue.dialog.v2.MessageDialog;

import java.util.LinkedList;
import java.util.List;

/**
 * 菜品编辑页
 */
public class DishesEditingActivity extends AppCompatActivity {
    private final String TAG = "DishesEditingActivity";
    private final boolean ADD = true;
    private final boolean MODIFY = false;
    /**
     * 视图的绑定类 ViewBinding
     */
    private ActivityDishesEditingBinding binding;

    /**
     * 添加还是修改
     */
    private boolean type;

    /**
     * 分组列表
     */
    private List<GroupItem> groupList = new LinkedList<>();
    /**
     * 材料列表
     */
    private List<Dish.Ingredient> ingredientList = new LinkedList<>();

    /**
     * 网络请求接口的实例
     */
    private DishService dishService = ServiceCreator.createService(DishService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 用 ViewBinding 绑定视图
        binding = ActivityDishesEditingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 从 intent 获取数据，是要增加还是要修改
        Intent intent = getIntent();
        if(intent.getStringExtra("function").equals("add")){
            type = ADD;
            binding.dishName.setText(intent.getStringExtra("dishName"));
        }else{
            type = MODIFY;


        }

        // 分组列表
        List<String> groupNameList = new LinkedList<>();
        ArrayAdapter<String> groupNameAdapter = new ArrayAdapter<>(this, R.layout.item_dishes_group, groupNameList);
        binding.groupName.setAdapter(groupNameAdapter);
        // 分组列表数据
        dishService.getAllGroup()
                .enqueue(new MCallback<GroupListResult>() {
                    @Override
                    public void onSuccess(GroupListResult result) {
                        groupList.addAll(result.getGroupList());

                        for(GroupItem item : groupList){
                            groupNameList.add(item.getName());
                        }
                        groupNameAdapter.notifyDataSetChanged();
                    }
                });

        // TODO 选中一个
        if (type == MODIFY) {
//            binding.groupName.setListSelection();
        }

        // 材料列表
        // TODO intent传递一个

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredientList);
        binding.ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.ingredientsRecycler.setAdapter(ingredientsAdapter);


        // 设置错误提示
//        binding.nameLayout.setError("太长了");
        // 要增加的菜品实例
        Dish dish = new Dish();
        // 分组输入框的子项点击事件，记录最后点击的哪项
        final int[] group = {-1};
        binding.groupName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                group[0] = position;
//                dish.setGroupId(groupList.get(position).getId());
            }
        });
        // 添加或修改
        // TODO 如果是修改，改一下button的文字，以及dish
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String errorMsg = "";
                // 获取用户输入的数据
                String name = binding.dishName.getText().toString();
                String price = binding.price.getText().toString();
                String description = binding.description.getText().toString();
                if(group[0]==-1){
                    binding.groupLayout.setError(getString(R.string.error_null));
                    errorMsg = "分组不能为空";
                }else {
                    binding.groupLayout.setError(null);
                    String groupID = groupList.get(group[0]).getId();
                    dish.setGroupId(groupID);
                }
                // 合理则存入dish，否则提示错误
                if(name.equals("")){
                    binding.nameLayout.setError(getString(R.string.error_null));
                    errorMsg = "菜品名不能为空";
                }else {
                    binding.nameLayout.setError(null);
                    dish.setName(name);
                }
                if(price.equals("")){
                    binding.priceLayout.setError(getString(R.string.error_null));
                    errorMsg = "价格不能为空";
                }else {
                    binding.priceLayout.setError(null);
                    dish.setPrice(Float.parseFloat(price));
                }
                if(description.length() > 100){
                    binding.descriptionLayout.setError(getString(R.string.error_max_words));
                    errorMsg = "介绍文字的长度不能超过100";
                }else{
                    binding.descriptionLayout.setError(null);
                    dish.setDescription(description);
                }

                // TODO 处理图片
//                dish.setPicUrl();

//                dish.setIngredients(ingredientList);


                if(errorMsg.equals("")){
                    // 发送添加菜品的网络请求
                    dishService.addDish(dish, null).enqueue(new MCallback<Result>() {
                        @Override
                        public void onSuccess(Result result) {
                            Toast.makeText(DishesEditingActivity.this, "iOrder：添加成功！", Toast.LENGTH_SHORT).show();
                            // 返回上一页，上一页要刷新
                            finish();
                        }
                    });
                }else{
                    // 显示错误提示
                    MessageDialog.show(DishesEditingActivity.this, "错误提示", errorMsg);
                }

            }
        });

    }




}