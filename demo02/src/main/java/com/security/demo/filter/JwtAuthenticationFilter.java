package com.security.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.bean.JwtUser;
import com.security.demo.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**登录Filter
 * @author ProgZhou
 * @createTime 2022/04/08
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /*
    * 获取授权管理, 创建JWTLoginFilter时获取
    * */
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/jwt/login");
    }

    /*
    * 当调用登录接口/jwt/login，执行该方法
    * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        JwtUser user = null;
        try {
            //从输入流获取登录信息
            user = new ObjectMapper().readValue(request.getInputStream(), JwtUser.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //调用之前实现UserDetailsService接口的实现类的loadUserByUsername进行登录验证
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
    }

    /*
    * 当登录验证成功，即执行该方法
    * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtUser user = (JwtUser) authResult.getPrincipal();
        //生成jwt，并放入响应头传回前端
        String token = JwtUtils.createToken(user.getId().toString(), user.getUsername(), user.getPassword());
        response.addHeader("token", token);

    }


    /*
    * 当登录验证失败，执行该方法
    * */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }
}
