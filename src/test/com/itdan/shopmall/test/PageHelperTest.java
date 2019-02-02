package com.itdan.shopmall.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdan.shopmall.BaseTest;
import com.itdan.shopmall.dao.TbItemMapper;
import com.itdan.shopmall.entity.TbItem;
import com.itdan.shopmall.entity.TbItemExample;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 分页助手插件测试
 */
public class PageHelperTest extends BaseTest {

    @Autowired
    private TbItemMapper itemMapper;
    @Test
    public void testPageHelperDemo01 () throws Exception{
        //初始化spring容器后从容器中获取mapper对象
        //查询之前设置分页信息，使用pageHelper的startPage()方法
        PageHelper.startPage(1,10);
        //执行查询
        TbItemExample itemExample=new TbItemExample();
        //注意mybastis的版本要和pageHelper的对应，否则相关的参数可能不一致，会出翔报错。
        List<TbItem> tbItems= itemMapper.selectByExample(itemExample);
        //获取分页信息，使用pageInfo来存储
        PageInfo<TbItem> pageInfo=new PageInfo<>(tbItems);
        System.out.println("pageNum"+pageInfo.getPageNum());
        System.out.println("total"+pageInfo.getTotal());
        System.out.println("list"+pageInfo.getList());
    }


    @Test
    public void testPage() throws Exception {
        //先初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring/spring-dao.xml");
        //获取的mapper对象
        TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
        //执行sql语句前使用分页插件pageHelper的pageStart来执行
        PageHelper.startPage(1, 10);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = mapper.selectByExample(example);
        //获取查询分页信息 InfoPage
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        System.out.println("总数:" + pageInfo.getTotal());
        System.out.println("总页数:" + pageInfo.getPages());
        System.out.println(list.size());
    }
}
