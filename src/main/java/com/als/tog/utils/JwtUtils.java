package com.als.tog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author dkw
 */
@Data
@Component
public class JwtUtils {
    /**
     * 过期时间
     * 一小时过期
     */
    private final static long  EXPIRATION_TIME = 60 * 60 * 1000L;
    /**
     * 密钥
     */
    private final static String SECRET_KEY = "ZheJiangZheJiangChanQuanJiaoYiGuanLiPingTai2024";

    // 生成JWT
    public static String generateToken(Map<String, Object> claims) {
        Date nowDate = new Date();
        // 1天过期
        Date expireDate = new Date(nowDate.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 解析JWT
    public static Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // 根据token获取用户名
    public String getUserNameFromJwtToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getAudience();
        } catch (Exception e) {
            return null;
        }
    }

    // 校验token
    public Boolean validateJwtToken(String token){
        return true;
    }
}
