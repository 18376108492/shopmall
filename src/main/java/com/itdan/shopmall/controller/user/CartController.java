package com.itdan.shopmall.controller.user;

import com.itdan.shopmall.dao.TbOrderItemMapper;
import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.entity.TbUser;
import com.itdan.shopmall.service.CartService;
import com.itdan.shopmall.service.ItemService;
import com.itdan.shopmall.utils.common.CookieUtils;
import com.itdan.shopmall.utils.common.JsonUtils;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车业务控制
 */
@Controller
public class CartController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private CartService cartService;


    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;


    /**
     * 从cookie中取购物车列表的处理
     * @param request
     * @return
     */
    private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, "cart", true);
        //判断json是否为空
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>();
        }
        //把json转换成商品列表
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }

    /**
     * 商品添加进购物车后，跳转至成功界面。
     *
     * @param itemId   商品id
     * @param num      商品数量
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId,
                          @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        //判断用户是否登入
        TbUser tbUser=(TbUser) request.getAttribute("user");
        if(tbUser!=null){
            //保存在服务器
            cartService.addCart(tbUser.getId(),itemId,num);
            //返回逻辑视图
            return  "jsp/cartSuccess";
        }

        //打印cart
       Object cart=  request.getAttribute("cart");
         if(cart!=null){
             System.out.println("cart:"+cart.toString());
         }

        //用于判断商品是否存在
        boolean falg = false;
        //从cookie获取购物车列表
        List<TbItem> list=getCartListFromCookie(request);

        //判断商品是否已经存在
        if (list != null && list.size() > 0) {
            for (TbItem item : list) {
                if (item.getId() == itemId.longValue()) {
                    falg = true;
                    //如果商品已经存在，num++
                    item.setNum(item.getNum() + num);
                    break;
                }
            }
        }
            //如果不存在，将其添加进购物车中
            if (!falg) {
                //根据商品的id，获取商品的对象
                TbItem tbItem = itemService.getItemById(itemId);
                //设置数量
                tbItem.setNum(num);
                //取一张图片
                String[] img = tbItem.getImage().split(",");
                if(img!=null) {
                    tbItem.setImage(img[0]);
                }
                //将商品添加进购物车中
                list.add(tbItem);
            }

        System.out.println("cartList"+list);
        //重新将购物车存入cookie中
        CookieUtils.setCookie(request, response, "cart",
                JsonUtils.objectToJson(list), COOKIE_CART_EXPIRE, true);
        //返回成功界面
        return "jsp/cartSuccess";
    }


    /**
     * 购物车界面展示
     * @param request
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request,
                               HttpServletResponse response){
        //从cookie中取出购物车
        //从cookie获取购物车列表
        List<TbItem> cartList= getCartListFromCookie(request);
        if(cartList!=null&&cartList.size()>0) {
            System.out.println("cartList1:" + cartList);
        }

        //判断用户是否为登入状态
        TbUser user=(TbUser) request.getAttribute("user");
        if(user!=null){
            //如果是登录状态
            //从cookie中获取购物车信息
            //如果不为空，把cookie中的购物车和redis中的购物车商品合并,并且清空cookie中的购物车
            cartService.mergeCart(user.getId(),cartList);
            //清空cookie
            CookieUtils.deleteCookie(request,response,"cart");
            //从服务端取购物车列表
            cartList=cartService.getCartList(user.getId());
        }

        //把列表传递给页面
        request.setAttribute("cartList",cartList);
        //返回逻辑视图
        return "jsp/cart";
    }

    /**
     * 修改购物车相应商品的数量
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public  ShopMallResult  updateItemNum(@PathVariable Long itemId,
                                  @PathVariable Integer num,
                                  HttpServletRequest request,
                                  HttpServletResponse response){

        //判断用户是否为登入状态
        TbUser user=(TbUser) request.getAttribute("user");
        if(user!=null){
           ShopMallResult shopMallResult=  cartService.updateCartNum(user.getId(),itemId,num);
           return shopMallResult;
        }
        //从cookie获取购物车列表
        //从cookie获取购物车列表
        List<TbItem> cartList= getCartListFromCookie(request);
        if(cartList!=null&&cartList.size()>0) {
            System.out.println("cartList2:" + cartList);
        }
        //根据商品ID查询出集合中商品
        for(TbItem tbItem:cartList){
            //当商品id相等时修改商品数量
            if(tbItem.getId()==itemId.longValue()){
                tbItem.setNum(num);
                break;
            }
        }
        //重新将购物车存入cookie中
        CookieUtils.setCookie(request, response, "cart",
                JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        //返回成功
        return ShopMallResult.ok();

    }

    /**
     * 删除购物车中的商品
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId,
                             HttpServletRequest request,
                             HttpServletResponse response){

        //判断用户是否登入
        TbUser user=(TbUser) request.getAttribute("user");
        if(user!=null){
            cartService.deleteCartItem(user.getId(),itemId);
            //返回逻辑视图
            return "redirect:/cart/cart";
        }

        //从cookie获取购物车列表
        //从cookie获取购物车列表
        List<TbItem> cartList= getCartListFromCookie(request);
        if(cartList!=null&&cartList.size()>0) {
            System.out.println("cartList3:" + cartList);
        }
        //根据商品ID查询出集合中商品
        for(TbItem tbItem:cartList){
            //当商品id相等时修改商品数量
            if(tbItem.getId()==itemId.longValue()){
                //删除商品
                cartList.remove(tbItem);
                break;
            }
        }
        //重新将购物车存入cookie中
        CookieUtils.setCookie(request, response, "cart",
                JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        //返回逻辑视图
        return "redirect:/cart/cart";

    }


}