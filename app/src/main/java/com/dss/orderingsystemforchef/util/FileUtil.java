package com.dss.orderingsystemforchef.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 文件操作工具类
 */
public class FileUtil {
    /**
     * 把用户id缓存在 SharedPreferences 文件中
     * @param id 用户id
     */
    public static void saveUserID(String id){
        SharedPreferences preferences = MyApplication.context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userID",id);
        // apply 是异步的
        editor.apply();
    }

    /**
     * 从缓存中获取用户id
     * @return 用户id
     */
    public static String getUserID(){
        SharedPreferences preferences = MyApplication.context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        return preferences.getString("userID","-1");
    }

    public static void deleteUserID(String id){
        SharedPreferences preferences = MyApplication.context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
