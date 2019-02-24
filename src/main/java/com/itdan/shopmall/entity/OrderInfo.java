package com.itdan.shopmall.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用于订单界面展示的实体类
 */
public class OrderInfo extends TbOrder implements Serializable {

    private List<TbOrderItem> orderItems;//订单中商品列表
    private TbOrderShipping orderShipping;//订单收件人的信息

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
