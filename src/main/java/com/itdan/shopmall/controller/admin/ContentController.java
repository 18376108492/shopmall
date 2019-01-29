package com.itdan.shopmall.controller.admin;

import com.itdan.shopmall.service.ContentService;
import com.itdan.shopmall.utils.common.JsonUtils;
import com.itdan.shopmall.utils.result.EasyUITreeNode;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 遍历内容管理的树形结构
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/content/category/list")
    @ResponseBody
    public String getContentList(
            @RequestParam(value = "id",defaultValue = "0")long parentId){
        //获取树形列表
       List<EasyUITreeNode> list= contentService.getContentList(parentId);
        return JsonUtils.objectToJson(list);
    }


    /**
     * 添加内容管理节点
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping(value = "/content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public ShopMallResult addContentCategory(long parentId,String name){
       ShopMallResult shopMallResult= contentService.addContentCategroy(parentId,name);
       return  shopMallResult;
    }

    /**
     * 删除内容分类管理节点
     * @param id
     * @return
     */
    @RequestMapping(value = "/content/category/delete/")
    @ResponseBody
    public ShopMallResult deleteContentCategory(long id){
       ShopMallResult shopMallResult= contentService.deleteContentCategroy(id);
       return shopMallResult;
    }


    /**
     * 更新内容分类管理节点
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/content/category/update")
    @ResponseBody
    public ShopMallResult updateContentCategroy(long id,String name){
       ShopMallResult shopMallResult= contentService.updateContentCategroy(id,name);
       return shopMallResult;
    }
}

