package com.security.demo.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**  spring security自带的权限判断类，可以为用户分配权限
 * @author ProgZhou
 * @createTime 2022/04/07
 */
@Data
@NoArgsConstructor
public class JwtUser implements UserDetails {

    /*
    UserDetails中的一些变量信息
    * //账户是否未过期,过期无法验证，在springSecurity 验证中自动调用
    boolean isAccountNonExpired;

    //指定用户是否解锁,锁定的用户无法进行身份验证，在springSecurity 验证中自动调用
    boolean isAccountNonLocked;

    //指示是否已过期的用户的凭据(密码),过期的凭据防止认证，在springSecurity 验证中自动调用
    boolean isCredentialsNonExpired;

    //是否可用 ,禁用的用户不能身份验证，在springSecurity 验证中自动调用
    boolean isEnabled;
    *
    * */

    private Integer id;

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    //将自定义的用户类转换为JwtUser
    public JwtUser(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        //spring security中返回的权限需要是ROLE_开头
        //假设每个用户只有一个权限
        String role = "ROLE_" + user.getRole().toUpperCase();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    //判断账号是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //判断账号是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //判断账号凭证是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}
