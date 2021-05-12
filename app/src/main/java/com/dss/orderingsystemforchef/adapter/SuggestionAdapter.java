package com.dss.orderingsystemforchef.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.entity.Suggestion;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 反馈页适配器
 */
public class SuggestionAdapter extends BaseQuickAdapter<Suggestion, BaseViewHolder> {

    public SuggestionAdapter() {
        super(R.layout.item_suggestion);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Suggestion suggestion) {
        baseViewHolder.setText(R.id.msg,suggestion.getSuggestion());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm");
        baseViewHolder.setText(R.id.time,sdf.format(new Date(suggestion.getTimestamp())));
    }
}
