package com.itdan.shopmall.service;

import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.entity.TbItemDesc;
import com.itdan.shopmall.utils.result.EasyUIDataGridResult;
import com.itdan.shopmall.utils.result.EasyUITreeNode;
import com.itdan.shopmall.utils.result.ShopMallResult;

import java.util.List;

/**
 * 商品后台管理业务逻辑接口
 */
public interface ItemService {

    /**
     * 根据商品ID获取商品对象
     * @param itemId
     * @return
     */
    TbItem getItemById(long itemId);


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
     ShopMallResult  addItem(TbItem tbItem, String desc);

    /**
     * 商城后台获取商品的分类
     * @param parentId 父类节点
     * @return
     */
    List<EasyUITreeNode> getItemCat(long parentId);

    /**
     * 后台商品修改操作(查询商品并回显)
     * @param id 商品id
     * @return
     */
    List editItem(long id);

    /**
     * 查询商品描述
     * @param itemId 商品id
     * @return
     */
    TbItemDesc queryItemDesc(long itemId);

    /**
     * 查询商品规格参数
     * @param itemId 商品id
     * @return
     */
    ShopMallResult queryItemParam(long itemId);

    /**
     * 删除商品
     * @param ids 商品id数组
     * @return
     */
    ShopMallResult deleteItem(String ids);

    /**
     * 下架商品
     * @param ids 商品id数组
     * @return
     */
    ShopMallResult instockItem(String ids);

    /**
     * 下架商品
     * @param ids 商品id数组
     * @return
     */
    ShopMallResult reshelfItem(String ids);
}
