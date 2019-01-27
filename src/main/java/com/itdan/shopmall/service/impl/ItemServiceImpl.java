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
import com.itdan.shopmall.utils.result.EasyUIDataGridResult;
import com.itdan.shopmall.utils.result.EasyUITreeNode;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        long itemId=IDUtils.genItemId();
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
    public TbItemDesc queryItemDesc(long itemId) {
        //添加查询条件
        TbItemDescExample itemDescExample=new TbItemDescExample();
        TbItemDescExample.Criteria criteria=itemDescExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //查询商品描述
       List<TbItemDesc> tbItemDesc =tbItemDescMapper.selectByExample(itemDescExample);
       return tbItemDesc!=null&&tbItemDesc.size()>0?tbItemDesc.get(0):null;
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
}
