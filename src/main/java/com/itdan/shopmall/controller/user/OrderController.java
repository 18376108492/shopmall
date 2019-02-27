package com.itdan.shopmall.controller.user;

import com.itdan.shopmall.entity.OrderInfo;
import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.entity.TbUser;
import com.itdan.shopmall.service.CartService;
import com.itdan.shopmall.service.OrderService;
import com.itdan.shopmall.utils.common.CookieUtils;
import com.itdan.shopmall.utils.common.JsonUtils;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单界面控制
 */
@Controller
public class OrderController {


    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    /**
     * 从购物车界面跳转至订单界面
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/order-cart",method =RequestMethod.GET)
    public String showOrderPage(HttpServletRequest request){

        //从购物车界面跳转至订单界面时，先去判断用户是否为登录状态
        // 当用户登录后，先将cookie中的商品和redis中的商品进行合并
        //先获取用户对象
        TbUser tbUser=(TbUser) request.getAttribute("user");
        if(tbUser==null){
           //如果用户没有登入,跳转至登录界面
            return "redirect:/page/login?redirectUrl="+request.getRequestURI();
        }
        //获取cookie购物车的数据，判断是否为空，如果不为空的话合并购物车
        String jsonCartList=CookieUtils.getCookieValue(request,"cart",true);
        if(StringUtils.isNotBlank(jsonCartList)){
            cartService.mergeCart(tbUser.getId(),JsonUtils.jsonToList(jsonCartList,TbItem.class));
        }
        //根据用户id获取redis中商品列表
        List<TbItem> cartList=cartService.getCartList(tbUser.getId());
        //把购物车列表传递给订单页面
        if(cartList!=null&&cartList.size()>0){
            System.out.println("订单中的购物车1:"+JsonUtils.objectToJson(cartList));
            System.out.println("订单中的购物车2:"+cartList.toString());
        }
        request.setAttribute("cartList",cartList);
        //返回视图
        return  "jsp/order-cart";
    }

    /**
     * 创建订单
     * @param orderInfo 订单信息表
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo,
                                      HttpServletRequest request){
        //获取用户对象信息
        TbUser user=(TbUser)request.getAttribute("user");
        //把用户信息添加到订单对象中
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        //调用服务生成订单
        ShopMallResult shopMallResult= orderService.createOrder(orderInfo);
        //订单生成成功后，将购物车中的商品清除
        if(shopMallResult.getStatus()==200) {
            cartService.clearCartItem(user.getId());
        }
        //把订单好返回给页面
        request.setAttribute("orderId",shopMallResult.getData());
        request.setAttribute("payment",orderInfo.getPayment());
        //返回逻辑视图
         return "jsp/success";
    }



}
