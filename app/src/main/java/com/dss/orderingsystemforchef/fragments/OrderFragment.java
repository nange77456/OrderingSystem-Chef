package com.dss.orderingsystemforchef.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.adapter.OrderAdapter;
import com.dss.orderingsystemforchef.adapter.SuggestionAdapter;
import com.dss.orderingsystemforchef.customview.ProgressView;
import com.dss.orderingsystemforchef.databinding.FragmentOrderBinding;
import com.dss.orderingsystemforchef.entity.Order;
import com.dss.orderingsystemforchef.fragments.viewmodel.OrderViewModel;

import java.util.List;

public class OrderFragment extends Fragment {
    private final String TAG = "OrderFragment";
    /**
     * fragment 持有 viewmodel
     */
    private OrderViewModel viewModel;

    /**
     * 数据列表的adapter
     */
    private OrderAdapter orderAdapter = new OrderAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);

        // recyclerview 基本设置
        orderAdapter.setEmptyView(new ProgressView(getContext()));    // 设置没数据时的圆形进度条
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 网络请求填充数据到视图
        Log.e(TAG, "onViewCreated: hhhhha28" );
        viewModel.orderData.observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                // BaseQuickAdapter 绑定数据类的方法
                orderAdapter.setNewInstance(orders);

                Log.e(TAG, "onChanged: 有数据啦："+orders );
            }
        });

    }
}
