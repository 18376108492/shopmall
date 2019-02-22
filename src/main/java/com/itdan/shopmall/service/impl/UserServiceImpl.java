package com.itdan.shopmall.service.impl;

import com.itdan.shopmall.dao.TbUserMapper;
import com.itdan.shopmall.entity.TbUser;
import com.itdan.shopmall.entity.TbUserExample;
import com.itdan.shopmall.service.UserSerivce;
import com.itdan.shopmall.utils.common.JsonUtils;
import com.itdan.shopmall.utils.jedis.JedisClient;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *  用户业务逻辑处理类
 */
@Service
public class UserServiceImpl implements UserSerivce {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public ShopMallResult registerCheck(String param, Integer type) {
        //创建查询对象
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria= example.createCriteria();
        //根据不同type，生成不同的查询条件
        //type类型:1为用户名，2为手机号，3为邮箱
        if(type==1){
            //查询用户名
            criteria.andUsernameEqualTo(param);
        }else if(type==2){
            //查询手机号
            criteria.andPhoneEqualTo(param);
        }else if(type==3){
            //查询邮箱
            criteria.andEmailEqualTo(param);
        }else {
           return ShopMallResult.build(400,"数据类型错误");
        }
        //执行查询
        List<TbUser> list= tbUserMapper.selectByExample(example);
        if(list!=null&&list.size()>0){
         return ShopMallResult.ok(false);
        }
        //校验数据(如果存在返回false，否则返回ture)
        return ShopMallResult.ok(true);
    }

    @Override
    public ShopMallResult register(TbUser tbUser) {
        //校验用户信息是否为位空
       if(StringUtils.isBlank(tbUser.getUsername())||StringUtils.isBlank(tbUser.getPassword())||StringUtils.isBlank(tbUser.getPhone())){
           return ShopMallResult.build(400,"用户数据不完整");
       }
       //校验用户信息是否已经存在
       if(!(boolean)registerCheck(tbUser.getUsername(),1).getData()){
           return ShopMallResult.build(400,"此用户名已经被使用");
       }
       if(!(boolean) registerCheck(tbUser.getPhone(),2).getData()){
            return ShopMallResult.build(400,"用户此手机号已经被注册");
        }

        //补全信息
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        //使用MD5对密码进行加密
        String password=tbUser.getPassword();
        tbUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));

        //向数据库中插入数据
        tbUserMapper.insert(tbUser);
        return ShopMallResult.ok();
    }

    @Override
    public ShopMallResult login(String username,String password) {
        //创建查询对象和条件
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria= example.createCriteria();
        criteria.andUsernameEqualTo(username);
        //通过用户名去查询数据库，获取用户对象
        List<TbUser> list=tbUserMapper.selectByExample(example);
        //判断用户是否存在
        if(list==null||list.size()==0){
            return  ShopMallResult.build(400,"用户名或密码错误");
        }
        //获取查询的用户对象
        TbUser user=list.get(0);
        //校验用户密码
        //先取出密码加密后再校验
        String newPassword=DigestUtils.md5DigestAsHex(password.getBytes());
        if(!user.getPassword().equals(newPassword)){
            return  ShopMallResult.build(400,"密码输入错误");
        }
        //生成token
        String token=UUID.randomUUID().toString();
        //把用户信息写入redis中，key: token value值为用户提示
        //将密码设为null
        user.setPassword(null);
        jedisClient.set("SESSION:"+token,JsonUtils.objectToJson(user));
        //设置过期时间
        jedisClient.expire("SESSION:"+token,SESSION_EXPIRE);
        //返回状态
        return ShopMallResult.ok(token);
    }
}
