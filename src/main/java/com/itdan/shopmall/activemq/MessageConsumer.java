package com.itdan.shopmall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 信息消费者示例
 */

public class MessageConsumer {


    @Test
    public  void msgConsumer() throws  Exception{
        //初始化spring容器
        ApplicationContext context=new ClassPathXmlApplicationContext(
                "classpath:spring/spring-*.xml");
        //等待
        System.in.read();

    }
}
