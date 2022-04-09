## Springboot整合redis
**导入springboot整合redis的包**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
**编写redis相关的配置**
```yml
redis:
    host: 127.0.0.1
    port: 6379
    #使用jedis，也可以使用新版的lettuce
    jedis:
      pool:
        max-active: 100   #连接池中最大的连接数
        max-idle: 8     #连接池中最大的空闲连接
        max-wait: 10000  #连接池中最大阻塞等待时间
    timeout: 5000   # 连接超时时间（毫秒）
```

**配置redisTemplate，解决序列化问题**
```java
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
```
**如果为了方便使用还可以编写一个与Redis操作相关的工具类**

Redis的使用一般写在Service层中，本demo只是非常简单的使用了一下redis
主要是记录一下springboot整合redis的步骤