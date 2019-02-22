package com.itdan.shopmall.controller.user;

import com.itdan.shopmall.service.TokenService;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 根据token获取用户信息控制
 */
@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public ShopMallResult getUserByToken(@PathVariable String token){
        ShopMallResult shopMallResult= tokenService.getUserByToken(token);
        return shopMallResult;
    }

}
