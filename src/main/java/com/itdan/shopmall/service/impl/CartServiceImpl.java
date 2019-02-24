package com.itdan.shopmall.service.impl;

import com.itdan.shopmall.dao.TbItemMapper;
import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.service.CartService;
import com.itdan.shopmall.utils.common.JsonUtils;
import com.itdan.shopmall.utils.jedis.JedisClient;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车业务逻辑实现类
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbItemMapper tbItemMapper;
    @Value("${CART_KEY}")
    private String CART_KEY;


    @Override
    public ShopMallResult addCart(long userId, long itemId, Integer num) {
        //向reids中添加缓存
        //判断hash值是否已经存在
        //数据类型为hash, key：用户ID， filed：商品ID，value：商品信息
        //判断商品是否存在
        Boolean hexists= jedisClient.hexists(CART_KEY+":"+userId,itemId+"");
        //如果商品存在，数量相加
        if(hexists){
             String json= jedisClient.hget(CART_KEY+":"+userId,itemId+"");
             //将json数据类型转换成Tbitem
             TbItem tbitem=JsonUtils.jsonToPojo(json,TbItem.class);
             tbitem.setNum(tbitem.getNum()+num);
             //将修改后的商品对象在存回数据库中
             jedisClient.hset(CART_KEY+":"+userId,itemId+"",JsonUtils.objectToJson(tbitem));
             return ShopMallResult.ok();
        }
        //如果不存在，先获取商品，在添加到购物车类表
        TbItem item= tbItemMapper.selectByPrimaryKey(itemId);
        //设置商品数量
        item.setNum(num);
        //取一张图片
        String[] img= item.getImage().split(",");
        if(img!=null) {
            item.setImage(img[0]);
        }
        //添加到redis缓存中
        jedisClient.hset(CART_KEY+":"+userId,itemId+"",JsonUtils.objectToJson(item));
        //返回状态
        return ShopMallResult.ok();
    }

    @Override
    public ShopMallResult mergeCart(long userId, List<TbItem> cartList) {
        //遍历商品列表
        //判断商品是否存在
        //如果存在数量相加
        //如果不存在就添加新商品
        if(cartList!=null&&cartList.size()>0) {
            for (TbItem tbItem : cartList) {
                addCart(userId, tbItem.getId(), tbItem.getNum());
            }
        }//返回状态
        return ShopMallResult.ok();
    }

    @Override
    public  List<TbItem> getCartList(long userId) {
        //根据用户id获取购物车信息
        List<String> jsonList= jedisClient.hvals(CART_KEY+":"+userId);
        List<TbItem> itemList=new ArrayList<>();
        for(String json:jsonList){
            //创建Item对象
           TbItem tbItem=JsonUtils.jsonToPojo(json,TbItem.class);
           itemList.add(tbItem);
        }
        return itemList;
    }

    @Override
    public ShopMallResult updateCartNum(long userId, long itemId, Integer num) {
       //从redis中获取购物车信息
        String json= jedisClient.hget(CART_KEY+":"+userId,itemId+"");
        //将json数据类型转换成Tbitem
        TbItem tbitem=JsonUtils.jsonToPojo(json,TbItem.class);
        //修改商品数量
        tbitem.setNum(num);
        //将修改后的数据写入redis中
        jedisClient.hset(CART_KEY+":"+userId,tbitem.getId()+"",JsonUtils.objectToJson(tbitem));
        return ShopMallResult.ok();
    }

    @Override
    public ShopMallResult deleteCartItem(long userId, long itemId) {
        //从redis中删除商品
        jedisClient.hdel(CART_KEY+":"+userId,itemId+"");
        return ShopMallResult.ok();
    }

    @Override
    public ShopMallResult clearCartItem(long userId) {
        //删除购物车
        jedisClient.del(CART_KEY+":"+userId);
        return ShopMallResult.ok();
    }
}
