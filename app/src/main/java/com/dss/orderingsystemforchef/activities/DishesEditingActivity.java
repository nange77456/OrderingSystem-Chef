package com.dss.orderingsystemforchef.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.databinding.ActivityDishesEditingBinding;

import java.util.LinkedList;

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

        LinkedList<String> list = new LinkedList<>();
        list.add("林");
        list.add("俊");
        list.add("杰");
        list.add("Jelly");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_dishes_group,list);
        binding.groupName.setAdapter(adapter);


    }
}