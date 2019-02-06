package com.itdan.shopmall.entity;

import java.util.Date;

/**  用户信息实体类
 *   `username` varchar(50) NOT NULL COMMENT '用户名',
 *   `password` varchar(32) NOT NULL COMMENT '密码，加密存储',
 *   `phone` varchar(20) DEFAULT NULL COMMENT '注册手机号',
 *   `email` varchar(50) DEFAULT NULL COMMENT '注册邮箱',
 */
public class TbUser {
    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Date created;

    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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