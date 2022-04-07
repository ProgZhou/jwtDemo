package com.security.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.security.demo.bean.User;
import com.security.demo.mapper.UserMapper;
import com.security.demo.service.UserService;
import com.security.demo.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Demo01ApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testService(){
//        System.out.println(userService);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 1);
        User user = userService.getOne(wrapper);
        System.out.println(user);

    }

    @Test
    public void testJwt(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 1);
        User user = userService.getOne(wrapper);
        //测试生成jwt字符串
        String token = JwtUtils.createToken(user.getId().toString(), user.getUsername(), user.getPassword());
        System.out.println(token);

        //测试验证jwt字符串
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getId() + "AAAAA")).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        //获取载荷
        Claim username = decodedJWT.getClaim("username");
        Claim password = decodedJWT.getClaim("password");
        System.out.println("username: " + username.asString());
        System.out.println("password: " + password.asString());

        List<String> audience = JWT.decode(token).getAudience();
        for (String s : audience) {
            System.out.println(s);
        }
    }

}
