package com.dss.orderingsystemforchef.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.dss.orderingsystemforchef.R;

public class ProgressView extends FrameLayout {
    public ProgressView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.progress_view,this,true);
    }
}
