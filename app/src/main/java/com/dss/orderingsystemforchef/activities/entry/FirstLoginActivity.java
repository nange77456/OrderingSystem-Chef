package com.dss.orderingsystemforchef.activities.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dss.orderingsystemforchef.R;

/**
 * 登录——输手机号的页面
 */
public class FirstLoginActivity extends AppCompatActivity {

    private final String TAG = "FirstLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        EditText telInput = findViewById(R.id.telInput);
        // 获取焦点
        telInput.requestFocus();

        // 跳转下一页的点击监听
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpIntent = new Intent(FirstLoginActivity.this, SecondLoginActivity.class);
                String tel = telInput.getText().toString();
                if (!tel.equals("")) {
                    if (tel.length() != 11) {
                        Toast.makeText(FirstLoginActivity.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                    }else{
                        jumpIntent.putExtra("tel", tel);
                        startActivity(jumpIntent);
                    }
                }
            }
        });





    }
}