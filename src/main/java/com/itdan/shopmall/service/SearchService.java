package com.itdan.shopmall.service;

import com.itdan.shopmall.utils.result.SearchResult;

/**
 * 商品搜索业务逻辑接口
 */
public interface SearchService {

    /**
     * 根据搜索关键词，查询商品结果
     * @param keyWord 关键词
     * @param page 页码
     * @param rows 条数
     * @return
     */
    SearchResult search(String keyWord,int page,int rows) throws Exception;

}
