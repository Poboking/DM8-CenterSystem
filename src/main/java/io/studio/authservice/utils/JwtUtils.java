package io.studio.authservice.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/8 11:03
 */
@Component
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
public class JwtUtils {


    @Value("${jwt.secret}")
    private static String secret;

    @Value("${jwt.expiration}")
    private static long expiration;

    private static SecretKey key;

    /**
     * 获取秘钥
     */
    private static SecretKey getKey() {
        if (key == null) {
            key = Keys.hmacShaKeyFor(secret.getBytes());
        }
        return key;
    }


    /**
     * 根据用户名生成jwt
     *
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getKey())
                .compact();
    }
    /**
     * 从Token中解析出内容(username)
     *
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * 判单Token有效与否
     *
     * @param token
     * @return
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 从JWT令牌中获取Claims对象
     *
     * @param token
     * @return
     */
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }
}




