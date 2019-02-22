package com.itdan.shopmall.controller.interceptor;

import com.itdan.shopmall.entity.TbUser;
import com.itdan.shopmall.service.TokenService;
import com.itdan.shopmall.utils.common.CookieUtils;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
       //前处理，执行handler之前执行该方法
        //返回true：放行，false：拦截
        //1.从cookie中获取token
        String token=CookieUtils.getCookieValue(request,"token");
        //2.如果没有token，未登录状态，直接放行
        ShopMallResult shopMallResult=tokenService.getUserByToken(token);
        //3.获取token，根据token获取用户信息
        if(shopMallResult.getStatus()!=200){
            return true;
        }
        //4.如果用户信息为空，表示token已经过期，直接放行
        //5.取到用户信息，登录状态
        TbUser tbUser=(TbUser) shopMallResult.getData();
        //6.把用户信息放到request中，只需要在Controller中判断request中是否包含user信息,放行
        request.setAttribute("user",tbUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //handler执行之后，返回ModelAndView之前
    }
}
