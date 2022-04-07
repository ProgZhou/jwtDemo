package com.security.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.security.demo.exceptions.TokenUnavailable;

import java.util.Calendar;
import java.util.Date;

/**jwt的验证其实就是验证jwt最后那一部分（签名部分）。这里在指定签名的加密方式的时候，还传入了一个字符串来加密，
 * 所以验证的时候不但需要知道加密算法，还需要获得这个字符串才能成功解密，提高了安全性。
 * @author ProgZhou
 * @createTime 2022/04/06
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
                .sign(Algorithm.HMAC256(userId + "AAAAA"));   //加密

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
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret + "AAAAA")).build();
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
}
