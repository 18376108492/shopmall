package com.itdan.shopmall.utils.result;

/**
 * 后台商品分类的树形结果
 */
public class EasyUITreeNode {
 private long id;//节点ID
 private String text;//节点文本
 private String state;//状态

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
