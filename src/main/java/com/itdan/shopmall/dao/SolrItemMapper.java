package com.itdan.shopmall.dao;

import com.itdan.shopmall.utils.result.SolrResult;

import java.util.List;

public interface SolrItemMapper {

    /**
     * 获取所有的商品
     * @return
     */
    List<SolrResult>getAllItem();
}
