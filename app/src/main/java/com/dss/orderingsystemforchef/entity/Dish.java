package com.dss.orderingsystemforchef.entity;

import java.util.List;

/**
 * 一道菜
 */
public class Dish {
    /**
     * 菜品名字
     */
    private String name;
    /**
     * 图片url
     */
    private String picUrl;
    /**
     * 菜品描述
     */
    private String description;
    /**
     * 菜品价格
     */
    private float price;
    /**
     * 配料列表
     */
    private List<Ingredient> ingredients;
    /**
     * 销售量
     */
    private int sales;
    /**
     * 好评率
     */
    private float positiveRate;
    /**
     * 归属的分组id
     */
    private int groupId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public float getPositiveRate() {
        return positiveRate;
    }

    public void setPositiveRate(float positiveRate) {
        this.positiveRate = positiveRate;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * 材料列表的子项
     */
    public static class Ingredient{
        /**
         * 材料名称
         */
        private String inName;
        /**
         * 材料单位
         */
        private String inUnit;
        /**
         * 材料的需求量
         */
        private float inNeed;

        public Ingredient(String inName, String inUnit, float inNeed) {
            this.inName = inName;
            this.inUnit = inUnit;
            this.inNeed = inNeed;
        }

        public String getInName() {
            return inName;
        }

        public void setInName(String inName) {
            this.inName = inName;
        }

        public String getInUnit() {
            return inUnit;
        }

        public void setInUnit(String inUnit) {
            this.inUnit = inUnit;
        }

        public float getInNeed() {
            return inNeed;
        }

        public void setInNeed(float inNeed) {
            this.inNeed = inNeed;
        }
    }
}
