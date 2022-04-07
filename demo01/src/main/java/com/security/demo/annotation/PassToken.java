package com.security.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**在controller层上的每个方法上，可以使用这些注解，来决定访问这个方法是否需要携带token，
 * 由于默认是全部检查，所以对于某些特殊接口需要有免验证注解
 * 跳过验证，通常是入口方法上用这个，比如登录接口
 * @author ProgZhou
 * @createTime 2022/04/06
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;   //默认都需要进行token验证
}
