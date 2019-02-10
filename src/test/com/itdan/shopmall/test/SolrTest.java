package com.itdan.shopmall.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * solr测试
 */
public class SolrTest {

    //添加文本
    @Test
    public void testAddDocument() throws Exception {
        //创建一个SolrServer对象创建一个连接,参数solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
        //创建一个文档对象,SolrInputDocument对象
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域，文档中必须包含一个ID域，所有的域的名称必须在schema.xml中定义
        document.addField("id", "doc01");
        document.addField("item_title", "测试商品01");
        document.addField("item_price", 1000);
        //把文档写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }


    @Test
    public void testDeleteDocument() throws Exception {
        //创建一个SolrServer对象创建一个连接,参数solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
        //根据id删除(传入文档id)
        solrServer.deleteById("doc01");
        //查询删除
        solrServer.deleteByQuery("id:doc01");
        //提交
        solrServer.commit();
    }

    //简单查询测试
    @Test
    public void testQueryIndex() throws Exception {

        //创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
        //创建一个SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        //solrQuery.set("*:*");查询全部
        solrQuery.set("q", "*:*");
        //执行查询,QueryResponse对象
        QueryResponse response = solrServer.query(solrQuery);
        //取文档列表和总记录数
        SolrDocumentList documentList = response.getResults();
        System.out.println(documentList.getNumFound());//获取总计录数
        //遍历文档
        for (SolrDocument document : documentList) {
            System.out.println(document.get("id"));
            System.out.println(document.get("item_title"));
            System.out.println(document.get("item_price"));
            System.out.println(document.get("item_image"));
            System.out.println(document.get("item_sell_point"));
            System.out.println(document.get("item_category_name"));
        }
    }

    //复杂查询测试
    @Test
    public void testQueryIndexFUza() throws Exception {

        //创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
        //创建一个SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        solrQuery.set("q","手机");
        //设置分页
        solrQuery.setStart(0);
        solrQuery.setRows(30);
        //设置默认查询域
        solrQuery.set("df", "item_title");
        //设置高亮
        solrQuery.setHighlight(true);//开启高亮
        solrQuery.addHighlightField("item_title");//设置高亮字段
        //设置高亮前后缀
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");
        //执行查询
        QueryResponse response = solrServer.query(solrQuery);
        //取文档列表和总记录数
        SolrDocumentList documentList = response.getResults();
        System.out.println("查询总条数:"+documentList.getNumFound());//获取总计录数
        //遍历文档
        Map<String,Map<String,List<String>>>  highlightingMap= response.getHighlighting();
        for (SolrDocument document : documentList) {
            System.out.println(document.get("id"));
            //取高亮的结果
            List<String>highlightingList=highlightingMap.get(document.get("id")).get(document.get("item_title"));//获取高亮集合
            //遍历高亮集合
            String title="";
            if(highlightingList!=null&&highlightingList.size()>0){
                   title=highlightingList.get(0);
            }else {
                   title=(String) document.get("item_title");
            }
            System.out.println(title);
            System.out.println(document.get("item_price"));
            System.out.println(document.get("item_image"));
            System.out.println(document.get("item_sell_point"));
            System.out.println(document.get("item_category_name"));
        }



    }


}
