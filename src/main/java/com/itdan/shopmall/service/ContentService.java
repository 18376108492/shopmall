package com.itdan.shopmall.service;

import com.itdan.shopmall.utils.result.EasyUITreeNode;
import com.itdan.shopmall.utils.result.ShopMallResult;

import java.util.List;

/**
 * 商城后台对前台内容管理逻辑接口
 */
public interface ContentService {

    /**
     * 获取内容管理的树形结构
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getContentList(long parentId);

    /**
     * 添加内容分类管理节点的功能
     * @param parentId
     * @param name
     * @return
     */
    ShopMallResult addContentCategroy(long parentId,String name);

    /**
     * 删除内容分类管理节点的功能
     * @param nodeID
     * @return
     */
    ShopMallResult deleteContentCategroy(long nodeID);

    /**
     * 更新内容分类管理节点的功能
     * @param nodeID
     * @return
     */
    ShopMallResult updateContentCategroy(long nodeID,String name);
}
