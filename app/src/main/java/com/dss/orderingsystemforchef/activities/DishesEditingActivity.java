package com.dss.orderingsystemforchef.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.adapter.IngredientsAdapter;
import com.dss.orderingsystemforchef.databinding.ActivityDishesEditingBinding;
import com.dss.orderingsystemforchef.entity.Dish;

import java.util.LinkedList;
import java.util.List;

public class DishesEditingActivity extends AppCompatActivity {
    private final String TAG = "DishesEditingActivity";

    /**
     * 视图的绑定类 ViewBinding
     */
    private ActivityDishesEditingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 用 ViewBinding 绑定视图
        binding = ActivityDishesEditingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 选择分组需要的列表
        LinkedList<String> list = new LinkedList<>();
        list.add("林");
        list.add("俊");
        list.add("杰");
        list.add("Jelly");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_dishes_group,list);
        binding.groupName.setAdapter(adapter);

        // 材料列表
        List<Dish.Ingredient> ingredientList = new LinkedList<>();
        ingredientList.add(new Dish.Ingredient("包菜","/把",2));
        ingredientList.add(new Dish.Ingredient("包菜","/把",3));
        ingredientList.add(new Dish.Ingredient("包菜","/把",1));
        ingredientList.add(new Dish.Ingredient("包菜","/把",4));
        ingredientList.add(new Dish.Ingredient("包菜","/把",2));
        ingredientList.add(new Dish.Ingredient("包菜","/把",3));
        ingredientList.add(new Dish.Ingredient("包菜","/把",1));
        ingredientList.add(new Dish.Ingredient("包菜","/把",4));
        ingredientList.add(new Dish.Ingredient("包菜","/把",2));
        ingredientList.add(new Dish.Ingredient("包菜","/把",3));
        ingredientList.add(new Dish.Ingredient("包菜","/把",1));
        ingredientList.add(new Dish.Ingredient("包菜","/把",4));
        ingredientList.add(new Dish.Ingredient("包菜","/把",2));
        ingredientList.add(new Dish.Ingredient("包菜","/把",3));
        ingredientList.add(new Dish.Ingredient("包菜","/把",1));
        ingredientList.add(new Dish.Ingredient("包菜","/把",4));
        ingredientList.add(new Dish.Ingredient("包菜","/把",2));
        ingredientList.add(new Dish.Ingredient("包菜","/把",3));
        ingredientList.add(new Dish.Ingredient("包菜","/把",1));
        ingredientList.add(new Dish.Ingredient("包菜","/把",4));
        ingredientList.add(new Dish.Ingredient("包菜","/把",2));
        ingredientList.add(new Dish.Ingredient("包菜","/把",3));
        ingredientList.add(new Dish.Ingredient("包菜","/把",1));
        ingredientList.add(new Dish.Ingredient("包菜","/把",4));
        ingredientList.add(new Dish.Ingredient("包菜","/把",2));
        ingredientList.add(new Dish.Ingredient("包菜","/把",3));
        ingredientList.add(new Dish.Ingredient("包菜","/把",1));
        ingredientList.add(new Dish.Ingredient("包菜","/把",4));
        ingredientList.add(new Dish.Ingredient("包菜","/把",2));
        ingredientList.add(new Dish.Ingredient("包菜","/把",3));
        ingredientList.add(new Dish.Ingredient("包菜","/把",1));
        ingredientList.add(new Dish.Ingredient("包菜","/把",4));

        binding.ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.ingredientsRecycler.setAdapter(new IngredientsAdapter(ingredientList));

    }
}