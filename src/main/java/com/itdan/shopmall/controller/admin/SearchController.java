package com.itdan.shopmall.controller.admin;

import com.itdan.shopmall.service.SearchService;
import com.itdan.shopmall.utils.result.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品搜索控制
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value("${ROWS}")
    private  int  ROWS;

    /**
     * 将查询商品结果返回
     * @param keyword
     * @param page
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search")
    public String searchItemList(String keyword,
                                 @RequestParam(defaultValue = "1") int page, Model model) throws Exception{
        //转码
        keyword=new String(keyword.getBytes("iso-8859-1"),"utf-8");
        //获取查询结果
       SearchResult searchResult=searchService.search(keyword,page,ROWS);
       //添加查询信息
       model.addAttribute("query",keyword);
       model.addAttribute("totalPages",searchResult.getTotalPages());
       model.addAttribute("page",page);
       model.addAttribute("recourdCount",searchResult.getRecourdCount());
       model.addAttribute("itemList",searchResult.getItemList());

       //返回逻辑视图
        return "jsp/search";

    }
}
