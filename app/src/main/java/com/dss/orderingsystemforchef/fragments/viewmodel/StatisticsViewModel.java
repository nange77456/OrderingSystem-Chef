package com.dss.orderingsystemforchef.fragments.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dss.orderingsystemforchef.network.OrderService;
import com.dss.orderingsystemforchef.network.results.StatisticsResult;
import com.dss.orderingsystemforchef.network.simplify.MCallback;
import com.dss.orderingsystemforchef.network.simplify.ServiceCreator;

import java.util.Calendar;

public class StatisticsViewModel extends ViewModel {

    private OrderService service = ServiceCreator.createService(OrderService.class);

    public MutableLiveData<Long> startTime = new MutableLiveData<>();

    public MutableLiveData<StatisticsResult> statisticsResult = new MutableLiveData<>();

    {
        setStartTime(System.currentTimeMillis());
    }

    public void setStartTime(Long time){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        startTime.setValue(c.getTimeInMillis());
    }

    public void getStatisticsData(){
        service.getStatistics(startTime.getValue()).enqueue(new MCallback<StatisticsResult>() {
            @Override
            public void onSuccess(StatisticsResult result) {
                statisticsResult.setValue(result);
            }
        });
    }

}
