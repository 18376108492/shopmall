package com.itdan.shopmall.service;

import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.utils.result.EasyUIDataGridResult;
import com.itdan.shopmall.utils.result.EasyUITreeNode;

import java.util.List;

/**
 * 商品后台管理业务逻辑接口
 */
public interface ItemService {


    /**
     * 后台商品集合显示及分页操作
     * @param page 当前页
     * @param rows 每页显示条数
     * @return
     */
     EasyUIDataGridResult getItemList(Integer page, Integer rows);

    /**
     * 后台商品新增操作
     * @param tbItem 商品对象
     * @param desc 商品描述
     */
      void addItem(TbItem tbItem,String desc);

    /**
     * 商城后台获取商品的分类
     * @param parentId 父类节点
     * @return
     */
    List<EasyUITreeNode> getItemCat(long parentId);
}
