package com.itdan.shopmall.entity;

/*      购物车实体类
        `id` varchar(20) COLLATE utf8_bin NOT NULL,
        `item_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '商品id',
        `order_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '订单id',
        `num` int(10) DEFAULT NULL COMMENT '商品购买数量',
        `title` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品标题',
        `price` bigint(50) DEFAULT NULL COMMENT '商品单价',
        `total_fee` bigint(50) DEFAULT NULL COMMENT '商品总金额',
        `pic_path` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品图片地址',*/
public class TbOrderItem {
    private String id;

    private String itemId;

    private String orderId;

    private Integer num;

    private String title;

    private Long price;

    private Long totalFee;

    private String picPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }
}