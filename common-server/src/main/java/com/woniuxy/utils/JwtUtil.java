package com.woniuxy.utils;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    //自定义的密钥
    private static final String SECRET = "qwertyuiopasdfghjklzxcvbnssadsadsadsadsadsadsadsadsadsadaggggsagam";
    //过期时间24小时
    private static final long EXPIRE_TIME = 60 * 24 * 60 * 1000;

    static Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String createToken(Map<String,Object> body){
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
        return Jwts.builder()
                .setClaims(body)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(key)
                .compact();
    }
    //解析token
    public static Claims parseToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public static <T> T parseToken(String token,Class<T> type){
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        //反射
        T value = null;
        try {
            value = type.newInstance();
            Field[] declaredFields = type.getDeclaredFields();
            for (Field field : declaredFields){
                String name = field.getName();
                Object v = claims.get(name);
                if(ObjectUtils.isEmpty(v)){
                    continue;
                }
                field.setAccessible(true);
                field.set(value,v);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

}
