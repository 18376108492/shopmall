package com.itdan.shopmall.test;

import com.itdan.shopmall.BaseTest;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


public class ActiveMQTOSpring extends BaseTest {

    //获取JmsTemplate对象
    @Autowired
    private JmsTemplate jmsTemplate;

    //从容器中获取一个Destination对象
    @Autowired
    private ActiveMQQueue queueDestination;

    @Test
    public void testDemo01 () throws Exception{

       //将ActiveMQQueue转换成Destination对象
        Destination destination=(Destination) queueDestination;
        //发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return   session.createTextMessage("hello world");
            }
        });

    }
}
