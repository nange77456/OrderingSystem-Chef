package com.dss.orderingsystemforchef.network;

import com.dss.orderingsystemforchef.entity.Dish;
import com.dss.orderingsystemforchef.network.results.DishListResult;
import com.dss.orderingsystemforchef.network.results.GroupIDResult;
import com.dss.orderingsystemforchef.network.results.GroupListResult;
import com.dss.orderingsystemforchef.network.results.Result;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 菜品相关的的网络请求，retrofit2
 */
public interface DishService {

            /**
     * 新建分组
     * @param name 分组名
     * @return 分组id
     */
    @FormUrlEncoded
    @POST("/dish/group/add")
    Call<GroupIDResult> addGroup(@Field("name") String name);

    /**
     * 获取所有分组
     * @return
     */
    @GET("/dish/group/get")
    Call<GroupListResult> getAllGroup();

    /**
     * 获取某个分组下的所有菜品
     * @param groupID
     * @return
     */
    @GET("/dish/get")
    Call<DishListResult> getDishesInGroup(@Query("groupID") String groupID);

    /**
     * 添加菜品
     * @return
     */
    @Multipart
    @POST("/dish/add")
    Call<Result> addDish(@Part("dish") Dish dish, @Part MultipartBody.Part img);

}
