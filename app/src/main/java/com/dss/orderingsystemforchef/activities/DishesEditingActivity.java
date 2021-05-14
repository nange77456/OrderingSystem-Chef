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

import com.bumptech.glide.Glide;
import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.adapter.IngredientsAdapter;
import com.dss.orderingsystemforchef.databinding.ActivityDishesEditingBinding;
import com.dss.orderingsystemforchef.entity.Dish;
import com.dss.orderingsystemforchef.network.DishService;
import com.dss.orderingsystemforchef.network.simplify.MCallback;
import com.dss.orderingsystemforchef.network.simplify.ServiceCreator;
import com.dss.orderingsystemforchef.entity.GroupItem;
import com.dss.orderingsystemforchef.network.results.Result;
import com.dss.orderingsystemforchef.network.simplify.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kongzue.dialog.v3.CustomDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.WaitDialog;
import com.qw.photo.CoCo;
import com.qw.photo.callback.CoCoAdapter;
import com.qw.photo.pojo.PickResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * 菜品编辑页
 */
public class DishesEditingActivity extends AppCompatActivity {
    private final String TAG = "DishesEditingActivity";
    /**
     * 视图的绑定类 ViewBinding
     */
    private ActivityDishesEditingBinding binding;

    /**
     * 添加还是修改
     */
    private boolean type;

    /**
     * 要增加的菜品实例
     */
    private Dish dish;
    /**
     * 菜品图片，可为空
     */
    MultipartBody.Part imgPart;

    /**
     * 分组列表
     */
    private List<GroupItem> groupList;
    /**
     * 材料列表
     */
    private List<Dish.Ingredient> ingredientList = new LinkedList<>();

    /**
     * 网络请求接口的实例
     */
    private DishService dishService = ServiceCreator.createService(DishService.class);
    /**
     * 选中的分组的位置
     */
    private int selectedGroupPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 用 ViewBinding 绑定视图
        binding = ActivityDishesEditingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 从 MenuFragment 获取的intent
        Intent intent = getIntent();
        // 从 Intent 获得 dish
        dish = intent.getSerializableExtra("curDish")==null ?
                new Dish() : (Dish) intent.getSerializableExtra("curDish");

        // 获取分组列表
        String groupString = intent.getStringExtra("groupItemList");
        groupList = new Gson().fromJson(groupString, new TypeToken<List<GroupItem>>(){}.getType());

        List<String> groupNameList = new LinkedList<>();
        for(GroupItem item : groupList){
            // 取所有groupName
            groupNameList.add(item.getName());
        }
        ArrayAdapter<String> groupNameAdapter = new ArrayAdapter<>(this, R.layout.item_dishes_group, groupNameList);
        binding.groupName.setAdapter(groupNameAdapter);


        // 从 intent 获取数据，显示在本页
        if(intent.getStringExtra("function").equals("add")){
            // 添加
            binding.dishName.setText(intent.getStringExtra("dishName"));
        }else{
            // 修改 modify
            // 显示传递过来的已有的dish信息
            binding.submitBtn.setText("确认修改");

            binding.dishName.setText(dish.getName());
            binding.description.setText(dish.getDescription());
            binding.price.setText(""+dish.getPrice());
            binding.salesNum.setText(""+dish.getSales());

            Glide.with(this)
                    .load(NetworkUtil.BASE_URL+dish.getPicUrl())
                    .into(binding.img);
        }

        //添加或者修改都要接收分组
        selectedGroupPosition = intent.getIntExtra("curGroupPosition",-1);
        binding.groupName.setText(groupNameList.get(selectedGroupPosition),false);
        Log.e(TAG, "onCreate: 接受的组号为："+selectedGroupPosition );

