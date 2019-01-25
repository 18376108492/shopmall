package com.itdan.shopmall.controller.admin;

import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.service.ItemService;
import com.itdan.shopmall.utils.result.EasyUIDataGridResult;
import com.itdan.shopmall.utils.result.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商城后台商品控制
 */

@Controller
//@RequestMapping(value = "/admin")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 商城后台商品列表显示
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList( Integer page,Integer rows ){
        //调用service层
       EasyUIDataGridResult dataGridResult= itemService.getItemList(page,rows);
      return dataGridResult;
    }

    /**
     * 商城后台商品类型显示
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCat(
            @RequestParam(name = "id",defaultValue = "0") long parentId){
        //查询分类
        List<EasyUITreeNode> treeNodes=itemService.getItemCat(parentId);
        return treeNodes;
    }

    /**
     * 商城后台添加商品操作
     * @param tbItem
     * @param desc
     * @return
     */
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    public Map<String,Object> addItem(TbItem tbItem,String desc){
        Map<String,Object> map=new HashMap<>();
        if(tbItem!=null){
            itemService.addItem(tbItem,desc);
             map.put("success",true);
        }else {
            map.put("msg","商城后台添加商品出现错误");
            map.put("success",false);
        }
        return map;
    }

}
