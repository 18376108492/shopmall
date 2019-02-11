package com.itdan.shopmall.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GlobalExxeptionResolver implements HandlerExceptionResolver {

    private static final Logger logger=LoggerFactory.getLogger(GlobalExxeptionResolver.class);

    @Override
    /**
     * 全局异常处理器
     */
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //打印控制台
        ex.printStackTrace();
        //写日志
        //一般都调成Info级别
        logger.info("系统发生异常");
        logger.error("系统发生异常",ex);
        //发邮件，发短信
        //使用jmall工具包,网上搜
        //回显错误页面
         ModelAndView modelAndView=new ModelAndView();
         modelAndView.setViewName("jsp/error/exception");//显示错误界面
        return modelAndView;
    }
}
