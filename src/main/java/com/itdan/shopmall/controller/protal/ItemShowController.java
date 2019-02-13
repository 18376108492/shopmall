package com.itdan.shopmall.controller.protal;

import com.itdan.shopmall.entity.Item;
import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.entity.TbItemDesc;
import com.itdan.shopmall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 单个商品的详细描述控制
 */
@Controller
public class ItemShowController {

    @Autowired
    private ItemService itemService;

    /**
     * 展示单个商品信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/item/{id}")
    public  String showItemInfo(@PathVariable Long id, Model model){
        //获取商品信息
        TbItem tbItem= itemService.getItemById(id);
         Item item=new Item(tbItem);
        //获取商品描述信息
        TbItemDesc tbItemDesc=itemService.queryItemDesc(id);
        //把信息返回界面
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",tbItemDesc);
        //返回逻辑视图
        return "jsp/item";
    }

    
}
