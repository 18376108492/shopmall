package com.itdan.shopmall.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商城后台页面控制管理
 */
@Controller
@RequestMapping(value = "/admin")
public class PageController {

    /**
     * 展示商城后台首页
     * @return
     */
    @RequestMapping(value = "/")
    public String showIndex(){
        return "admin-index";
    }

    /**
     * 展示首页下的节点分页
     * @return
     */
    @RequestMapping(value = "/{page}")
    public String showPage(@PathVariable String page){
        System.out.println(page);
        return "admin/"+page;
    }


}
