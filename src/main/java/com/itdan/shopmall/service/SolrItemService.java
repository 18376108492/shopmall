package com.itdan.shopmall.service;


import com.itdan.shopmall.utils.result.ShopMallResult;

/**
 * Solr查询商品业务逻辑接口
 */
public interface SolrItemService {

    /**
     * 导入所有商品
     * @return
     */
    ShopMallResult importAllItem();


}
