package com.itdan.shopmall.service.impl;

import com.itdan.shopmall.dao.SolrItemMapper;
import com.itdan.shopmall.dao.TbItemMapper;
import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.service.SolrItemService;
import com.itdan.shopmall.utils.result.ShopMallResult;
import com.itdan.shopmall.utils.result.SolrResult;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Solr查询商品业务逻辑实现类
 */
@Service
public class SolrItemServiceImpl implements SolrItemService {

    @Autowired
    private SolrItemMapper solrItemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public ShopMallResult importAllItem() {
        //查询所有的商品
        List<SolrResult> itemList =solrItemMapper.getAllItem();
        try {
            //将商品遍历
            for (SolrResult result:itemList){
                //将遍历的商品添加到文档中
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
            }
            //提交
            solrServer.commit();
            return ShopMallResult.ok();
            //返回成功
        }catch (Exception e){
         e.printStackTrace();
         return ShopMallResult.build(500,"索引库数据添加失败");
        }

    }
}
