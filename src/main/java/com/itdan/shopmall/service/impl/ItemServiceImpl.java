package com.itdan.shopmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdan.shopmall.dao.TbItemCatMapper;
import com.itdan.shopmall.dao.TbItemDescMapper;
import com.itdan.shopmall.dao.TbItemMapper;
import com.itdan.shopmall.entity.*;
import com.itdan.shopmall.service.ItemService;
import com.itdan.shopmall.utils.common.IDUtils;
import com.itdan.shopmall.utils.result.EasyUIDataGridResult;
import com.itdan.shopmall.utils.result.EasyUITreeNode;
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
    public void addItem(TbItem tbItem, String desc) {
        //为商品生产唯一的id
        long itemId=IDUtils.genItemId();
        //保存id
        tbItem.setCid(itemId);
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
}
