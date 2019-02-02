package com.itdan.shopmall.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * redis缓存测试
 */
public class RedisTest {

    @Test
    public void testRedis () throws Exception{
        //创建Jedis对象
        Jedis jedis=new Jedis("192.168.25.192",6379);
        //使用Jedis来操作reids
        jedis.set("name","xiaoming");
        String name=jedis.get("name");
        System.out.println(name);
        jedis.close();
    }


    //使用连接池
    @Test
    public void testJedisPool () throws Exception{
     //创建连接池对象
        JedisPool jedisPool =new JedisPool("192.168.25.192",6379);
        //从连接中获取Jedis对象
        Jedis jedis= jedisPool.getResource();
        //使用Jedis来操作reids
        jedis.set("name","xiaoming");
        String name=jedis.get("name");
        System.out.println(name);
        //关闭支援才能回收资源
        jedis.close();
    }

    //使用redis集群
    @Test
    public void testJedisClutser () throws Exception{
     //创建JedisCluster对象，有一个参数nodes是一个set类型。set中包含若干个HostAndPost对象
        Set<HostAndPort> nodes=new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.192",7001));
        nodes.add(new HostAndPort("192.168.25.192",7002));
        nodes.add(new HostAndPort("192.168.25.192",7003));
        nodes.add(new HostAndPort("192.168.25.192",7004));
        nodes.add(new HostAndPort("192.168.25.192",7005));
        nodes.add(new HostAndPort("192.168.25.192",7006));

        //直接使用JedisCluster来操作redis
        JedisCluster jedisCluster=new JedisCluster(nodes);
        jedisCluster.set("name","小红");
        String name=jedisCluster.get("name");
        System.out.println(name);
        //关闭资源
        jedisCluster.close();
    }

}
