package com.itdan.shopmall.controller.protal;

import com.itdan.shopmall.entity.TbContent;
import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.service.ContentService;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 商城前台界面控制
 */
@Controller
public class ProtalPageController {


    @Autowired
    private ContentService contentService;

    @Value("${AD1_CATEGORY_ID}")
    private long AD1_CATEGORY_ID;

    /**
     * 商城首页展示
     * @return
     */
    @RequestMapping(value = "/")
    public ModelAndView showProtalIndex(){
        ModelAndView view=new ModelAndView();
        //获取广告轮播图
        List<TbContent>list= contentService.getContentList(AD1_CATEGORY_ID);
        view.addObject("ad1List",list);
        view.setViewName("jsp/index");
        return view;
    }




}
