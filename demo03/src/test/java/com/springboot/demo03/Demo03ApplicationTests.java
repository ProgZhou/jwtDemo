package com.springboot.demo03;

import com.springboot.demo03.service.UserService;
import com.springboot.demo03.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class Demo03ApplicationTests {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
    }


    @Test
    void testRedis(){

        ValueOperations<String, String> value = redisTemplate.opsForValue();

        String k1 = value.get("k1");

        value.set("k2", "v2");
        System.out.println(k1);
    }

    @Test
    void testRedisUtils(){
        System.out.println(redisUtils);

        Object k1 = redisUtils.getKey("k1");
        System.out.println(k1);

        String k2 = redisTemplate.opsForValue().get("k2");
        System.out.println(k2);

        boolean key = redisUtils.setKey("k3", "v3");
        System.out.println(key);
    }

    @Test
    void test(){
        String userById = userService.getUserById(1);
        System.out.println(userById);
    }

}
