package com.itdan.shopmall.service;

import com.itdan.shopmall.entity.OrderInfo;
import com.itdan.shopmall.utils.result.ShopMallResult;

/**
 * 订单处理服务
 */
public interface OrderService {


    /**
     * 生成订单操作
     * @param orderInfo 订单对象
     * @return
     */
   ShopMallResult createOrder(OrderInfo orderInfo);

}
