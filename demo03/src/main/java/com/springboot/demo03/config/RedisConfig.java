package com.springboot.demo03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/** redis的配置类
 * @author ProgZhou
 * @createTime 2022/04/09
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> serializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //key序列化
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);

        //value序列化
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        return redisTemplate;
    }

}