        // 材料列表
        if (dish.getIngredients() != null) {
            ingredientList.addAll(dish.getIngredients());
        }
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredientList);
        binding.ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.ingredientsRecycler.setAdapter(ingredientsAdapter);


        // 分组输入框的子项点击事件，记录最后点击的哪项
        binding.groupName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedGroupPosition = position;
            }
        });

        // 添加或修改按钮的点击事件
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 错误提示string
                String errorMsg = "";

                // 获取用户输入的数据
                String name = binding.dishName.getText().toString();
                String price = binding.price.getText().toString();
                String description = binding.description.getText().toString();

                // 用户输入的数据合理则存入dish，否则提示错误
                if(selectedGroupPosition ==-1){
                    binding.groupLayout.setError(getString(R.string.error_null));
                    errorMsg = "分组不能为空";
                }else {
                    binding.groupLayout.setError(null);
                    String groupID = groupList.get(selectedGroupPosition).getId();

//                    Log.e(TAG, "onClick: 拿到groupID："+selectedGroupPosition );
                    dish.setGroupId(groupID);
                }
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

//                dish.setIngredients(ingredientList);


                // 发出网络请求
                if(errorMsg.equals("")){

                    if(intent.getStringExtra("function").equals("add")){
                        // 发送添加菜品的网络请求
                        WaitDialog.show(DishesEditingActivity.this,"正在添加");
                        dishService.addDish(dish, imgPart).enqueue(new MCallback<Result>() {
                            @Override
                            public void onSuccess(Result result) {
                                WaitDialog.dismiss();
                                Toast.makeText(DishesEditingActivity.this, "iOrder：添加成功！", Toast.LENGTH_SHORT).show();
                                // 返回上一页，上一页要刷新
                                finish();
                            }
                        });
                    }else{
                        // 发送修改的网络请求
                        WaitDialog.show(DishesEditingActivity.this, "正在修改");

                        Log.e(TAG, "onClick: 需要被修改的菜品id："+dish.getId());
                        // TODO 如果没修改图片，imgPart肯定是空

                        dishService.modifyDish(dish, imgPart).enqueue(new MCallback<Result>() {
                            @Override
                            public void onSuccess(Result result) {
                                WaitDialog.dismiss();
                                Toast.makeText(DishesEditingActivity.this, "iOrder: 修改成功！", Toast.LENGTH_SHORT).show();
                                // 返回上一页
                                finish();
                            }
                        });

                    }
                    
                }else{
                    // 显示错误提示
                    MessageDialog.show(DishesEditingActivity.this, "错误提示", errorMsg);
                }

            }
        });


        // 图片的点击监听
        binding.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用第三方系统相册选择器
                CoCo.with(DishesEditingActivity.this)
                        .pick()
                        .start(new CoCoAdapter<PickResult>() {
                            @Override
                            public void onSuccess(PickResult pickResult) {
                                // 显示图片
                                binding.img.setImageURI(pickResult.originUri);

                                // 添加dish需要的参数之一，MultipartBody.Part
                                try {
                                    // 构造 RequestBody 需要用到的两个参数：
                                    InputStream inputStream = getContentResolver().openInputStream(pickResult.originUri);
                                    String imgType = pickResult.getLocalPath().split("\\.")[1];
                                    // 构造一个 RequestBody
                                    RequestBody imgRequestBody = new RequestBody() {
                                        @Override
                                        public MediaType contentType() {
                                            return MediaType.parse("image/" + imgType);
                                        }

                                        @Override
                                        public void writeTo(BufferedSink sink) throws IOException {
                                            try (Source source = Okio.source(inputStream)) {
                                                sink.writeAll(source);
                                            }
                                        }
                                    };
                                    // 构造并保存 MultipartBody.Part ，是实际上后端需要的数据
                                    imgPart = MultipartBody.Part.createFormData("name","fileName",imgRequestBody);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });

        // 添加一项原材料
        binding.ingredientsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹出自定义对话框，实现添加一项原材料
                CustomDialog.show(DishesEditingActivity.this, R.layout.dialog_ingredients, new CustomDialog.OnBindView() {
                    @Override
                    public void onBind(CustomDialog dialog, View v) {

                    }
                });
            }
        });

    }





}