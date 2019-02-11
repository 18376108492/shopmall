package com.itdan.shopmall.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;
import javax.xml.soap.Text;

/**
 * 消息插件测试
 */
public class ActiveMQTest {



    @Test
    public void testQueueProducer () throws Exception {
        //点到点发送形式,生产者Producer
        //1.创建链接工厂对象,2.需要指定服务的ip和port
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://192.168.25.128:61616");
        //3.使用工厂对象来 创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        //4.开启连接，调用start方法
        connection.start();
        //5.创建session对象
         //第一个参数：是否开启事务，true开启，第二个参数无意义。false关闭，一般不开启事务
         //第二个参数：应答模式.一般自动应答，手动应答.
        Session session= connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //6.使用Session创建一个目的地(Destination对象).两种形式queue。topic，现在应该使用queue
        Queue queue=session.createQueue("test-queue");
        //7.使用session对象创建Producer对象
        MessageProducer producer=session.createProducer(queue);
        //8.创建一个Messag对象,可以使用TextMessage对象
        /* TextMessage textMessage=new ActiveMQTextMessage();
         textMessage.setText("hello world");*/
        TextMessage textMessage= session.createTextMessage("hello world");
        //9.发送消息
        producer.send(textMessage);
        //10.关闭资源
        producer.close();
        session.close();
        connection.close();

    }

    @Test
    public void testQueueMQConsumer () throws Exception{
        //点到点发送形式,消费者Consumer
        //创建一个ConnectionFactory对象连接MQ服务器
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(
                "tcp://192.168.25.128:61616");
        //创建一个连接对象
        Connection connection= connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用Connection对象创建一个Session对象
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //创建一个Destination对象,queue对象
        Queue queue=session.createQueue("test-queue");
        //使用Session创建一个消费者对象
        MessageConsumer consumer=session.createConsumer(queue);
        //接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印结果
                  TextMessage textMessage=(TextMessage) message;
                  String text;
                  try {
                     text=textMessage.getText();
                      System.out.println(text);
                  }catch (Exception e){
                      e.printStackTrace();
                  }

            }
        });

        //等待接收消息
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }


    //广播形式，发布和收到形式
    //生产者版
    @Test
    public void testTopicsProducer () throws Exception{

        //1.创建链接工厂对象,2.需要指定服务的ip和port
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://192.168.25.128:61616");
        //3.使用工厂对象来 创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        //4.开启连接，调用start方法
        connection.start();
        //5.创建session对象
        //第一个参数：是否开启事务，true开启，第二个参数无意义。false关闭，一般不开启事务
        //第二个参数：应答模式.一般自动应答，手动应答.
        Session session= connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //6.使用Session创建一个目的地(Destination对象).两种形式queue。topic，现在应该使用queue
         Topic topic=session.createTopic("test-topic");
        //7.使用session对象创建Producer对象
        MessageProducer producer=session.createProducer(topic);
        //8.创建一个Messag对象,可以使用TextMessage对象
        /* TextMessage textMessage=new ActiveMQTextMessage();
         textMessage.setText("hello world");*/
        TextMessage textMessage= session.createTextMessage("hello world");
        //9.发送消息
        producer.send(textMessage);
        //10.关闭资源
        producer.close();
        session.close();
        connection.close();

    }

    //广播形式，发布和收到形式
    //消费者版
    @Test
    public void testTopicConsumer () throws Exception{

        //点到点发送形式,消费者Consumer
        //创建一个ConnectionFactory对象连接MQ服务器
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(
                "tcp://192.168.25.128:61616");
        //创建一个连接对象
        Connection connection= connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用Connection对象创建一个Session对象
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //创建一个Destination对象,queue对象
        Topic topic=session.createTopic("test-topic");
        //使用Session创建一个消费者对象
        MessageConsumer consumer=session.createConsumer(topic);
        //接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印结果
                TextMessage textMessage=(TextMessage) message;
                String text;
                try {
                    text=textMessage.getText();
                    System.out.println(text);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        //等待接收消息
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();

    }

}
