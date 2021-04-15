package com.dss.orderingsystemforchef.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VTabLayout extends ViewGroup {
    public VTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        for (int i = 0; i < getChildCount(); ++i) {
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        if (changed) {
            int count = getChildCount();
            if (count < 1) {
                return;
            }

            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                Log.e("tag",""+child.getMeasuredHeight());
                child.layout(left, top, child.getMeasuredWidth(), top + child.getMeasuredHeight());
                top += child.getMeasuredHeight();
            }
        }
    }


    public void addItem(View child){

    }
}
