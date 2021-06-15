package com.dss.orderingsystemforchef.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.activities.entry.EntryActivity;
import com.dss.orderingsystemforchef.databinding.FragmentFlowBinding;
import com.dss.orderingsystemforchef.fragments.viewmodel.StatisticsViewModel;
import com.dss.orderingsystemforchef.network.results.StatisticsResult;
import com.dss.orderingsystemforchef.network.simplify.NetworkUtil;
import com.dss.orderingsystemforchef.util.FileUtil;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FlowFragment extends Fragment {
    private final static String TAG = "FlowFragment";

    private FragmentFlowBinding binding;


    private StatisticsViewModel viewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(StatisticsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFlowBinding.inflate(inflater, container, false);
        viewModel.getStatisticsData();


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("选择日期")
                        .build();
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
//                        Log.e("tag",(long)selection+"");
                        viewModel.setStartTime((Long)selection);
                        refresh();
                    }
                });
                datePicker.show(getFragmentManager(),"hhh");
            }
        });

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        viewModel.startTime.observe(getViewLifecycleOwner(),time->{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
            binding.timeText.setText(sdf.format(time));
        });

        viewModel.statisticsResult.observe(this, statisticsResult -> {
            binding.saleText.setText("销售总额：" + statisticsResult.getTurnover() + "元");
            binding.orderSumText.setText("订单总数："+statisticsResult.getOrderSum());

            // 前三名菜品
            List<StatisticsResult.SaleTopDish> topDishes = statisticsResult.getSaleTop();
            StringBuilder saleTopText = new StringBuilder("今日菜品销售前三名分别是：");
            for (int i = 0; i < topDishes.size(); ++i) {
                switch (i) {
                    case 1: {
                        Glide.with(this)
                                .load(NetworkUtil.BASE_URL + topDishes.get(i).getPic())
                                .into(binding.top1);
                        saleTopText.append(topDishes.get(i).getName());
                    }
                    case 2: {
                        Glide.with(this)
                                .load(NetworkUtil.BASE_URL + topDishes.get(i).getPic())
                                .into(binding.top2);
                        saleTopText.append("、").append(topDishes.get(i).getName());

                    }
                    case 3: {
                        Glide.with(this)
                                .load(NetworkUtil.BASE_URL + topDishes.get(i).getPic())
                                .into(binding.top3);
                        saleTopText.append("、").append(topDishes.get(i).getName());
                    }
                }
            }
            saleTopText.append("。");
            binding.saleTopText.setText(saleTopText);
            // 就餐时间条形图
            setBarChart(statisticsResult.getSaleTimeData());

        });
    }

    private void setBarChart(int[] saleTimeData) {
        binding.barChart.setDrawGridBackground(false);
        binding.barChart.setScaleEnabled(false);
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getAxisLeft().setDrawGridLines(false);
        binding.barChart.getAxisLeft().setEnabled(false);
        binding.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.barChart.getXAxis().setDrawGridLines(false);
        binding.barChart.getLegend().setEnabled(false);

        // 设置数据
        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < saleTimeData.length; ++i) {
            entries.add(new BarEntry(i + 1, saleTimeData[i]));
        }
        BarDataSet dataSet = new BarDataSet(entries, "label");
        // 柱子的颜色
        dataSet.setColor(ContextCompat.getColor(requireContext(), R.color.theme));
        dataSet.setHighlightEnabled(false);

        BarData barData = new BarData(dataSet);

        binding.barChart.setData(barData);
        binding.barChart.setVisibleXRangeMaximum(8);

        barData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) value + "人";
            }
        });

        // 定义x轴坐标显示
        binding.barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if ((int) value % 2 == 0) {
                    Log.e("tag", "1," + value);
                    return "";
                } else {
                    Log.e("tag", "2," + value);
                    return "" + ((int) value / 2) + "点";
                }
            }
        });

        binding.barChart.invalidate();

    }

    /**
     * 刷新
     */
    private void refresh(){
        viewModel.getStatisticsData();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}