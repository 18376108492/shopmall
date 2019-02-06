package com.itdan.shopmall.entity;

import java.util.Date;

/*      内容注释实体类
        `id` bigint(20) NOT NULL AUTO_INCREMENT,
        `category_id` bigint(20) NOT NULL COMMENT '内容类目ID',
        `title` varchar(200) DEFAULT NULL COMMENT '内容标题',
        `sub_title` varchar(100) DEFAULT NULL COMMENT '子标题',
        `title_desc` varchar(500) DEFAULT NULL COMMENT '标题描述',
        `url` varchar(500) DEFAULT NULL COMMENT '链接',
        `pic` varchar(300) DEFAULT NULL COMMENT '图片绝对路径',
        `pic2` varchar(300) DEFAULT NULL COMMENT '图片2',
        `content` text COMMENT '内容',*/
public class TbContent {
    private Long id;

    private Long categoryId;

    private String title;

    private String subTitle;

    private String titleDesc;

    private String url;

    private String pic;

    private String pic2;

    private Date created;

    private Date updated;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc == null ? null : titleDesc.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2 == null ? null : pic2.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}