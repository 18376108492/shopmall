package com.itdan.shopmall.service.impl;

import com.itdan.shopmall.entity.TbUser;
import com.itdan.shopmall.service.TokenService;
import com.itdan.shopmall.utils.common.JsonUtils;
import com.itdan.shopmall.utils.jedis.JedisClient;
import com.itdan.shopmall.utils.result.ShopMallResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public ShopMallResult getUserByToken(String token) {
        //根据key值获取数据信息
        String json= jedisClient.get("SESSION:"+token);
        //查看信息是否过期
        if(StringUtils.isBlank(json)){
            //当信息为空时，返回提示
            return ShopMallResult.build(201,"用户登录已过期");
        }
        //获取信息后，重新设置token的过期时间
        jedisClient.expire("SESSION:"+token,SESSION_EXPIRE);
        //转换json数据
        TbUser tbUser=JsonUtils.jsonToPojo(json,TbUser.class);
        return ShopMallResult.ok(tbUser);
    }
}
