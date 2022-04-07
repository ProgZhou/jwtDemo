package com.security.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.security.demo.annotation.PassToken;
import com.security.demo.bean.User;
import com.security.demo.service.UserService;
import com.security.demo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ProgZhou
 * @createTime 2022/04/06
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(){
//        System.out.println("**************************");
        return "index";
    }

    @PassToken(required = false)
    @ResponseBody
    @GetMapping("/test")
    public String get(){
        return "successful";
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 如果用户登录失败报错，登陆成功就返回用户信息，并生成jwt返回给用户
     */
    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password){
        //简单写一下条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        User user = userService.getOne(queryWrapper);
        if(user == null){
            return "登陆失败";
        }
        String token = JwtUtils.createToken(user.getId().toString(), user.getUsername(), user.getPassword());
        return user.toString() + "/ jwt: " + token;
    }

}
