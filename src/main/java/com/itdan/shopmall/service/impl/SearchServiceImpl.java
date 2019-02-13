package com.itdan.shopmall.service.impl;

import com.itdan.shopmall.dao.SearchDAO;
import com.itdan.shopmall.service.SearchService;
import com.itdan.shopmall.utils.result.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAData;

/**
 * 商品搜索业务逻辑实现类
 */
@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    private SearchDAO searchDAO;

    @Override
    public SearchResult search(String keyWord, int page,int rows) throws Exception{
        SolrQuery query=new SolrQuery();
        //创建查询条件
        query.set("q",keyWord);
        if (page<=0){
            page=1;
        }
        //分页设置
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //默认搜索域
        query.set("df","item_title");
        //开启高亮显示
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");

        //查询
        SearchResult searchResult= searchDAO.search(query);
        long recourCount=searchResult.getRecourdCount();
        //计算总页数
        int totalPage=(int) recourCount/rows;
        if(recourCount%rows>0){
            totalPage++;
        }
        //添加返回结果
        searchResult.setTotalPages(totalPage);
        return searchResult;
    }
}
