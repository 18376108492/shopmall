package com.itdan.shopmall.controller.protal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商城前台界面控制
 */
@Controller
public class ProtalPageController {

    /**
     * 商城首页展示
     * @return
     */
    @RequestMapping(value = "/")
    public String showProtalIndex(){
        return "jsp/index";
    }

}
