package com.itdan.shopmall.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * solr测试
 */
public class SolrTest {

    //添加文本
    public void testAddDocument () throws Exception{
        //创建一个SolrServer对象创建一个连接,参数solr服务的url
        SolrServer  solrServer=new HttpSolrServer("192.168.25.128:8080/solr");
        //创建一个文档对象,SolrInputDocument对象
        SolrInputDocument document=new SolrInputDocument();
        //向文档中添加域，文档中必须包含一个ID域，所有的域的名称必须在schema.xml中定义
        document.addField("id","doc01");
        document.addField("item_title","测试商品01");
        document.addField("item_price",1000);
        //把文档写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }


    @Test
    public void testDeleteDocument () throws Exception{
        //创建一个SolrServer对象创建一个连接,参数solr服务的url
        SolrServer  solrServer=new HttpSolrServer("192.168.25.128:8080/solr");
        //根据id删除(传入文档id)
        solrServer.deleteById("doc01");
        //查询删除
        solrServer.deleteByQuery("id:doc01");
        //提交
        solrServer.commit();
    }

    @Test
    public void testSolrJ () throws Exception{

    }
}
