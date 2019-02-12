package com.itdan.shopmall.activemq.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * ActiveMQ信息接收例子
 */
public class MyMessageListener implements MessageListener {

    //接收信息
    @Override
    public void onMessage(Message message) {
       TextMessage textMessage=(TextMessage) message;
       //获取内容
        try {
           String text= textMessage.getText();
            System.out.println(text);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
