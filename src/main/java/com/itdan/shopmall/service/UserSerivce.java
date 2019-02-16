package com.itdan.shopmall.service;

import com.itdan.shopmall.entity.TbUser;
import com.itdan.shopmall.utils.result.ShopMallResult;

/**
 * 用户业务逻辑处理接口
 */
public interface UserSerivce {

    /**
     * 用户注册信息检查
     * @param param 检查数据的参数
     * @param type  检查数据的类型(type类型:1为用户名，2为手机号，3为邮箱)
     * @return
     */
   ShopMallResult registerCheck(String param,Integer type);

    /**
     * 用户注册提交信息操作
     * @param tbUser 注册的用户对象
     * @return
     */
   ShopMallResult register(TbUser tbUser);

    /**
     * 用户登录操作
     * @param username 登录的用户用户名
     * @param password 登录的用户密码
     * @return
     */
   ShopMallResult login(String username, String password);
}
