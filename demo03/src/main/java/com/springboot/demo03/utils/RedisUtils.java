package com.springboot.demo03.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/** 为了方便redis的操作可以编写一个redis的工具类
 * @author ProgZhou
 * @createTime 2022/04/09
 */
@Component
public final class RedisUtils {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 判断key是否在redis中
     * @param key 键值
     * @return 如果key存在，则返回true；否则返回false
     */
    public boolean hasKey(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));

    }

    /**
     * 向缓存中存入一个<K, V>键值对
     * @param key 存入缓存的键
     * @param value 键对应的值
     * @return 如果存入成功，则返回true；否则返回失败
     */
    public boolean setKey(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    /**
     * 取得key的值
     * @param key key
     * @return 返回key键对应的值
     */
    public Object getKey(String key){
        return redisTemplate.opsForValue().get(key);
    }


}
