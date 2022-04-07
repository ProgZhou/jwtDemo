package com.security.demo.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.security.demo.annotation.PassToken;
import com.security.demo.bean.User;
import com.security.demo.exceptions.TokenUnavailable;
import com.security.demo.service.UserService;
import com.security.demo.service.impl.UserServiceImpl;
import com.security.demo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author ProgZhou
 * @createTime 2022/04/06
 */
@Slf4j
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中取出token
        String token = request.getHeader("token");

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //检查是否有PassToken注解
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            //如果passToken注解的值为true，直接放行
            if(passToken.required()){
                return true;
            }
        } else{
            log.info("验证jwt");
            //登录失效，token失效
            if(token == null){
                throw new TokenUnavailable("token已失效，请重新登录");
            }

            //获取签发对象 -- userId
            String audience = JwtUtils.getAudience(token);
            //先按照获取的userId查找是否有这个user
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id", Integer.parseInt(audience));
            User user = userService.getOne(wrapper);
            if(user == null){
                throw new TokenUnavailable("用户不存在");
            }

            //验证token是否为用户token
            JwtUtils.verifyToken(token, audience);

            Claim username = JwtUtils.getClaimByName(token, "username");
            Claim password = JwtUtils.getClaimByName(token, "password");

            request.setAttribute("username", username);
            request.setAttribute("password", password);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
