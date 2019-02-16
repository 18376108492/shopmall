package com.itdan.shopmall.activemq.message;

import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.entity.TbItemDesc;
import com.itdan.shopmall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 监听商品添加消息,生成对应的静态页面
 */
public class HtmlGenMessageLisener implements MessageListener {


    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfig freemarkerConfig;

    @Override
    public void onMessage(Message message) {
        //创建一个模板，参考jsp
        try {
            //从消息中获取商品的id
            TextMessage textMessage= (TextMessage) message;
            Long id=new Long(textMessage.getText());
            //等待事务提交一秒钟
            Thread.sleep(1000);

            //根据商品的id查询商品信息，商品基本信息和商品的描述
            TbItem tbItem=itemService.getItemById(id);
            TbItemDesc tbItemDesc=itemService.queryItemDesc(id);
            //创建一个数据集合，把商品数据封装
            Map data=new HashMap();
            data.put("item",tbItem);
            data.put("itemDesc",tbItemDesc);
            //加载模板对象
            Configuration configuration= freemarkerConfig.getConfiguration();
            //设置模板文件保持的目录
            /*configuration.setDirectoryForTemplateLoading(
                    new File("D:/idea/wokespace/shopmall/src/main/webapp/WEB-INF/ftl") );*/
            //模板文件的编码格式一般为UTF-8
            configuration.setDefaultEncoding("UTF-8");
            Template template= configuration.getTemplate("item.ftl");
            //创建出一个输出流，指定输出的目录及文件名
            Writer out=new FileWriter(new File("D:/ge/test/"+tbItem.getId()+".ftl"));
            //生成静态页面
            template.process(data,out);
            //关闭流
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
