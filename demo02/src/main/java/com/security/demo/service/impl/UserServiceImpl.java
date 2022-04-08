package com.security.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.demo.bean.JwtUser;
import com.security.demo.bean.User;
import com.security.demo.mapper.UserMapper;
import com.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ProgZhou
 * @createTime 2022/04/07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.eq("username", s);
        User user = userMapper.selectOne(wrapper);
        if(user == null){
            return null;
        }
        /*
        * // 查询权限并添加权限到userDetail
        List<AuthorityAdmin> userIdRoleAuthority = authorityAdminMapper.findUserIdRoleAuthority(userAdmin.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (AuthorityAdmin authority : userIdRoleAuthority) {
            authorities.add(new SimpleGrantedAuthority(authority.getUrl()));
        }
        userDetail.setAuthorities(authorities);
        * */

        return new JwtUser(user);
    }
}
