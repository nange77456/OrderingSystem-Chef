package com.dss.orderingsystemforchef.network;

import com.dss.orderingsystemforchef.network.results.SuggestionResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SuggestionService {


    /**
     * 获取店铺所有评论
     * @return
     */
    @GET("/suggestion/get")
    Call<SuggestionResult> getSuggestions();

}
