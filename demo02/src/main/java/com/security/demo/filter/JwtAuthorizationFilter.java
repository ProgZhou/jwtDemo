package com.security.demo.filter;

import com.security.demo.exceptions.TokenUnavailable;
import com.security.demo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/** 认证过滤器
 * @author ProgZhou
 * @createTime 2022/04/08
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /*
    * 拦截请求
    * */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("请求方式:{} 请求URL:{} ", request.getMethod(), request.getServletPath());
        //获取请求头中携带的token信息
        String token = request.getHeader("token");

        //进行权限认证
        List<SimpleGrantedAuthority> userAuthList = JwtUtils.getUserAuth(token);
        String username = JwtUtils.getClaimByName(token, "username").asString();
        //  添加账户的权限信息，和账号是否为空，然后保存到Security的Authentication授权管理器中
        if(!"".equals(username) && userAuthList != null){
            SecurityContextHolder.getContext().
                    setAuthentication(new UsernamePasswordAuthenticationToken(username, null, userAuthList));
        }

        //放行
        super.doFilterInternal(request, response, chain);

    }
}
