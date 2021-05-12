package com.dss.orderingsystemforchef.network.results;

import com.dss.orderingsystemforchef.entity.Suggestion;

import java.util.List;

public class SuggestionResult extends Result {
    private List<Suggestion> data;

    public List<Suggestion> getData() {
        return data;
    }

    public void setData(List<Suggestion> data) {
        this.data = data;
    }
}
