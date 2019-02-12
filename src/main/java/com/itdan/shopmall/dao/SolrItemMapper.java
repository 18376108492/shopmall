package com.itdan.shopmall.dao;

import com.itdan.shopmall.utils.result.SearchResult;
import com.itdan.shopmall.utils.result.SolrResult;

import java.util.List;

public interface SolrItemMapper {

    /**
     * 获取所有的商品
     * @return
     */
    List<SolrResult>getAllItem();

    /**
     * 根据id获取查询信息
     * @param itemid
     * @return
     */
    SolrResult getItembyId(long itemid);

}
