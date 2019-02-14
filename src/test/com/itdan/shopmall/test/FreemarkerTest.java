package com.itdan.shopmall.test;

import com.itdan.shopmall.pojo.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 静态界面测试
 */
public class FreemarkerTest {

    @Test
    public void testFreemarkerDemo01 () throws Exception{
        //1.创建一个模板文件
        //2.创建一个Configuration对象
        Configuration configuration=new Configuration(Configuration.getVersion());
        //3.设置模板文件保持的目录
        configuration.setDirectoryForTemplateLoading(new File("D:/idea/wokespace/shopmall/src/main/webapp/WEB-INF/ftl") );
        //4.模板文件的编码格式一般为UTF-8
        configuration.setDefaultEncoding("UTF-8");
        //5.加载模板文件，创建一个模板对象
        Template template= configuration.getTemplate("hello.ftl");
        //6.创建一个数据集，可以是POJO也可以是Map(推荐使用)
        Map data=new HashMap<>();
        data.put("hello","hello freemarker");
        //7.创建一个Writer对象,指定输出文件的路径及文件名
        Writer out=new FileWriter(new File("D:/ge/hello.txt"));
        //8.生成静态界面
        template.process(data,out);
        //9.关闭流
        out.close();

    }


    //访问POJO属性
    @Test
    public void testFreemarkerDemo02 () throws Exception{
        //1.创建一个模板文件
        //2.创建一个Configuration对象
        Configuration configuration=new Configuration(Configuration.getVersion());
        //3.设置模板文件保持的目录
        configuration.setDirectoryForTemplateLoading(new File("D:/idea/wokespace/shopmall/src/main/webapp/WEB-INF/ftl") );
        //4.模板文件的编码格式一般为UTF-8
        configuration.setDefaultEncoding("UTF-8");
        //5.加载模板文件，创建一个模板对象
        Template template= configuration.getTemplate("student.ftl");
        //6.创建一个数据集，可以是POJO也可以是Map(推荐使用)
        Map data=new HashMap<>();
        //创建学生对象
        Student student=new Student(1,"小明","五环",17);
        //添加到map
        data.put("student",student);
        //7.创建一个Writer对象,指定输出文件的路径及文件名
        Writer out=new FileWriter(new File("D:/ge/student.html"));
        //8.生成静态界面
        template.process(data,out);
        //9.关闭流
        out.close();

    }

    //遍历集合的元素
    @Test
    public void testFreemarkerDemo03 () throws Exception{
        //1.创建一个模板文件
        //2.创建一个Configuration对象
        Configuration configuration=new Configuration(Configuration.getVersion());
        //3.设置模板文件保持的目录
        configuration.setDirectoryForTemplateLoading(new File("D:/idea/wokespace/shopmall/src/main/webapp/WEB-INF/ftl") );
        //4.模板文件的编码格式一般为UTF-8
        configuration.setDefaultEncoding("UTF-8");
        //5.加载模板文件，创建一个模板对象
        Template template= configuration.getTemplate("student.ftl");
        //6.创建一个数据集，可以是POJO也可以是Map(推荐使用)
        Map data=new HashMap<>();
        Student student5=new Student(5,"小六","五环",17);
        //添加到map
        data.put("student",student5);
        //创建学生对象
        Student student1=new Student(1,"小明","五环",17);
        Student student2=new Student(2,"小红","而环",17);
        Student student3=new Student(3,"小绿","都是环",17);
        Student student4=new Student(4,"小黄","五都是",17);
        //创建List
        List<Student>list=new ArrayList<>();
        list.add(student1);
        list.add(student2);
        list.add(student3);
        list.add(student4);
        list.add(student5);
        //添加到map中
        data.put("studentList",list);
        //7.创建一个Writer对象,指定输出文件的路径及文件名
        Writer out=new FileWriter(new File("D:/ge/student1.html"));
        //8.生成静态界面
        template.process(data,out);
        //9.关闭流
        out.close();

    }

}
