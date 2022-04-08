package com.security.demo.config;

import com.security.demo.filter.JwtAuthenticationFilter;
import com.security.demo.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/** spring security配置类
 *
 * WebSecurityConfigurerAdapter 提供了一种便利的方式去创建 WebSecurityConfigurer的实例，
 * 只需要重写 WebSecurityConfigurerAdapter 的方法，即可配置拦截什么URL、设置什么权限等安全控制。
 * @author ProgZhou
 * @createTime 2022/04/07
 */
@Configuration
@EnableWebSecurity  //启用Spring Security的Web安全支持
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailsService;

    //加密器
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启跨域访问
        http.cors().disable();
        //开启模拟请求
        http.csrf().disable();

        //不需要权限控制的请求，可直接放行
        http.authorizeRequests().antMatchers("/jwt/login", "POST").permitAll()
                //设置jwt过滤器
                .and().addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                //设置不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        super.configure(http);
    }
}
