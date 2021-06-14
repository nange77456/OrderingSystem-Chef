package com.dss.orderingsystemforchef.network.results;

import java.util.List;

/**
 * 营收统计
 */
public class StatisticsResult extends Result{

    /**
     * 营业额
     */
    private float turnover;

    /**
     * 总订单数
     */
    private int orderSum;

    /**
     * 前一天营业额
     */
    private float lastTurnover;

    /**
     * 销量前3菜品
     */
    private List<SaleTopDish> saleTop;

    private int[] saleTimeData;

    public static class SaleTopDish{
        /**
         * 菜品id
         */
        private String id;

        /**
         * 菜品图
         */
        private String pic;

        /**
         * 菜品名
         */
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public int getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(int orderSum) {
        this.orderSum = orderSum;
    }

    public float getTurnover() {
        return turnover;
    }

    public void setTurnover(float turnover) {
        this.turnover = turnover;
    }

    public float getLastTurnover() {
        return lastTurnover;
    }

    public void setLastTurnover(float lastTurnover) {
        this.lastTurnover = lastTurnover;
    }

    public List<SaleTopDish> getSaleTop() {
        return saleTop;
    }

    public void setSaleTop(List<SaleTopDish> saleTop) {
        this.saleTop = saleTop;
    }

    public int[] getSaleTimeData() {
        return saleTimeData;
    }

    public void setSaleTimeData(int[] saleTimeData) {
        this.saleTimeData = saleTimeData;
    }
}
