package com.itdan.shopmall.activemq.message;

import com.itdan.shopmall.dao.SolrItemMapper;
import com.itdan.shopmall.utils.result.SolrResult;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听商品的添加信息，并将新增的商品同步到索引库中
 */
public class AddItemMessageLisener implements MessageListener {

    @Autowired
    private SolrItemMapper  solrItemMapper;
    @Autowired
    private SolrServer solrServer;


    @Override
    public void onMessage(Message message) {

        try {
            //从消息获取商品的id
            TextMessage textMessage=(TextMessage) message;
            String text=textMessage.getText();
            long itemid=new Long(text);

            //等待事务提交
            Thread.sleep(1000);

            //根据获取的id获取商品对象
            SolrResult result= solrItemMapper.getItembyId(itemid);
            //添加文本域
            //创建文档对象
            SolrInputDocument document=new SolrInputDocument();
            //添加文档内容
            document.addField("id",result.getId());
            document.addField("item_title",result.getTitle());
            document.addField("item_price",result.getPrice());
            document.addField("item_image",result.getImage());
            document.addField("item_sell_point",result.getSell_point());
            document.addField("item_category_name",result.getCategroy_name());
            //提交到索引库
            solrServer.add(document);
            //提交
            solrServer.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
