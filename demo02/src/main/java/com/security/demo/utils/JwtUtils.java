package com.security.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.security.demo.bean.JwtUser;
import com.security.demo.exceptions.TokenUnavailable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

/** jwt工具类
 * @author ProgZhou
 * @createTime 2022/04/07
 */
public abstract class JwtUtils {
    /**
     * 有效时间30min，加密密钥：用户id + “AAAA”，签发对象为userId
     * @param userId 用户id
     * @param userName 用户名
     * @param password 用户密码
     * @return 加密之后的JWT字符串
     */
    public static String createToken(String userId, String userName, String password){
        Calendar time = Calendar.getInstance();

        time.add(Calendar.MINUTE, 30);

        Date expireDate = time.getTime();

        return JWT.create().withAudience(userId)   //签发对象
                .withIssuedAt(new Date())    //发行时间
                .withExpiresAt(expireDate)  //有效时间
                .withClaim("username", userName)    //载荷，随便写几个都可以
                .withClaim("password", password)
                .sign(Algorithm.HMAC256(userId + "spring-security-jwt"));   //加密

    }

    /**
     * 从用户类中生成token
     * @param user 查询到的用户类
     * @return jwt字符串
     */
    public static String createToken(JwtUser user){

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        //这里确定了每个用户只有一个角色，当不确定时，需要使用数组存储角色信息
        String role = null;
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }

        Calendar time = Calendar.getInstance();

        time.add(Calendar.MINUTE, 30);

        Date expireDate = time.getTime();

        return JWT.create().withAudience(user.getId().toString())
                .withClaim("auth", role)
                .withClaim("username", user.getUsername())
                .withClaim("userId", user.getId().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(user.getId() + "spring-security-jwt"));
    }

    /**
     * 检验token是否合法
     * @param token 传输的JWT字符串
     * @param secret 密钥，这里应该是userId
     * @throws TokenUnavailable
     */
    public static void verifyToken(String token, String secret) throws TokenUnavailable {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret + "spring-security-jwt")).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //效验失败
            //这里抛出的异常是我自定义的一个异常，你也可以写成别的
            throw new TokenUnavailable("Token不正确");
        }
    }

    /**
     * 获取签发对象
     * @param token 传输的yoken
     * @return
     * @throws TokenUnavailable
     */
    public static String getAudience(String token) throws TokenUnavailable {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            //这里是token解析失败
            throw new TokenUnavailable();
        }
        return audience;
    }

    /**
     * 通过载荷名字获取载荷的值
     * @param token
     * @param name
     * @return
     */
    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }

    /**
     * 通过token获取用户的权限列表
     * @param token
     * @return
     */
    public static List<SimpleGrantedAuthority> getUserAuth(String token){
        Claim auth = JWT.decode(token).getClaim("auth");
        String s = auth.asString();
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(s));
        return list;
    }
}
