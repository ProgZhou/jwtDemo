package com.springboot.demo03.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.demo03.bean.User;

/**
 * @author ProgZhou
 * @createTime 2022/04/09
 */
public interface UserService extends IService<User> {
    String getUserById(Integer id);
}
