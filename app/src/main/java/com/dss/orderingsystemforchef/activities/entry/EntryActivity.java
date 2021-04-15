package com.dss.orderingsystemforchef.activities.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dss.orderingsystemforchef.MainActivity;
import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.util.FileUtil;

/**
 * 登录前的页面
 */
public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        if (!FileUtil.getUserID().equals("-1")) {
            Intent jumpIntent = new Intent(this, MainActivity.class);
            jumpIntent.putExtra("userID", FileUtil.getUserID());
            startActivity(jumpIntent);
            finish();
        }

        // 跳转登录
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpIntent = new Intent(EntryActivity.this, FirstLoginActivity.class);
                startActivity(jumpIntent);
            }
        });

    }
}