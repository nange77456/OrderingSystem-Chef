package com.dss.orderingsystemforchef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dss.orderingsystemforchef.util.FileUtil;

public class RestaurantCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_code);

        ImageView codeImage = findViewById(R.id.codeImage);

        Glide.with(this)
                .load("https://api.pwmqr.com/qrcode/create/?url=restaurantID:"+ FileUtil.getUserID())
                .into(codeImage);

    }
}