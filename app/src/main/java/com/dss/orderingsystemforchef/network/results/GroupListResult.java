package com.dss.orderingsystemforchef.network.results;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 获取所有分组信息的网络请求结果类
 */
public class GroupListResult extends Result {
    /**
     * 分组信息列表
     */
    @SerializedName("dishGroups")
    private List<GroupItem> groupList;

    public List<GroupItem> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupItem> groupList) {
        this.groupList = groupList;
    }


}
