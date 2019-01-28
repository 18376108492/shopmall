package com.itdan.shopmall.controller.admin;

import com.itdan.shopmall.service.ContentService;
import com.itdan.shopmall.utils.common.JsonUtils;
import com.itdan.shopmall.utils.result.EasyUITreeNode;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商城后台对前台内容控制
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/category/list")
    @ResponseBody
    public String getContentList(
            @RequestParam(value = "id",defaultValue = "0")long parentId){
        //获取树形列表
       List<EasyUITreeNode> list= contentService.getContentList(parentId);
        return JsonUtils.objectToJson(list);
    }


    @RequestMapping(value = "/content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public ShopMallResult addContentCategroy(long parentId,String name){
       ShopMallResult shopMallResult= contentService.addContentCategroy(parentId,name);
       return  shopMallResult;
    }
}

