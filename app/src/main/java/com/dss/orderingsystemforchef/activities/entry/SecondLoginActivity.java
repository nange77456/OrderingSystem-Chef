package com.dss.orderingsystemforchef.activities.entry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dss.orderingsystemforchef.MainActivity;
import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.network.results.LoginResult;
import com.dss.orderingsystemforchef.network.results.Result;
import com.dss.orderingsystemforchef.network.UserService;
import com.dss.orderingsystemforchef.util.FileUtil;
import com.dss.orderingsystemforchef.network.simplify.ServiceCreator;
import com.github.cirno_poi.verifyedittextlibrary.VerifyEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登录——输验证码的页面
 */
public class SecondLoginActivity extends AppCompatActivity {
    private final String TAG = "SecondLoginActivity";

    /**
     * 登录成功之后的用户ID
     */
    private String userID;
    /**
     * 用户的网络请求接口
     */
    private UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_login);

        TextView telNumberView = findViewById(R.id.telNumber);
        TextView countdownView = findViewById(R.id.countdown);
        VerifyEditText verifyEditText = findViewById(R.id.verifyCode);

        // 获取上一页输入的手机号
        Intent firstLoginIntent = getIntent();
        String tel = firstLoginIntent.getStringExtra("tel");
        telNumberView.setText(tel);

        // 发送获取验证码的网络请求
        service = ServiceCreator.createService(UserService.class);
        sendCaptcha(tel);

        // 输入验证码的倒计时
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownView.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                countdownView.setText("重新获取");
                countdownView.setTextColor(ContextCompat.getColor(SecondLoginActivity.this
                        , R.color.theme));
            }
        }.start();
        // 倒计时重新获取的点击监听
        countdownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countdownView.getText().equals("重新获取")){
                    countDownTimer.start();
                    countdownView.setTextColor(ContextCompat.getColor(SecondLoginActivity.this
                            , R.color.default_text_color));
                    // 重新发送获取验证码的网络请求
                    sendCaptcha(tel);
                }
            }
        });

        // 验证码输入结束的回调方法
        verifyEditText.setInputCompleteListener(new VerifyEditText.inputCompleteListener() {
            @Override
            public void inputComplete(VerifyEditText verifyEditText, String s) {

                // 发送登录网络请求
                service.login(tel, s).enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                        if (response.body().getCode() == 200) {
                            Toast.makeText(SecondLoginActivity.this, "iOrder: 登录成功！", Toast.LENGTH_SHORT).show();
                            userID = response.body().getUserID();
                            Log.i(TAG, "onResponse: 登陆成功返回的用户id："+userID);
                            // 缓存用户id
                            FileUtil.saveUserID(userID);

                            // 登陆成功跳转主页
                            Intent jumpIntent = new Intent(SecondLoginActivity.this, MainActivity.class);
                            jumpIntent.putExtra("userID",userID);
                            startActivity(jumpIntent);

                            finish();    // 跳转后销毁登录页面

                        }else {
                            Toast.makeText(SecondLoginActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {

                    }
                });


            }
        });



    }

    /**
     * 发送验证码的网络请求的封装
     * @param tel
     */
    private void sendCaptcha(String tel){
        service.getCaptcha(tel).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(SecondLoginActivity.this, "iOrder: 验证码发送成功!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SecondLoginActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
}