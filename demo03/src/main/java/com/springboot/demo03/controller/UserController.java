package com.springboot.demo03.controller;

import com.springboot.demo03.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ProgZhou
 * @createTime 2022/04/09
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id") Integer id){
        return userService.getUserById(id);
    }
}
