package com.itdan.shopmall.controller.admin;

import com.itdan.shopmall.service.SolrItemService;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 索引库维护控制
 */
@Controller
public class SolrItemController {

    @Autowired
    private SolrItemService solrItemService;

    /**
     * 将商品导入索引库中
     * @return
     */
    @RequestMapping(value = "/index/item/import",method = RequestMethod.POST)
    @ResponseBody
    public ShopMallResult searchItem(){
       ShopMallResult shopMallResult= solrItemService.importAllItem();
        return shopMallResult;
    }
}
