package com.security.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.demo.bean.User;
import com.security.demo.mapper.UserMapper;
import com.security.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author ProgZhou
 * @createTime 2022/04/06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
