//package com.security.demo.utils;
//
//import com.security.demo.bean.JwtUser;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * @author ProgZhou
// * @createTime 2022/04/08
// */
//public abstract class JwtUtils_jjwt {
//    // 主题
//    private static final String SUBJECT = "xijia";
//
//    // jwt的token有效期，
//    //private static final long EXPIRITION = 1000L * 60 * 60 * 24 * 7;//7天
//    private static final long EXPIRITION = 1000L * 60 * 30;   // 半小时
//
//    // 加密key（黑客没有该值无法篡改token内容）
//    private static final String APPSECRET_KEY = "xijia";
//
//    // 用户url权限列表key
//    private static final String AUTH_CLAIMS = "auth";
//
//    /**
//     * TODO  生成token
//     *
//     * @param user
//     * @return java.lang.String
//     * @date 2020/7/6 0006 9:26
//     */
//    public static String generateToken(JwtUser user) {
//        String token = Jwts
//                .builder()
//                // 主题
//                .setSubject(SUBJECT)
//                // 添加jwt自定义值
//                .claim(AUTH_CLAIMS, user.getAuthorities())
//                .claim("username", user.getUsername())
//                .claim("userId", user.getId())
//                .setIssuedAt(new Date())
//                // 过期时间
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
//                // 加密方式,加密key
//                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
//        return token;
//    }
//
//
////    public static Claims checkJWT(String token) {
////        try {
////            final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
////            return claims;
////        } catch (Exception e) {
////            e.printStackTrace();
////            return null;
////        }
////    }
//
//    /**
//     * 获取用户Id
//     *
//     * @param token
//     * @return
//     */
//    public static String getUserId(String token) {
//        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
//        return claims.get("userId").toString();
//    }
//
//
//    /**
//     * 获取用户名
//     *
//     * @param token
//     * @return
//     */
//    public static String getUsername(String token) {
//        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
//        return claims.get("username").toString();
//    }
//
//    /**
//     * 获取用户角色的权限列表, 没有返回空
//     *
//     * @param token
//     * @return
//     */
//    public static List<SimpleGrantedAuthority> getUserAuth(String token) {
//        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
//        List auths = (List) claims.get(AUTH_CLAIMS);
//        String json = JSONArray.toJSONString(auths);
//        List<SimpleGrantedAuthority> grantedAuthorityList = JSON.parseArray(json, SimpleGrantedAuthority.class);
//        return grantedAuthorityList;
//    }
//
//    /**
//     * 是否过期
//     *
//     * @param token
//     * @return
//     */
//    public static boolean isExpiration(String token) {
//        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
//        System.out.println("过期时间: " + claims.getExpiration());
//        return claims.getExpiration().before(new Date());
//    }
//
//}
