package com.itdan.shopmall.entity;

import java.util.Date;

/**  收件人信息实体类
 *   `order_id` varchar(50) NOT NULL COMMENT '订单ID',
 *   `receiver_name` varchar(20) DEFAULT NULL COMMENT '收货人全名',
 *   `receiver_phone` varchar(20) DEFAULT NULL COMMENT '固定电话',
 *   `receiver_mobile` varchar(30) DEFAULT NULL COMMENT '移动电话',
 *   `receiver_state` varchar(10) DEFAULT NULL COMMENT '省份',
 *   `receiver_city` varchar(10) DEFAULT NULL COMMENT '城市',
 *   `receiver_district` varchar(20) DEFAULT NULL COMMENT '区/县',
 *   `receiver_address` varchar(200) DEFAULT NULL COMMENT '收货地址，如：xx路xx号',
 *   `receiver_zip` varchar(6) DEFAULT NULL COMMENT '邮政编码,如：310001',
 */
public class TbOrderShipping {
    private String orderId;

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverState;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

    private Date created;

    private Date updated;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile == null ? null : receiverMobile.trim();
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState == null ? null : receiverState.trim();
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity == null ? null : receiverCity.trim();
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict == null ? null : receiverDistrict.trim();
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress == null ? null : receiverAddress.trim();
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip == null ? null : receiverZip.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}