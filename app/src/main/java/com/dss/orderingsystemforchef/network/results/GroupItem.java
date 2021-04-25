package com.dss.orderingsystemforchef.network.results;

import com.google.gson.annotations.SerializedName;

/**
 * 一条分组信息，内部类
 */
public class GroupItem{
    /**
     * 分组 id
     */
    @SerializedName("_id")
    private String id;
    /**
     * 分组 name
     */
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
