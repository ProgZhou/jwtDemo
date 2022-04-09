package com.springboot.demo03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.demo03.bean.User;
import com.springboot.demo03.mapper.UserMapper;
import com.springboot.demo03.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ProgZhou
 * @createTime 2022/04/09
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    UserMapper userMapper;

    @Override
    public String getUserById(Integer id){
        if(Boolean.TRUE.equals(redisTemplate.hasKey("user" + id))){
            log.info("user: " + redisTemplate.opsForValue().get("user"));
            return redisTemplate.opsForValue().get("user");
        } else{
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            User user = userMapper.selectOne(wrapper);
            log.info("user(no redis): " + user);
            redisTemplate.opsForValue().set("user" + id, user.toString());
            //设置key的过期时间
            redisTemplate.expire("user" + id, 30, TimeUnit.SECONDS);
            log.info("key timeout" + redisTemplate.getExpire("user"));
            return user.toString();
        }
    }


}
