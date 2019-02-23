package com.itdan.shopmall.service;

import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.utils.result.ShopMallResult;

import java.util.List;

/**
 * 购物车业务逻辑接口
 */
public interface CartService {


    /**
     * 登录状态下添加购物车
     * @param userId 用户id
     * @param itemId 商品id
     * @param num 商品数量
     * @return
     */
    ShopMallResult addCart(long userId,long itemId,Integer num);

    /**
     * 登入状态合并购物车
     * @param userId 用户id
     * @param cartList 购物车列表
     * @return
     */
    ShopMallResult mergeCart(long userId, List<TbItem> cartList);

    /**
     * 根据用户ID获取购物车列表
     * @param userId 用户id
     * @return
     */
    List<TbItem> getCartList(long userId);

    /**
     * 更新购物车商品的数量
     * @param userId 用户id
     * @param itemId 商品id
     * @param num 商品数量
     * @return
     */
    ShopMallResult updateCartNum(long userId,long itemId,Integer num);

    /**
     * 删除购物车中的商品
     * @param userId 用户id
     * @param itemId 商品id
     * @return
     */
    ShopMallResult deleteCartItem(long userId,long itemId);
}
