package com.itdan.shopmall.utils.result;

import java.util.List;

/**
 * 商城搜索结果集
 */
public class SearchResult {

    private long recourdCount;//查询的商品总数
    private int totalPages;//总页数
    private List<SolrResult> itemList;

    public long getRecourdCount() {
        return recourdCount;
    }

    public void setRecourdCount(long recourdCount) {
        this.recourdCount = recourdCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SolrResult> getItemList() {
        return itemList;
    }

    public void setItemList(List<SolrResult> itemList) {
        this.itemList = itemList;
    }
}
