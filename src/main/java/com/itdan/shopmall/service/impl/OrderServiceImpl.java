package com.itdan.shopmall.service.impl;

import com.itdan.shopmall.dao.TbOrderItemMapper;
import com.itdan.shopmall.dao.TbOrderMapper;
import com.itdan.shopmall.dao.TbOrderShippingMapper;
import com.itdan.shopmall.entity.OrderInfo;
import com.itdan.shopmall.entity.TbOrderItem;
import com.itdan.shopmall.entity.TbOrderShipping;
import com.itdan.shopmall.service.OrderService;
import com.itdan.shopmall.utils.jedis.JedisClient;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单处理服务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_ID_GEN_KEY]")
    private String ORDER_ID_GEN_KEY;

    @Value("${ORDER_INITIALIZATION_ID}")
    private String ORDER_INITIALIZATION_ID;

    @Value("${ORDER_ITEM_ID_GEN_KEY}")
    private String ORDER_ITEM_ID_GEN_KEY;

    @Value("${ORDER_ITEM_INITIALIZATION_ID}")
    private String ORDER_ITEM_INITIALIZATION_ID;


    @Override
    public ShopMallResult createOrder(OrderInfo orderInfo) {
        //生成订单ID，使用redis的incr生成
        //判断redis中是否包含key值
        if(!jedisClient.exists(ORDER_ID_GEN_KEY)){
            //如果不包括就给其初始化一个
            jedisClient.set(ORDER_ID_GEN_KEY,ORDER_INITIALIZATION_ID);
        }
        String orderId=jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        // 并补全所有信息
        orderInfo.setOrderId(orderId);
        //'状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        //插入订单表
        tbOrderMapper.insert(orderInfo);

        List<TbOrderItem> orderItemList=orderInfo.getOrderItems();
        //遍历订单表中的商品对象
        for(TbOrderItem orderItem:orderItemList){
            //使用redis生成订单明细ID
            if(!jedisClient.exists(ORDER_ITEM_ID_GEN_KEY)){
                jedisClient.set(ORDER_ITEM_ID_GEN_KEY,ORDER_ITEM_INITIALIZATION_ID);
            }
           String orderItemId= jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
            //补全数据
            orderItem.setId(orderId);
            orderItem.setItemId(orderItemId);
            //向订单明细表中插入数据
            tbOrderItemMapper.insert(orderItem);
        }
        //向订单物流表中插入数据
        TbOrderShipping orderShipping=orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        tbOrderShippingMapper.insert(orderShipping);
        //返回
        return ShopMallResult.ok(orderId);
    }
}
