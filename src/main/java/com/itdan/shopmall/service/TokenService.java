package com.itdan.shopmall.service;

import com.itdan.shopmall.entity.TbUser;
import com.itdan.shopmall.utils.result.ShopMallResult;

/**
 * 根据token获取用户信息
 */
public interface TokenService {

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    ShopMallResult getUserByToken(String token);
}
