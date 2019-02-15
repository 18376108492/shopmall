package com.itdan.shopmall.controller.protal;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成静态页面的控制层
 */
@Controller
public class HtmlGenController  {

    @Autowired
    private FreeMarkerConfig freemarkerConfig;

    /**
     * freemarker与spring整合的简单测试
     * @return
     * @throws Exception
     */
    @RequestMapping("/genhtml")
    @ResponseBody
    public  String genhtml() throws Exception{
      Configuration configuration=freemarkerConfig.getConfiguration();
      //加载模板对象
       Template template=configuration.getTemplate("hello.ftl");
      //创建一个数据集
        Map data=new HashMap();
        data.put("hello",1134654);
      //指定文件输出的路径和文件名
        Writer out=new FileWriter(new File("D:/ge/hello.txt"));
        template.process(data,out);
        // 关闭资源
        out.close();
        return "ok";
    }



}
