package com.itdan.shopmall.entity;

import java.util.Date;

/*      商品描述实体类
        `item_id` bigint(20) NOT NULL COMMENT '商品ID',
        `item_desc` text COMMENT '商品描述',
        `created` datetime DEFAULT NULL COMMENT '创建时间',
        `updated` datetime DEFAULT NULL COMMENT '更新时间',*/
public class TbItemDesc {
    private Long itemId;

    private Date created;

    private Date updated;

    private String itemDesc;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc == null ? null : itemDesc.trim();
    }
}