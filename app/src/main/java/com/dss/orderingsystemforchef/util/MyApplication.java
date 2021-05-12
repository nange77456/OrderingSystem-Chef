package com.dss.orderingsystemforchef.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.dss.orderingsystemforchef.R;
import com.kongzue.dialog.util.DialogSettings;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;

public class MyApplication extends Application {


    public static final int SDK_APP_ID = 1400500290;
    public static final String SECRETKEY = "de9d6552114b3446a5d24f84b2f23a0a34b4719f51bb4831b2be38753603902c";

    /**
     * 全局context
     */
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initIMSDK();
    }

    private void initIMSDK() {
        V2TIMSDKConfig config = new V2TIMSDKConfig();
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_INFO);
        V2TIMManager.getInstance().initSDK(context, SDK_APP_ID, config,
                new V2TIMSDKListener() {
                    @Override
                    public void onConnecting() {
                        super.onConnecting();
                        // 正在连接腾讯云服务器
                        Log.e("tag","正在连接腾讯云服务器");
                    }

                    @Override
                    public void onConnectSuccess() {
                        super.onConnectSuccess();
                        // 成功连接腾讯云服务器
                        Log.e("tag","成功连接腾讯云服务器");
                    }

                    @Override
                    public void onConnectFailed(int code, String error) {
                        super.onConnectFailed(code, error);
                        // 连接腾讯云服务器失败
                        Log.e("tag","连接腾讯云服务器失败");
                    }

                    @Override
                    public void onUserSigExpired() {
                        super.onUserSigExpired();
                        // 登录票据过期，需要新的UserSig
                        Log.e("tag","登录票据过期，需要新的UserSig");
                    }
                });
    }
}
