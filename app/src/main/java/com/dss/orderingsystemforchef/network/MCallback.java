package com.dss.orderingsystemforchef.network;

import android.util.Log;
import android.widget.Toast;

import com.dss.orderingsystemforchef.network.results.Result;
import com.dss.orderingsystemforchef.util.MyApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 网络请求中Callback的封装
 */
public abstract class MCallback <T extends Result> implements Callback<T> {
    private final String TAG = "MCallback";

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()){
            T result = response.body();
            if (result.getCode() == 200) {
                // 回调
                onSuccess(result);
            }else {
                onError(result);
            }
        } else{
            Log.d(TAG, "响应失败，onResponse: result为null");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Toast.makeText(MyApplication.context, "iOrder：请求失败！", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "请求失败，onFailure() called with: call = [" + call + "], t = [" + t + "]");
    }

    /**
     * 响应成功（200）的回调
     * @param result code为200的响应结果
     */
    public abstract void onSuccess(T result);

    /**
     * 响应失败
     * @param result code不为200的响应结果
     */
    private void onError(T result){
        Toast.makeText(MyApplication.context, "iOrder："+result.getMsg(), Toast.LENGTH_SHORT).show();
    }
}
