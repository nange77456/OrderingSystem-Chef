package com.dss.orderingsystemforchef.customview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.util.UnitConvert;

/**
 * 带返回按钮的 Toolbar
 */
public class BackToolbar extends Toolbar {
    public BackToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 设置返回图标
        setNavigationIcon(R.drawable.ic_back);
        // 设置标题和图标之间的距离
        setContentInsetStartWithNavigation(UnitConvert.px2dp(24));
        // 设置返回监听
        setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof Activity){
                    ((Activity)context).finish();
                }
            }
        });
        // 设置EntryActivity的toolbar的高度为44dp
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UnitConvert.dp2px(44)));

    }

}
