package com.dss.orderingsystemforchef.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.dss.orderingsystemforchef.R;

/**
 * 向上的图标和向下的图标切换
 */
public class ToggleImage extends AppCompatImageView {
    private boolean isUpIcon = true;

    public ToggleImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 点击事件
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 切换图标
                if (isUpIcon) {
                    setImageResource(R.drawable.ic_down);
                }else {
                    setImageResource(R.drawable.ic_up);
                }
                isUpIcon = !isUpIcon;
                // TODO 收缩列表

            }
        });
    }
}
