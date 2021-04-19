package com.dss.orderingsystemforchef.network;

import com.dss.orderingsystemforchef.network.results.LoginResult;
import com.dss.orderingsystemforchef.network.results.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Retrofit2 网络请求需要用到的接口
 */
public interface UserService {
    /**
     * 获取验证码
     * @param tel 手机号
     * @return 验证码
     */
    @GET("/captcha/send")
    Call<Result> getCaptcha(@Query("tel") String tel);

    /**
     * 用户登录
     * @param tel 手机号
     * @param verifyCode 验证码
     * @return 登录结果
     */
    @FormUrlEncoded
    @POST("/user/login")
    Call<LoginResult> login(@Field("tel") String tel, @Field("verifyCode") String verifyCode);

    /**
     * 新建分组
     * @param name 分组名
     * @return 分组id
     */
    @FormUrlEncoded
    @POST("/dish/group/add")
    Call<Result> addGroup(@Field("name") String name);

    Call<Result> addDish();
}
