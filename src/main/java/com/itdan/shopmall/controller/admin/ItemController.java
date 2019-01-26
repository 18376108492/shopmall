package com.itdan.shopmall.controller.admin;

import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.service.ItemService;
import com.itdan.shopmall.utils.common.FastDFSClient;
import com.itdan.shopmall.utils.common.JsonUtils;
import com.itdan.shopmall.utils.result.EasyUIDataGridResult;
import com.itdan.shopmall.utils.result.EasyUITreeNode;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商城后台商品控制
 */

@Controller
//@RequestMapping(value = "/admin")
public class ItemController {

    @Autowired
    private ItemService itemService;

    //获取图片服务器地址
    @Value("${IMAGE_SERVICE_URL}")
    private String IMAGE_SERVICE_URL;

    /**
     * 商城后台商品列表显示
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList( Integer page,Integer rows ){
        //调用service层
       EasyUIDataGridResult dataGridResult= itemService.getItemList(page,rows);
      return dataGridResult;
    }

    /**
     * 商城后台商品类型显示
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCat(
            @RequestParam(name = "id",defaultValue = "0") long parentId){
        //查询分类
        List<EasyUITreeNode> treeNodes=itemService.getItemCat(parentId);
        return treeNodes;
    }

    /**
     * 商城后台商品添加图片上传
     * @param uploadFile
     * @return
     */
    @RequestMapping(value = "/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        Map<String,Object> map=new HashMap<>();
        try {
            //创建FastDFSClient对象
            FastDFSClient fastDFSClient=new FastDFSClient("classpath:client.conf");
            //获取文件全名
            String originalFilename=uploadFile.getOriginalFilename();
            //获取文件扩展名
            String extName=originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            //把图片上传到图片服务器
            String imgUrl=fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
            //将返回的存储路径拼接完整
            String url=IMAGE_SERVICE_URL+imgUrl;
            //封装到map中返回
            map.put("url",url);
            map.put("error",0);//0表示成功
            return JsonUtils.objectToJson(map);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("error",1);//1表示失败
            return JsonUtils.objectToJson(map);
        }
    }

    /**
     * 商城后台添加商品操作
     * @param tbItem
     * @param desc
     * @return
     */
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    public ShopMallResult addItem(TbItem tbItem, String desc){
           ShopMallResult shopMallResult= itemService.addItem(tbItem,desc);
           return shopMallResult;
    }

    @RequestMapping(value = "/item-edit")
    @ResponseBody
    public String restEidtPage(@RequestParam(value = "id") long id){
        TbItem tbItem=itemService.editItem(id);
       return JsonUtils.objectToJson(tbItem);
    }

    @RequestMapping(value = "/rest/item/update",method = RequestMethod.POST)
    @ResponseBody
    public ShopMallResult eidtItem(TbItem tbItem,String desc){
        ShopMallResult shopMallResult= itemService.addItem(tbItem,desc);
        return shopMallResult;
    }


}
