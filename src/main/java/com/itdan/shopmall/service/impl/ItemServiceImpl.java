package com.itdan.shopmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdan.shopmall.dao.TbItemCatMapper;
import com.itdan.shopmall.dao.TbItemDescMapper;
import com.itdan.shopmall.dao.TbItemMapper;
import com.itdan.shopmall.dao.TbItemParamItemMapper;
import com.itdan.shopmall.entity.*;
import com.itdan.shopmall.service.ItemService;
import com.itdan.shopmall.utils.common.IDUtils;
import com.itdan.shopmall.utils.common.JsonUtils;
import com.itdan.shopmall.utils.jedis.JedisClient;
import com.itdan.shopmall.utils.result.EasyUIDataGridResult;
import com.itdan.shopmall.utils.result.EasyUITreeNode;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品后台管理业务逻辑实现层
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    //获取JmsTemplate对象
    @Autowired
    private JmsTemplate jmsTemplate;
    //从容器中获取一个Destination对象(根据id注入)
    @Resource
    private  Destination topicDestination;
    @Autowired
    private JedisClient jedisClient;

    @Value("${RDEIS_TINE_PRE}")
    private String RDEIS_TINE_PRE;
    @Value("${ITEM_CACHE_EXPIRE}")
    private Integer ITEM_CACHE_EXPIRE;



    @Override
    public TbItem getItemById(long itemId) {
        //查询缓存
        try {
            //根据key获取缓存
            String json=jedisClient.get(RDEIS_TINE_PRE+":"+itemId+":BASE");
           if (StringUtils.isNotBlank(json)) {
               TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
               return tbItem;
           }
        }catch (Exception e){
            e.printStackTrace();
        }

        //根据主键获取商品对象
        TbItem tbItem= tbItemMapper.selectByPrimaryKey(itemId);

        //查询数据库,把结果添加到redis缓存中
        try {
            //设置缓存的key和value值
            jedisClient.set(RDEIS_TINE_PRE+":"+itemId+":BASE",JsonUtils.objectToJson(tbItem));
            //设置缓存过期时间
            jedisClient.expire(RDEIS_TINE_PRE+":"+itemId+":BASE",ITEM_CACHE_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tbItem;
    }

    @Override
    public TbItemDesc queryItemDesc(long itemId) {
        //查询缓存
        try {
            //根据key获取缓存
            String json=jedisClient.get(RDEIS_TINE_PRE+":"+itemId+":DESC");
            if (StringUtils.isNotBlank(json)) {
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //查询商品描述
        TbItemDesc tbItemDesc =tbItemDescMapper.selectByPrimaryKey(itemId);

        //查询数据库,把结果添加到redis缓存中
        try {
            //设置缓存的key和value值
            jedisClient.set(RDEIS_TINE_PRE+":"+itemId+":DESC",JsonUtils.objectToJson(tbItemDesc));
            //设置缓存过期时间
            jedisClient.expire(RDEIS_TINE_PRE+":"+itemId+":DESC",ITEM_CACHE_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tbItemDesc;
    }

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //添加查询条件
        PageHelper.startPage(page,rows);
        //执行查询
        TbItemExample tbItemExample=new TbItemExample();
        List<TbItem> tbItems=tbItemMapper.selectByExample(tbItemExample);
        //使用pageInfo封装查询参数
        PageInfo<TbItem> pageInfo=new PageInfo<>(tbItems);
        //将需要的取出
        EasyUIDataGridResult easyUIDataGridResult=new EasyUIDataGridResult();
        easyUIDataGridResult.setRows(tbItems);
        easyUIDataGridResult.setTotal((int)pageInfo.getTotal());
        return easyUIDataGridResult;
    }


    @Override
    public ShopMallResult addItem(TbItem tbItem, String desc) {
        //补全商品状态，其他的在商品添加页面获取
        //为商品生产唯一的id
        final long itemId=IDUtils.genItemId();
        //保存id
        tbItem.setId(itemId);
        //保存商品状态 :1-正常 ,2-下架,3-删除
        tbItem.setStatus((byte)1);
        //设置商品添加时间
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        tbItemMapper.insert(tbItem);

        //创建一个商品描述对象
        TbItemDesc tbItemDesc=new TbItemDesc();
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDescMapper.insert(tbItemDesc);

        //添加商品成功，并同步索引库
        //发送消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return   session.createTextMessage(itemId+"");
            }
        });

        //返回状态
        return  ShopMallResult.ok();
    }

    @Override
    public List<EasyUITreeNode> getItemCat(long parentId) {
         //查询商品类型
         TbItemCatExample example=new TbItemCatExample();
         //添加查询添加
         TbItemCatExample.Criteria criteria= example.createCriteria();
         criteria.andParentIdEqualTo(parentId);
         List<TbItemCat> itemCats=tbItemCatMapper.selectByExample(example);
         //创建一个返回集合
        List<EasyUITreeNode> treeNodes=new ArrayList<>();
         //遍历对象
         for(TbItemCat itemCat:itemCats){
             EasyUITreeNode  treeNode=new EasyUITreeNode();
             treeNode.setId(itemCat.getId());
             treeNode.setText(itemCat.getName());
             //closed代表节点下还有子节点，open表示没有
             treeNode.setState(itemCat.getIsParent()?"closed":"open");
             //将节点添加到集合中
             treeNodes.add(treeNode);
         }
          return  treeNodes;
    }

    @Override
    public List editItem(long id) {
        //根据商品id查询商品
        TbItem tbItem= tbItemMapper.selectByPrimaryKey(id);
        //获取商品类型的cid
        Long  cid= tbItem.getCid();
        //查询商品类型
        TbItemCat itemCat=tbItemCatMapper.selectByPrimaryKey(cid);
        //查询商品描述

        List list=new ArrayList();
        list.add(tbItem);
        list.add(itemCat);
        //将查询后的商品回显
           return list;
    }



    @Override
    public ShopMallResult queryItemParam(long itemId) {
        //添加查询条件
        TbItemParamItemExample itemDescExample =new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria=itemDescExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //查询商品描述
        List<TbItemParamItem> tbItemParamItems =tbItemParamItemMapper.selectByExample(itemDescExample);
        return ShopMallResult.ok(tbItemParamItems);
    }

    @Override
    public ShopMallResult deleteItem(String ids) {
        //使用逗号截取ids，获取单个商品的id
        String [] id=ids.split(",");
        //迭代出当个商品id,并且将其一一删除
        for (String i:id){
           Long itemId=Long.valueOf(i);
           tbItemMapper.deleteByPrimaryKey(itemId);
        }
        return ShopMallResult.ok();
    }

    @Override
    public ShopMallResult instockItem(String ids) {
        //使用逗号截取ids，获取单个商品的id
        String [] id=ids.split(",");
        //迭代出当个商品id,并且将其一一修改状态
        //'商品状态，1-正常，2-下架，3-删除'
        for(String i:id){
            Long itemId=Long.valueOf(i);
            //先根据id获取商品，然后修改商品状态
            TbItem tbItem=tbItemMapper.selectByPrimaryKey(itemId);
            //修改商品转态为下架形式
            tbItem.setStatus((byte)2);
            //更新商品
            tbItemMapper.updateByPrimaryKey(tbItem);
        }
        return ShopMallResult.ok();
    }

    @Override
    public ShopMallResult reshelfItem(String ids) {
        //使用逗号截取ids，获取单个商品的id
        String [] id=ids.split(",");
        //迭代出当个商品id,并且将其一一修改状态
        //'商品状态，1-正常，2-下架，3-删除'
        for(String i:id){
            Long itemId=Long.valueOf(i);
            //先根据id获取商品，然后修改商品状态
            TbItem tbItem=tbItemMapper.selectByPrimaryKey(itemId);
            //修改商品转态为下架形式
            tbItem.setStatus((byte)1);
            //更新商品
            tbItemMapper.updateByPrimaryKey(tbItem);
        }
        return ShopMallResult.ok();
    }
}
