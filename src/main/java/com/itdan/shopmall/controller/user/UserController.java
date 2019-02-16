package com.itdan.shopmall.controller.user;


import com.itdan.shopmall.entity.TbUser;
import com.itdan.shopmall.service.UserSerivce;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户操作控制层
 */
@Controller
public class UserController {

    @Autowired
    private UserSerivce userSerivce;

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/page/register")
    public String toRegisterPage(){
        return "jsp/register";
    }


    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/page/login")
    public String toLoginPage(){
        return "jsp/login";
    }


    /**
     * 用户注册信息检查
     * @param param 检查数据的参数
     * @param type 检查数据的类型(type类型:1为用户名，2为手机号，3为邮箱)
     * @return
     */
    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public ShopMallResult registerCheck(@PathVariable String param,@PathVariable Integer type){
       ShopMallResult shopMallResult= userSerivce.registerCheck(param,type);
       return shopMallResult;
    }


    /**
     * 用户注册提交信息操作
     * @param tbUser 注册的用户对象
     * @return
     */
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public ShopMallResult register(TbUser tbUser){
       ShopMallResult shopMallResult=userSerivce.register(tbUser);
       return shopMallResult;
     }

    /**
     * 用户登入操作
     * @param tbUser 登入对象
     * @return
     */
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public ShopMallResult login(TbUser tbUser){
          ShopMallResult shopMallResult = userSerivce.login(tbUser);
        return shopMallResult;
    }



}
